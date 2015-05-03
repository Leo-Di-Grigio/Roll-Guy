package game.script.ui.app;

import ui.widgets.used.Button;
import game.SceneGame;
import game.script.Script;

public class ui_LOSMode implements Script {

	private SceneGame scene;
	private Button button;

	public ui_LOSMode(SceneGame scene, Button button) {
		this.scene = scene;
		this.button = button;
	}

	@Override
	public void execute() {
		scene.losMode();
		button.setActive(!button.getActive());
	}
}
