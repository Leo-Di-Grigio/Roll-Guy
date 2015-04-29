package client.scenes.list;

import client.net.Network;
import client.scenes.ui.Alignment;
import client.scenes.ui.UI;
import client.scenes.ui.script.list.ui_DisconnectConnection;
import client.scenes.ui.widget.Button;

public class UIMenuConnect extends UI {

	private static final String UI_DISCONNECT = "disconnect";

	public UIMenuConnect(Network net) {
		super();
		
		Button button = new Button(UI_DISCONNECT, "Disconnect");
		button.setVisible(true);
		button.setSize(128, 32);
		button.setPosition(Alignment.CENTER, 0, -36);
		button.setScript(new ui_DisconnectConnection(net));
		this.add(button);
	}

	@Override
	public void onload() {
		
	}

	@Override
	public void onclose() {
		
	}
}


