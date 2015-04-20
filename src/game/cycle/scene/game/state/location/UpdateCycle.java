package game.cycle.scene.game.state.location;

import com.badlogic.gdx.graphics.OrthographicCamera;

import game.cycle.scene.game.state.location.creature.Creature;
import game.cycle.scene.game.state.location.creature.NPC;
import game.cycle.scene.game.state.location.creature.Player;
import game.cycle.scene.ui.list.UIGame;
import game.script.game.event.Logic;
import game.tools.Const;
import game.tools.Log;

public class UpdateCycle {

	// mode
	private boolean turnBased;
	private boolean playerTurn;
	
	// request
	private boolean requestSwitch;
	private boolean requestTurnBased;
	private boolean requestEndTurn;
	
	// conditions
	private boolean combat;
	private boolean playerInit;
		
	// Control
	public void switchMode(boolean playerInit) {
		this.playerInit = playerInit;
		this.requestSwitch = true;
	}

	public void turnMode(boolean playerInit) {
		this.playerInit = playerInit;
		this.requestTurnBased = true;
	}
	
	public void endTurn() {
		this.requestEndTurn = true;
	}
	
	public void update(Player player, Location loc, OrthographicCamera camera, UIGame ui, boolean losMode) {
		updateRequests(player, loc);
		updateUI(player, loc, ui);
		updateLoc(player, loc, camera, ui, losMode);
	}
	
	private void updateRequests(Player player, Location loc) {
		if(!player.isDirected){
			if(requestEndTurn || requestSwitch || requestTurnBased){
				player.resetPath();
				
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
						turnBase(playerInit, loc);
					}
					requestSwitch = false;
				}
				else if(requestTurnBased){
					if(!turnBased){
						turnBase(playerInit, loc);
					}
					
					requestTurnBased = false;
				}
			}
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
					
					if(!combat){
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
	
	private void updateLoc(Player player, Location loc, OrthographicCamera camera, UIGame ui, boolean losMode) {
		for(Creature creature: loc.creaturesValues()){
			creature.animationUpdate();
		}
		
		// turn
		if(turnBased){
			if(playerTurn){
				playerUpdate(player, loc, camera, losMode);
			}
			else{
				npcUpdate(player, loc, camera, ui, losMode);
			}
		}
		else{
			playerUpdate(player, loc, camera, losMode);
			npcUpdate(player, loc, camera, ui, losMode);
		}
		
		// conditions check
		checkCombat(loc);
	}

	//
	private void npcUpdate(Player player, Location loc, OrthographicCamera camera, UIGame ui, boolean losMode){
		if(turnBased){
			boolean update = false; // unupdated NPC check
			
			for(NPC npc: loc.npcValues()){
				if(!npc.aidata.softUpdated && npc.isAlive()){
					update = true;
					npc.update(loc, camera, player, losMode);
					break;
				}
			}
			
			if(update == false){ // no unupdated NPC
				playerTurn(player, ui);
				Log.debug("Player turn");
			}
		}
		else{
			for(NPC npc: loc.npcValues()){
				if(npc.aidata.softUpdated && npc.isAlive()){
					npc.resetAI();
				}
				else{
					npc.update(loc, camera, player, losMode);
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

	private void playerTurn(Player player, UIGame ui){
		playerTurn = true;
		player.resetAp();
		player.setUsedSkill(ui, null);
		ui.setMode(Const.INVALID_ID);
	}
	
	private void playerUpdate(Player player, Location loc, OrthographicCamera camera, boolean losMode){
		player.update(loc, camera, player, losMode);
	}

	private void realTime(Player player, Location loc){
		if(turnBased){
			turnBased = false;
			player.resetAp();
		}
	}

	private void turnBase(boolean playerTurn, Location loc) {
		if(!turnBased){
			turnBased = true;
			this.playerTurn = playerTurn;
			
			for(NPC npc: loc.npcValues()){
				npc.animationMovement = false;
			}
		}
	}
	
	private void resetNpcAI(Location loc){
		for(NPC npc: loc.npcValues()){
			if(npc.isAlive()){
				npc.resetAI();
				npc.resetAp();
			}
		}
	}

	private void checkCombat(Location loc) {
		boolean combat = false;
		
		for(NPC npc: loc.npcValues()){
			if(npc.aidata.combat){
				combat = true;
				break;
			}
		}
		
		this.combat = combat;
		
		if(combat && !turnBased){
			Logic.requestSwitchMode(playerTurn);
		}
	}
	
	public boolean isTurnBased() {
		return turnBased;
	}

	public boolean isPlayerTurn() {
		return playerTurn;
	}
}
