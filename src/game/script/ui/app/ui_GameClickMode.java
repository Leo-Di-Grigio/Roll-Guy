package game.script.ui.app;

import game.cycle.scene.game.SceneGame;
import game.script.Script;

public class ui_GameClickMode implements Script {
	
	private SceneGame scene;
	private int mode;
	
	public ui_GameClickMode(SceneGame scene, int mode) {
		this.scene = scene;	
		this.mode = mode;
	}

	@Override
	public void execute() {
		scene.clickMode(mode);
	}
}
