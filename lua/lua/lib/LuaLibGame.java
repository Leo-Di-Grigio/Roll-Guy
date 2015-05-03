package lua.lib;

import cycle.scene.SceneMng;

public class LuaLibGame {

	public void endGame(){
		SceneMng.switchScene(SceneMng.SCENE_MENU);
		SceneMng.removeScene(SceneMng.SCENE_GAME);
	}
}
