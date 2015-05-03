package game.script.ui.app;

import com.badlogic.gdx.Gdx;

import game.script.Script;

public class ui_Exit implements Script {

	@Override
	public void execute() {
		Gdx.app.exit();
	}
}
