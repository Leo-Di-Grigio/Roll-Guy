package game.script.ui;

import game.cycle.scene.ui.widgets.Button;
import game.cycle.scene.ui.widgets.windows.WindowDialog;
import game.script.Script;
import game.script.game.event.Logic;

public class ui_DialogClose implements Script {

	private WindowDialog dialog;
	private Button button;
	
	public ui_DialogClose(WindowDialog dialog, Button button) {
		this.dialog = dialog;
		this.button = button;
	}

	@Override
	public void execute() {
		this.dialog.setVisible(false);
		this.button.setVisible(false);
		this.dialog.setCreature(null);
		Logic.dialogEnd();
	}
}
