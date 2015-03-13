package game.script.ui.app;

import game.cycle.scene.ui.list.UIGame;
import game.script.Script;

public class ui_LocationAddMenu implements Script {

	private UIGame ui;
	
	public ui_LocationAddMenu(UIGame ui) {
		this.ui = ui;
	}

	@Override
	public void execute() {
		ui.setVisibleCreteLocation(!ui.locationCreateVisible);
	}
}
