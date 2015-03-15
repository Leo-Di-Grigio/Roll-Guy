package game.script.ui.editor;

import game.cycle.scene.game.SceneGame;
import game.script.Script;

public class ui_LocationSave implements Script {

	private SceneGame scene;
	
	public ui_LocationSave(SceneGame scene) {
		this.scene = scene;
	}

	@Override
	public void execute() {
		scene.saveLocation();
	}
}
