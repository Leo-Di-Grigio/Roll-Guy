package game.script.ui.app;

import game.cycle.scene.SceneMng;
import game.script.Script;

public class ui_ExitGame implements Script {

	@Override
	public void execute() {
		SceneMng.switchScene(SceneMng.sceneKeyMenu);
		SceneMng.removeScene(SceneMng.sceneKeyGame);
	}
}
