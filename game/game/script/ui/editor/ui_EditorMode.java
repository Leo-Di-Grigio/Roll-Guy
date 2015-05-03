package game.script.ui.editor;

import game.cycle.scene.ui.list.UIGame;
import game.script.Script;

public class ui_EditorMode implements Script {

	private UIGame ui;
	private int mode;

	public ui_EditorMode(UIGame ui, int mode) {
		this.ui = ui;
		this.mode = mode;
	}

	@Override
	public void execute() {
		ui.setMode(mode);
	}
}
