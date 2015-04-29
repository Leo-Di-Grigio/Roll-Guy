package client.scenes.list;

import server.Server;
import client.net.Network;
import client.scenes.ui.Alignment;
import client.scenes.ui.UI;
import client.scenes.ui.script.list.ui_DisconnectServer;
import client.scenes.ui.script.list.ui_StartGame;
import client.scenes.ui.widget.Button;

public class UIMenuServer extends UI {

	private static final String UI_START = "start";
	private static final String UI_DISCONNECT = "disconnect";

	public UIMenuServer(Network net, Server server) {
		super();
		
		Button button = new Button(UI_START, "Start");
		button.setVisible(true);
		button.setSize(128, 32);
		button.setPosition(Alignment.CENTER, 0, 0);
		button.setScript(new ui_StartGame(server));
		this.add(button);
		
		button = new Button(UI_DISCONNECT, "Disconnect");
		button.setVisible(true);
		button.setSize(128, 32);
		button.setPosition(Alignment.CENTER, 0, -36);
		button.setScript(new ui_DisconnectServer(net));
		this.add(button);
	}
	
	@Override
	public void onload() {
		
	}

	@Override
	public void onclose() {

	}
}
