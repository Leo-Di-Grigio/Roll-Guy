package game.cycle.scene.game.world.creature;

public class CreatureProto {
	
	public int id;
	public int texture;
	public Stats stats;
	public String name;
	
	public CreatureProto() {
		stats = new Stats(5);
	}
}
