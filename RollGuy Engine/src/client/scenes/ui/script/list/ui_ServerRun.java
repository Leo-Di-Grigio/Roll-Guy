package client.scenes.ui.script.list;

import client.net.Network;
import client.scenes.ui.script.UIScript;

public class ui_ServerRun extends UIScript {

	private Network net;

	public ui_ServerRun(Network net) {
		this.net = net;
	}
	
	@Override
	public void execute() {
		net.runServer();
	}
}