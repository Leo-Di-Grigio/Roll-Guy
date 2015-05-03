package game.script.ui.app;

import cycle.scene.SceneMng;
import game.SceneGame;
import game.script.Script;

public class ui_NewGame implements Script {

	@Override
	public void execute() {
		SceneGame scene = new SceneGame();
		SceneMng.addScene(scene, SceneMng.SCENE_GAME);
		SceneMng.switchScene(SceneMng.SCENE_GAME);
	}
}
