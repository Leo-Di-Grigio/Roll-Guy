package client.scenes.ui.script.list;

import client.scenes.ui.script.UIScript;

import com.badlogic.gdx.Gdx;

public class ui_Exit extends UIScript {

	@Override
	public void execute() {
		Gdx.app.exit();
	}
}
