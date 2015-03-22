package game.cycle.scene.game.world.creature.skills;

import java.util.TreeMap;

import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.skill.Skill;

public class SkillList {

	public TreeMap<Integer, Skill> skills;
	
	public SkillList() {
		skills = new TreeMap<Integer, Skill>();
		
		// test list
		skills.put(0, Database.getSkill(0));
		skills.put(1, Database.getSkill(1));
	}

	public Skill get(int id) {
		return skills.get(id);
	}
}
