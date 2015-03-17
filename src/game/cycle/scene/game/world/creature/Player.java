package game.cycle.scene.game.world.creature;

import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.map.Location;
public class Player extends Creature {

	public Player() {
		super(Database.getCreature(0));
		this.player = true;
	}
	
	@Override
	public void update(Location location) {
		super.update(location);
	}
}
