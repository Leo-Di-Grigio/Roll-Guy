package game.script.ui;

import ui.widgets.Button;
import game.script.Script;

public class ui_ButtonClick implements Script {
	
	private Button button;
	
	public ui_ButtonClick(Button button) {
		this.button = button;
	}

	@Override
	public void execute() {
		button.click();
	}
}
