package client.scenes.ui.script.list;

import client.scenes.game.SceneGame;
import client.scenes.ui.script.UIScript;

public class ui_ExitGame extends UIScript {

	private SceneGame scene;
	
	public ui_ExitGame(SceneGame scene) {
		this.scene = scene;
	}

	@Override
	public void execute() {
		this.scene.showMainMenu();
	}
}
