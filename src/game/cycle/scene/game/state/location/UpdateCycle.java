package game.cycle.scene.game.state.location;

import com.badlogic.gdx.graphics.OrthographicCamera;

import game.cycle.scene.game.state.location.creature.Creature;
import game.cycle.scene.game.state.location.creature.NPC;
import game.cycle.scene.game.state.location.creature.Player;
import game.cycle.scene.ui.list.UIGame;

public class UpdateCycle {
	
	public void update(Player player, Location loc, OrthographicCamera camera, UIGame ui, boolean losMode) {
		updateLoc(player, loc, camera, ui, losMode);
	}
	
	private void updateLoc(Player player, Location loc, OrthographicCamera camera, UIGame ui, boolean losMode) {
		for(Creature creature: loc.creaturesValues()){
			creature.animationUpdate();
		}
		
		playerUpdate(player, loc, camera, losMode);
		npcUpdate(player, loc, camera, ui, losMode);
	}
	
	private void npcUpdate(Player player, Location loc, OrthographicCamera camera, UIGame ui, boolean losMode){
		for(NPC npc: loc.npcValues()){
			if(npc.aidata.softUpdated && npc.isAlive()){
				npc.resetAI();
			}
			else{
				npc.update(loc, camera, player, losMode);
			}
		}
	}
	
	private void playerUpdate(Player player, Location loc, OrthographicCamera camera, boolean losMode){
		player.update(loc, camera, player, losMode);
	}
}
