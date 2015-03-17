package game.cycle.scene.game.world.skill;

import game.cycle.scene.game.world.creature.Creature;

public interface Effect {
	abstract public void execute(Creature target);
}