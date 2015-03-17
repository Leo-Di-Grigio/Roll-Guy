package game.cycle.scene.game.world.skill;

import game.cycle.scene.game.world.LocationObject;

public interface Effect {
	abstract public void execute(LocationObject caster, LocationObject target);
}