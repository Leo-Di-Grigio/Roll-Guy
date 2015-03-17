package game.cycle.scene.game.world.creature;

import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.skill.Skill;

public class SkillList {

	public Skill attack;
	
	public SkillList() {
		attack = Database.getSkill(0);
	}
}
