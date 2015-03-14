package game.cycle.scene.game.world.creature;

import game.cycle.scene.game.world.database.Database;

public class Player extends Creature {

	public Player() {
		super(Database.getCreature(0));
	}
}
