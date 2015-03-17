package game.script.game.effect;

import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.game.world.skill.Effect;

public class effect_Damage implements Effect {

	private int damage;
	
	public effect_Damage(int damage) {
		this.damage = damage;
	}

	@Override
	public void execute(Creature target) {
		target.damage(damage);
	}
}
