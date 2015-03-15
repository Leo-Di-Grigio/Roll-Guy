package game.script.ui;

import game.cycle.scene.ui.widgets.Button;
import game.cycle.scene.ui.widgets.Dialog;
import game.script.Script;

public class ui_DialogClose implements Script {

	private Dialog dialog;
	private Button button;
	
	public ui_DialogClose(Dialog dialog, Button button) {
		this.dialog = dialog;
		this.button = button;
	}

	@Override
	public void execute() {
		this.dialog.setVisible(false);
		this.button.setVisible(false);
		this.dialog.setCreature(null);
	}
}
