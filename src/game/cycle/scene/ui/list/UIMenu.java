package game.cycle.scene.ui.list;

import game.cycle.scene.SceneMng;
import game.cycle.scene.ui.UI;
import game.cycle.scene.ui.Widget.Alignment;
import game.cycle.scene.ui.widgets.Button;
import game.script.ui.app.ui_NewGame;
import game.script.ui.app.ui_SwitchScene;

public class UIMenu extends UI {

	public static final String uiNewGame = "new-game";
	public static final String uiLoad = "load-game";
	public static final String uiOptions = "options";
	public static final String uiExit = "exit";
	
	public UIMenu() {
		super();
		
		Button button = new Button(uiNewGame, "New Game");
		button.setVisible(true);
		button.setSize(128, 32);
		button.setPosition(Alignment.CENTER, 0, 0);
		button.setScript(new ui_NewGame());
		this.add(button);
		
		button = new Button(uiLoad, "Load Game");
		button.setVisible(true);
		button.setSize(128, 32);
		button.setPosition(Alignment.CENTER, 0, -34);
		this.add(button);
		
		button = new Button(uiOptions, "Options");
		button.setVisible(true);
		button.setSize(128, 32);
		button.setPosition(Alignment.CENTER, 0, -68);
		this.add(button);
		
		button = new Button(uiExit, "Exit");
		button.setVisible(true);
		button.setSize(128, 32);
		button.setPosition(Alignment.CENTER, 0, -136);
		button.setScript(new ui_SwitchScene(SceneMng.sceneKeyExit));
		this.add(button);
	}
	
	@Override
	public void onload() {
		
	}

	@Override
	public void onclose() {
		
	}
}
