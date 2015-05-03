package game.script.ui.game;

import game.cycle.scene.ui.widgets.windows.WindowPlayerActionBar;
import game.script.Script;

public class ui_ActionBarPick implements Script {

	private WindowPlayerActionBar window;
	private int actionBarSlot;

	public ui_ActionBarPick(WindowPlayerActionBar window, int actionBarSlot) {
		this.window = window;
		this.actionBarSlot = actionBarSlot;
	}

	@Override
	public void execute() {
		window.pickSkill(actionBarSlot);
	}
}
