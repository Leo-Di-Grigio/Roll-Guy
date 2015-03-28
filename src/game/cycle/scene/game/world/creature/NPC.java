package game.cycle.scene.game.world.creature;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.cycle.scene.game.world.creature.ai.AI;
import game.cycle.scene.game.world.creature.ai.AIData;
import game.cycle.scene.game.world.event.LocationEvent;
import game.cycle.scene.game.world.map.Location;
import game.resources.Resources;
import game.resources.Tex;

public class NPC extends Creature {

	// AI
	public AIData aidata;
	private static Texture warningTex;
	
	public NPC(int guid, CreatureProto proto) {
		super(guid, proto);
		NPC.warningTex = Resources.getTex(Tex.npcWarning);
		this.npc = true;
		aidata = new AIData();
	}
	
	@Override
	public void update(Location location, OrthographicCamera camera) {
		if(!aidata.executed){
			AI.execute(location, this);
		}
		if(!isMoved){
			AI.update(location, this);
		}
		
		super.update(location, camera);
	}

	public void resetAI() {
		this.resetPath();
		aidata.reset();
	}

	public void aiEvent(Location location, LocationEvent event) {
		AI.event(location, event, this);
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
		
		if(isAlive()){
			if(aidata.viewedEnemy.size() > 0){
				batch.draw(warningTex, sprite.getX(), sprite.getY());
			}
		}
	}
}
