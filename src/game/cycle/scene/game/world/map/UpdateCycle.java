package game.cycle.scene.game.world.map;

import com.badlogic.gdx.graphics.OrthographicCamera;

import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.game.world.creature.NPC;
import game.cycle.scene.game.world.creature.Player;
import game.script.game.event.GameEvents;
import game.tools.Log;

public class UpdateCycle {
	
	private boolean turnBased;
	private boolean playerTurn;
	
	public void update(Player player, Location loc, OrthographicCamera camera) {
		for(Creature creature: loc.creatures.values()){
			creature.animationUpdate();
		}
		
		if(turnBased){
			if(playerTurn){
				playerUpdate(player, loc, camera);
			}
			else{
				npcUpdate(loc, camera);
			}
		}
		else{
			playerUpdate(player, loc, camera);
			npcUpdate(loc, camera);
		}
		
		checkCombat(loc);
	}
	
	private void npcUpdate(Location loc, OrthographicCamera camera){
		if(turnBased){
			boolean update = false; // unupdated NPC check
			
			for(NPC npc: loc.npcs.values()){
				if(!npc.aidata.updated && npc.isAlive()){
					update = true;
					npc.update(loc, camera);
					break;
				}
			}
			
			if(update == false){ // no unupdated NPC
				playerTurn = true;
				GameEvents.nextTurn();
				Log.debug("Player turn");
			}
		}
		else{
			for(NPC npc: loc.npcs.values()){
				if(npc.aidata.updated && npc.isAlive()){
					npc.resetAI();
				}
				else{
					npc.update(loc, camera);
				}
			}
		}
	}
	
	private void playerUpdate(Player player, Location loc, OrthographicCamera camera){
		player.update(loc, camera);
	}
	
	public void npcTurn(Player player, Location loc){
		player.resetPath();
		playerTurn = false;
		GameEvents.nextTurn();
		resetNpcAI(loc);
	
		Log.debug("NPC turn");
	}
	
	public void realTime(Player player, Location loc){
		if(turnBased){
			turnBased = false;
			player.resetAp();
		}
	}
	
	private void resetNpcAI(Location loc){
		for(NPC npc: loc.npcs.values()){
			if(npc.isAlive()){
				npc.resetAI();
				npc.resetAp();
			}
		}
	}

	public void turnBase(boolean playerTurn) {
		if(!turnBased){
			turnBased = true;
			this.playerTurn = playerTurn;
		}
	}

	public boolean isTurnBased() {
		return turnBased;
	}
	
	private void checkCombat(Location loc) {
		boolean combat = false;
		
		for(NPC npc: loc.npcs.values()){
			if(npc.aidata.combat){
				combat = true;
				break;
			}
		}
		
		if(!combat){
			GameEvents.gameModeRealTime();
		}
	}
}
