package game.script.ui.app;

import game.cycle.scene.game.SceneGame;
import game.script.Script;

public class ui_LOSMode implements Script {

	private SceneGame scene;

	public ui_LOSMode(SceneGame scene) {
		this.scene = scene;
	}

	@Override
	public void execute() {
		scene.losMode();
	}
}
