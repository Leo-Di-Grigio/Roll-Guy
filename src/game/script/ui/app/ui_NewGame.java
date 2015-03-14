package game.script.ui.app;

import game.cycle.scene.SceneMng;
import game.cycle.scene.game.SceneGame;
import game.script.Script;
import game.script.game.event.GameEvents;

public class ui_NewGame implements Script {

	@Override
	public void execute() {
		SceneGame scene = new SceneGame();
		new GameEvents(scene);
		
		SceneMng.addScene(scene, SceneMng.sceneKeyGame);
		SceneMng.switchScene(SceneMng.sceneKeyGame);
	}
}
