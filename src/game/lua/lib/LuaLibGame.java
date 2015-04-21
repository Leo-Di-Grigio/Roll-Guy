package game.lua.lib;

import game.cycle.scene.SceneMng;

public class LuaLibGame {

	public void endGame(){
		SceneMng.switchScene(SceneMng.sceneKeyMenu);
		SceneMng.removeScene(SceneMng.sceneKeyGame);
	}
}
