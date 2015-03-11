package game.script.ui.app;

import game.cycle.scene.SceneMng;
import game.cycle.scene.game.SceneGame;
import game.script.Script;

public class ui_NewGame implements Script {

	@Override
	public void execute() {
		SceneGame scene = new SceneGame();
		
		SceneMng.addScene(scene, SceneMng.sceneKeyGame);
		SceneMng.switchScene(SceneMng.sceneKeyGame);
	}
}
