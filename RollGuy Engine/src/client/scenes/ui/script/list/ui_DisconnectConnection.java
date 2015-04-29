package client.scenes.ui.script.list;

import client.net.Network;
import client.scenes.ui.script.UIScript;

public class ui_DisconnectConnection extends UIScript {

	private Network net;

	public ui_DisconnectConnection(Network net) {
		this.net = net;
	}

	@Override
	public void execute() {
		net.closeConnection();
	}
}
