package client.net;

import client.scenes.SceneMng;
import client.scenes.game.SceneGame;
import common.net.Message;

public class ClientLogic {

	private Network net;

	public ClientLogic(Network net) {
		this.net = net;
	}

	public void read(Message msg) {
		switch (msg.key) {
			case Message.VERSION_CHECK_SUCCESS:
				checkVersionSuccess();
				break;
				
			case Message.VERSION_CHECK_ERROR:
				checkVeresionError();
				break;
				
			case Message.LOAD_GAME:
				loadState();
				break;
		}
	}

	private void checkVersionSuccess() {
		net.showConnectionMenu();
	}

	private void checkVeresionError() {
		net.closeConnection();
	}

	private void loadState() {
		SceneMng.addScene(SceneMng.SCENE_GAME, new SceneGame(SceneMng.SCENE_GAME, net));
		SceneMng.switchScene(SceneMng.SCENE_GAME);
	}
}