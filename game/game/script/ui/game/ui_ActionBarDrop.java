package game.script.ui.game;

import resources.Cursors;
import game.cycle.scene.ui.widgets.windows.WindowPlayerActionBar;
import game.script.Script;
import game.state.skill.Skill;

public class ui_ActionBarDrop implements Script {

	private WindowPlayerActionBar window;
	private int actionBarSlot;

	public ui_ActionBarDrop(WindowPlayerActionBar window, int actionBarSlot) {
		this.window = window;
		this.actionBarSlot = actionBarSlot;
	}

	@Override
	public void execute() {
		Skill skill = Cursors.getSelectedSkill();
		
		if(skill != null){
			window.dropSkill(skill, actionBarSlot);
			Cursors.setSelectedSkill(null);
		}
	}
}
