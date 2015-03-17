package game.cycle.scene.game.world.creature;

import game.cycle.scene.game.world.creature.ai.AIData;

public class NPC extends Creature {

	// AI
	public AIData aidata;
	
	public NPC(CreatureProto proto) {
		super(proto);
		aidata = new AIData();
	}
}
