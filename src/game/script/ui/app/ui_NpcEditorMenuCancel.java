package game.script.ui.app;

import game.cycle.scene.ui.list.UIGame;
import game.script.Script;

public class ui_NpcEditorMenuCancel implements Script {

	private UIGame ui;

	public ui_NpcEditorMenuCancel(UIGame ui) {
		this.ui = ui;
	}

	@Override
	public void execute() {
		ui.editorNpcEdit.setCreature(null);
	}
}
