package game.script.game.effect;

import game.cycle.scene.game.world.location.LocationObject;
import game.cycle.scene.game.world.location.creature.Creature;
import game.cycle.scene.game.world.skill.Effect;

public class effect_Heal implements Effect {

	private int heal;
	
	public effect_Heal(int heal) {
		this.heal = heal;
	}

	@Override
	public void execute(LocationObject caster, LocationObject target) {
		if(target.isCreature()){
			Creature creature = (Creature)target;
			
			if(creature.struct.isAlive()){
				creature.struct.heal(heal);
			}
		}
	}
}
