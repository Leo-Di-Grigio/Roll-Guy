package game.script.ui.app;

import game.cycle.scene.SceneMng;
import game.script.Script;

public class ui_SwitchScene implements Script {

	private String sceneKey;
	
	public ui_SwitchScene(String key) {
		this.sceneKey = key;
	}
	
	@Override
	public void execute() {
		SceneMng.switchScene(sceneKey);
	}
}
