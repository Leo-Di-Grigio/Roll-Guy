package game.script.ui.app;

import cycle.scene.SceneMng;
import game.script.Script;

public class ui_ExitGame implements Script {

	@Override
	public void execute() {
		SceneMng.switchScene(SceneMng.SCENE_MENU);
		SceneMng.removeScene(SceneMng.SCENE_GAME);
	}
}
