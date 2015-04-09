package game.script.ui.game;

import game.script.Script;
import game.script.game.event.GameEvents;

public class ui_SwitchMode implements Script {

	@Override
	public void execute() {
		GameEvents.requestSwitchMode(true);
	}
}
