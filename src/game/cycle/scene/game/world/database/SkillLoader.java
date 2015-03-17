package game.cycle.scene.game.world.database;

import game.cycle.scene.game.world.skill.Effect;
import game.cycle.scene.game.world.skill.Skill;
import game.script.game.effect.effect_Damage;

public class SkillLoader {

	public static Skill build(Skill skill, int effect1, int effect2, int effect3, int effect4) {
		skill.effects = new Effect[4];
		skill.effects[0] = setEffect(effect1);
		skill.effects[1] = setEffect(effect2);
		skill.effects[2] = setEffect(effect3);
		skill.effects[3] = setEffect(effect4);
		
		return skill;
	}
	
	// Effects
	public static final int effect_Damage = 1;
	
	private static Effect setEffect(int effectId) {
		switch (effectId) {
			case effect_Damage:
				return new effect_Damage(3);
		}
		
		return null;
	}
}
