package client.scenes.ui.script.list;

import server.Server;
import client.scenes.ui.script.UIScript;

public class ui_StartGame extends UIScript {

	private Server server;

	public ui_StartGame(Server server) {
		this.server = server;
	}

	@Override
	public void execute() {
		server.getLogic().loadState();
	}
}
