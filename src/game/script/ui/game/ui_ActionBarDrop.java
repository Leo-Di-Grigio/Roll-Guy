package game.script.ui.game;

import game.cycle.scene.game.world.skill.Skill;
import game.cycle.scene.ui.widgets.windows.WindowPlayerActionBar;
import game.resources.Cursors;
import game.script.Script;

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
