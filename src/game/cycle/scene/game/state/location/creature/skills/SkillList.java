package game.cycle.scene.game.state.location.creature.skills;

import java.util.TreeMap;

import game.cycle.scene.game.state.skill.Skill;

public class SkillList {

	public TreeMap<Integer, Skill> skills;
	
	public SkillList() {
		skills = new TreeMap<Integer, Skill>();
	}

	public Skill get(int id) {
		return skills.get(id);
	}

	public void put(int slot, Skill skill) {
		skills.put(slot, skill);
	}

	public void remove(int slot){
		skills.remove(slot);
	}
}
