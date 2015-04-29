package client.scenes.ui.script.list;

import client.scenes.ui.script.UIScript;
import client.scenes.ui.widget.Window;

public class ui_WindowClose extends UIScript {

	private Window window;

	public ui_WindowClose(Window window) {
		this.window = window;
	}

	@Override
	public void execute() {
		this.window.setVisible(false);
	}
}
