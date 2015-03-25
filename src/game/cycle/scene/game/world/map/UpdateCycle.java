package game.cycle.scene.game.world.map;

import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.game.world.creature.NPC;
import game.cycle.scene.game.world.creature.Player;
import game.script.game.event.GameEvents;
import game.tools.Log;

public class UpdateCycle {
	
	private boolean turnBased;
	private boolean playerTurn;
	
	public void update(Player player, Location loc) {
		for(Creature creature: loc.creatures.values()){
			creature.animationUpdate();
		}
		
		if(turnBased){
			if(playerTurn){
				playerUpdate(player, loc);
			}
			else{
				npcUpdate(loc);
			}
		}
		else{
			playerUpdate(player, loc);
			npcUpdate(loc);
		}
		
		checkCombat(loc);
	}
	
	private void npcUpdate(Location loc){
		if(turnBased){
			boolean update = false; // unupdated NPC check
			
			for(NPC npc: loc.npcs.values()){
				if(!npc.aidata.updated){
					update = true;
					npc.update(loc);
					break;
				}
			}
			
			if(update == false){ // no unupdated NPC
				playerTurn = true;
				GameEvents.nextTurn();
				Log.debug("Player turn");
			}
		}
	}
	
	private void playerUpdate(Player player, Location loc){
		player.update(loc);
	}
	
	public void npcTurn(Player player, Location loc){
		player.resetPath();
		playerTurn = false;
		GameEvents.nextTurn();
		
		for(NPC npc: loc.npcs.values()){
			if(npc.isAlive()){
				npc.resetAI();
				npc.resetAp();
			}
		}
	
		Log.debug("NPC turn");
	}
	
	public void realTime(Player player){
		if(turnBased){
			turnBased = false;
			player.resetAp();
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
