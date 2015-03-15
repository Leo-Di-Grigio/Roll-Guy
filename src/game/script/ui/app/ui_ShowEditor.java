package game.script.ui.app;

import game.cycle.scene.ui.list.UIGame;
import game.script.Script;

public class ui_ShowEditor implements Script {

	private UIGame ui;
	
	public ui_ShowEditor(UIGame ui) {
		this.ui = ui;
	}

	@Override
	public void execute() {
		ui.showEditor();
	}
}
