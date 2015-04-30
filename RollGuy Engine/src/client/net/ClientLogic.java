package client.net;

import client.scenes.SceneMng;
import client.scenes.game.Location;
import client.scenes.game.SceneGame;
import common.net.Message;

public class ClientLogic {

	private Network net;
	private Location loc;

	public ClientLogic(Network net) {
		this.net = net;
	}

	public void readTCP(Message msg) {
		switch (msg.key) {
			case Message.SERVER_VERSION_CHECK_SUCCESS:
				checkVersionSuccess();
				break;
				
			case Message.SERVER_VERSION_CHECK_ERROR:
				checkVeresionError();
				break;
				
			case Message.SERVER_LOAD_GAME:
				loadState();
				break;
				
			case Message.SERVER_ADD_PLAYER_FULL:
				addPlayerFull(msg);
				break;
				
			case Message.SERVER_ADD_PLAYER:
				addPlayer(msg);
				break;
				
			case Message.SERVER_PLAYER_MOVE:
				movePlayer(msg);
				break;
		}
	}

	private void checkVersionSuccess() {
		net.showConnectionMenu();
	}

	private void checkVeresionError() {
		net.closeConnection();
		loc = null;
	}

	private void loadState() {
		loc = new Location(net);
		SceneMng.addScene(SceneMng.SCENE_GAME, new SceneGame(SceneMng.SCENE_GAME, net, loc));
		SceneMng.switchScene(SceneMng.SCENE_GAME);
	}
	
	private void addPlayer(Message msg) {
		loc.addPlayer(msg);
	}

	private void addPlayerFull(Message msg) {
		loc.addPlayerFull(msg);
	}
	
	private void movePlayer(Message msg) {
		loc.movePlayer(msg);
	}
}