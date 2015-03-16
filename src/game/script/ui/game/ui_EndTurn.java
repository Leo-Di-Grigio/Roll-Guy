package game.script.ui.game;

import game.cycle.scene.game.SceneGame;
import game.script.Script;

public class ui_EndTurn implements Script {

	private SceneGame scene;

	public ui_EndTurn(SceneGame scene) {
		this.scene = scene;
	}

	@Override
	public void execute() {
		scene.endTurn();
	}
}
