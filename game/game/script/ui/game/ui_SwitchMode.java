package game.script.ui.game;

import game.script.Script;
import game.script.game.event.Logic;

public class ui_SwitchMode implements Script {

	@Override
	public void execute() {
		Logic.requestSwitchMode(true);
	}
}
