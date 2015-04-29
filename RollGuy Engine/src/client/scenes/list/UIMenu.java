package client.scenes.list;

import client.net.Network;
import client.scenes.ui.Alignment;
import client.scenes.ui.UI;
import client.scenes.ui.script.list.ui_Connect;
import client.scenes.ui.script.list.ui_Exit;
import client.scenes.ui.script.list.ui_ServerRun;
import client.scenes.ui.widget.Button;

public class UIMenu extends UI {

	private static final String UI_RUN_SERVER = "run-server";
	private static final String UI_CONNECT = "connect";
	private static final String UI_SETUP = "setup";
	private static final String UI_EXIT = "exit";
	
	public UIMenu(Network net) {
		super();
		
		Button button = new Button(UI_RUN_SERVER, "Create server");
		button.setVisible(true);
		button.setSize(128, 32);
		button.setPosition(Alignment.CENTER, 0, 0);
		button.setScript(new ui_ServerRun(net));
		this.add(button);
		
		button = new Button(UI_CONNECT, "Connect to");
		button.setVisible(true);
		button.setSize(128, 32);
		button.setPosition(Alignment.CENTER, 0, -36);
		button.setScript(new ui_Connect(net));
		this.add(button);
		
		button = new Button(UI_SETUP, "Setup");
		button.setVisible(true);
		button.setSize(128, 32);
		button.setPosition(Alignment.CENTER, 0, -72);
		this.add(button);
		
		button = new Button(UI_EXIT, "Exit");
		button.setVisible(true);
		button.setSize(128, 32);
		button.setPosition(Alignment.CENTER, 0, -108);
		button.setScript(new ui_Exit());
		this.add(button);
	}
	
	@Override
	public void onload() {
		
	}

	@Override
	public void onclose() {
		
	}
}