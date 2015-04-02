package game.cycle.scene.ui.widgets;

import game.cycle.scene.game.world.skill.Skill;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpellBookItem {

	private Skill skill;

	public SpellBookItem(Skill skill) {
		this.skill = skill;
	}
	
	public void draw(SpriteBatch sprites, BitmapFont font, int x, int y) {
		sprites.draw(skill.tex, x, y + 5, 48, 48);
		font.draw(sprites, skill.title, x + 50, y + 50);
		font.draw(sprites, skill.tooltip, x + 50, y + 35);
	}

	public Skill getSkill() {
		return skill;
	}
}
