package game.cycle.scene.game.world.location;

import com.badlogic.gdx.graphics.OrthographicCamera;

import game.cycle.scene.game.world.location.creature.Creature;
import game.cycle.scene.game.world.location.creature.NPC;
import game.cycle.scene.game.world.location.creature.Player;
import game.cycle.scene.ui.list.UIGame;
import game.tools.Log;

public class UpdateCycle {

	// mode
	private boolean turnBased;
	private boolean playerTurn;
	
	// request
	private boolean requestSwitch;
	private boolean requestEndTurn;

	// conditions
	private boolean combat;
	private boolean playerInit;
		
	// Control
	public void switchMode(boolean playerInit) {
		this.playerInit = playerInit;
		requestSwitch = true;
	}

	public void endTurn() {
		requestEndTurn = true;
	}
	
	public void update(Player player, Location loc, OrthographicCamera camera, UIGame ui) {
		updateRequests(player, loc);
		updateUI(player, loc, ui);
		updateLoc(player, loc, camera);
	}
	
	private void updateRequests(Player player, Location loc) {
		if(requestEndTurn){
			npcTurn(player, loc);
			requestEndTurn = false;
		}
		else if(requestSwitch){
			if(turnBased){
				if(!combat){
					realTime(player, loc);
				}
			}
			else{
				turnBase(playerInit);
			}
			requestSwitch = false;
		}
	}

	private void updateUI(Player player, Location loc, UIGame ui) {
		if(turnBased){
			if(playerTurn){
				if(player.ap == 0){
					ui.actionBar.endTurn.setVisible(false);
					ui.actionBar.switchMode.setVisible(false);
					npcTurn(player, loc);
				}
				else{
					ui.actionBar.endTurn.setVisible(true);
					
					if(combat){
						ui.actionBar.switchMode.setText("End combat");
						ui.actionBar.switchMode.setVisible(true);
					}
				}
			}
			else{
				ui.actionBar.switchMode.setVisible(false);
				ui.actionBar.endTurn.setVisible(false);
			}
		}
		else{
			ui.actionBar.switchMode.setVisible(true);
			ui.actionBar.switchMode.setText("Begin combat");
			ui.actionBar.endTurn.setVisible(false);
		}
	}
	
	private void updateLoc(Player player, Location loc, OrthographicCamera camera) {
		if(!requestEndTurn && !requestSwitch){
			for(Creature creature: loc.creatures.values()){
				creature.animationUpdate();
			}
		
			// turn
			if(turnBased){
				if(playerTurn){
					playerUpdate(player, loc, camera);
				}
				else{
					npcUpdate(player, loc, camera);
				}
			}
			else{
				playerUpdate(player, loc, camera);
				npcUpdate(player, loc, camera);
			}
		
			// conditions check
			checkCombat(loc);
		}
	}

	//
	private void npcUpdate(Player player, Location loc, OrthographicCamera camera){
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
				playerTurn(player);
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
	
	private void npcTurn(Player player, Location loc){
		player.resetPath();
		playerTurn = false;
		resetNpcAI(loc);
	
		Log.debug("NPC turn");
	}

	private void playerTurn(Player player){
		playerTurn = true;
		player.resetAp();
	}
	
	private void playerUpdate(Player player, Location loc, OrthographicCamera camera){
		player.update(loc, camera);
	}

	private void realTime(Player player, Location loc){
		if(turnBased){
			turnBased = false;
			player.resetAp();
		}
	}

	private void turnBase(boolean playerTurn) {
		if(!turnBased){
			turnBased = true;
			this.playerTurn = playerTurn;
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

	private void checkCombat(Location loc) {
		boolean combat = false;
		
		for(NPC npc: loc.npcs.values()){
			if(npc.aidata.combat){
				combat = true;
				break;
			}
		}
		
		if(!combat){
			this.combat = true;
		}
	}
	
	public boolean isTurnBased() {
		return turnBased;
	}
}
