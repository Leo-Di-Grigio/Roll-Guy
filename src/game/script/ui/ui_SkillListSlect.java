package game.script.ui;

import ui.widgets.SpellBook;
import game.cycle.input.UserInput;
import game.script.Script;

public class ui_SkillListSlect implements Script {

	private SpellBook spellbook;

	public ui_SkillListSlect(SpellBook spellbook) {
		this.spellbook = spellbook;
	}

	@Override
	public void execute() {
		spellbook.selectLine(UserInput.mouseX, UserInput.mouseY);
	}
}
