package game.cycle.scene.game.world.creature;

import game.cycle.scene.game.world.creature.ai.AI;
import game.cycle.scene.game.world.creature.ai.AIData;
import game.cycle.scene.game.world.map.Location;

public class NPC extends Creature {

	// AI
	public AIData aidata;
	
	public NPC(CreatureProto proto) {
		super(proto);
		this.npc = true;
		aidata = new AIData();
	}
	
	@Override
	public void update(Location location) {
		if(!aidata.executed){
			AI.execute(location, this);
		}
		
		super.update(location);
		
		if(path == null){
			AI.update(location, this);
		}
	}
}
