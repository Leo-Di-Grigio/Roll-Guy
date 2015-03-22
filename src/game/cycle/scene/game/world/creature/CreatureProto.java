package game.cycle.scene.game.world.creature;

import game.cycle.scene.game.world.creature.struct.Stats;

public class CreatureProto {
	
	public int id;
	public int texture;
	public int fraction;
	public Stats stats;
	public String name;
	
	public CreatureProto() {
		stats = new Stats(5);
	}
}