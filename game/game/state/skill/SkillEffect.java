package game.state.skill;

import game.state.location.LocationObject;

public interface SkillEffect {
	
	abstract public void execute(LocationObject caster, LocationObject target);
}