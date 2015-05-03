package game.script.ui.app;

import ui.widgets.used.Button;
import game.SceneGame;
import game.script.Script;

public class ui_FreeCameraMode implements Script {

	private SceneGame scene;
	private Button button;
	
	public ui_FreeCameraMode(SceneGame scene, Button button) {
		this.scene = scene;
		this.button = button;
	}

	@Override
	public void execute() {
		scene.freeCameraMode();
		button.setActive(!button.getActive());
	}
}
