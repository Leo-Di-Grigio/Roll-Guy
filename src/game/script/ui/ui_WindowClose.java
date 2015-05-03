package game.script.ui;

import ui.widgets.Window;
import game.script.Script;

public class ui_WindowClose implements Script {

	private Window window;
	
	public ui_WindowClose(Window window) {
		this.window = window;
	}

	@Override
	public void execute() {
		this.window.setVisible(false);
	}
}
