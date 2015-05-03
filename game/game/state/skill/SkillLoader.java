package game.state.skill;

import game.script.game.effect.effect_Damage;
import game.script.game.effect.effect_Drag;
import game.script.game.effect.effect_Heal;

public class SkillLoader {

	public static Skill build(Skill skill, int effect1, int effect2, int effect3, int effect4) {
		skill.effects = new SkillEffect[4];
		skill.effects[0] = setEffect(effect1);
		skill.effects[1] = setEffect(effect2);
		skill.effects[2] = setEffect(effect3);
		skill.effects[3] = setEffect(effect4);
		
		return skill;
	}
	
	// Effects
	public static final int EFFECT_DAMAGE = 1;
	public static final int EFFECT_HEAL = 2;
	public static final int EFFECT_DRAG = 3;
	
	private static SkillEffect setEffect(int effectId) {
		switch (effectId) {
			case EFFECT_DAMAGE:
				return new effect_Damage(3);
				
			case EFFECT_HEAL:
				return new effect_Heal(10);
				
			case EFFECT_DRAG:
				return new effect_Drag();
		}
		
		return null;
	}
}