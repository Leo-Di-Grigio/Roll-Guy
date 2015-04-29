package client.scenes.ui.widget.window;

import client.resources.Resources;
import client.scenes.game.SceneGame;
import client.scenes.ui.Alignment;
import client.scenes.ui.UI;
import client.scenes.ui.script.list.ui_ExitGame;
import client.scenes.ui.widget.Button;
import client.scenes.ui.widget.Window;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import common.resources.Tex;

public class WindowTools extends Window {
	
	// common UI
	public static final String uiMainMenu = "main-menu";
	
	public Button mainMenu;
	
	public WindowTools(String title, UI ui, int layer, SceneGame scene) {
		super(title, ui, Alignment.UPRIGTH, 128, 24, 0, 0, layer);
		this.setTexNormal(Resources.getTex(Tex.UI_LIST_LINE));
		
		loadWidgets(scene);
		setVisible(true);
	}

	private void loadWidgets(SceneGame scene) {
		this.lockButton(true);
		
		mainMenu = new Button(uiMainMenu, "Main menu");
		mainMenu.setVisible(true);
		mainMenu.setSize(128, 32);
		mainMenu.setPosition(Alignment.UPLEFT, 0, -24);
		mainMenu.setScript(new ui_ExitGame(scene));
		this.add(mainMenu);
	}
	
	@Override
	public void draw(SpriteBatch sprites) {
		super.draw(sprites);
	}
}
