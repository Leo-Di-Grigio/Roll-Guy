package game.cycle.scene.game.state.skill;

import game.cycle.scene.game.state.location.LocationObject;

public interface SkillEffect {
	
	abstract public void execute(LocationObject caster, LocationObject target);
}