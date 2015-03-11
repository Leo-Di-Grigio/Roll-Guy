package game.cycle.scene.game.world.map;

import game.cycle.scene.game.world.creature.Creature;

public class Node {

	public boolean passable;
	public int type;
	
	public Creature creature;
	
	public Node() {
		creature = null;
	}
}
