package game.script.ui.game;

import game.script.Script;
import game.script.game.event.GameEvents;

public class ui_EndTurn implements Script {
	
	@Override
	public void execute() {
		GameEvents.endTurn();
	}
}