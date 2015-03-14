package game.script.ui.app;

import game.cycle.scene.ui.list.UIGame;
import game.script.Script;

public class ui_LocationAddMenuCancel implements Script {

	private UIGame ui;
	
	public ui_LocationAddMenuCancel(UIGame ui) {
		this.ui = ui;
	}

	@Override
	public void execute() {
		ui.setVisibleCreteLocation(!ui.locationCreateVisible);
	}
}
