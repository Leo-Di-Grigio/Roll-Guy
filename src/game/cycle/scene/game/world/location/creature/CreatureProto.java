package game.cycle.scene.game.world.location.creature;

import game.cycle.scene.game.world.location.creature.struct.Stats;

public class CreatureProto {
	
	public int id;
	public int texture;
	public int fraction;
	public int race;
	public Stats stats;
	public String name;
	
	// dialogs
	public int dialogStart;
	public int [] dialogTopics;
	
	// corpse
	public boolean leaveCorpse;
	
	public CreatureProto() {
		stats = new Stats(5);
	}
}