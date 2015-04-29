package client.scenes.game;

import client.scenes.ui.UI;
import client.scenes.ui.widget.window.WindowTools;

public class UIGame extends UI {

	public UIGame(SceneGame scene) {
		WindowTools tools = new WindowTools("tools-window", this, 1, scene);
		this.add(tools);
	}
	
	@Override
	public void onload() {
		
	}

	@Override
	public void onclose() {

	}
}
