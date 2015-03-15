package game.script.ui.editor;

import game.cycle.scene.ui.list.UIGame;
import game.script.Script;

public class ui_GOEditorMenuCancel implements Script {

	private UIGame ui;
	
	public ui_GOEditorMenuCancel(UIGame ui) {
		this.ui = ui;
	}

	@Override
	public void execute() {
		ui.goEdit.setGO(null);
	}
}
