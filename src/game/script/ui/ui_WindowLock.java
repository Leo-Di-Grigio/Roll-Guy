package game.script.ui;

import game.cycle.scene.ui.widgets.Window;
import game.script.Script;

public class ui_WindowLock implements Script {

	private Window window;
	
	public ui_WindowLock(Window window) {
		this.window = window;
	}

	@Override
	public void execute() {
		this.window.lock();
	}
}
