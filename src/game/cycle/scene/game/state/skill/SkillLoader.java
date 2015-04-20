package game.cycle.scene.game.state.skill;

import game.script.game.effect.effect_Damage;
import game.script.game.effect.effect_Drag;
import game.script.game.effect.effect_Heal;

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
	public static final int effect_Heal = 2;
	public static final int effect_Drag = 3;
	
	private static Effect setEffect(int effectId) {
		switch (effectId) {
			case effect_Damage:
				return new effect_Damage(3);
				
			case effect_Heal:
				return new effect_Heal(10);
				
			case effect_Drag:
				return new effect_Drag();
		}
		
		return null;
	}
}