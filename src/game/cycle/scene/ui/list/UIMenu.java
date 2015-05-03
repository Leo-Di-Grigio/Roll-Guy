package game.cycle.scene.ui.list;

import ui.Alignment;
import ui.UI;
import ui.widgets.used.Button;
import game.cycle.scene.SceneMng;
import game.script.ui.app.ui_NewGame;
import game.script.ui.app.ui_SwitchScene;

public class UIMenu extends UI {

	public static final String NEW_GAME = "new-game";
	public static final String LOAD = "load-game";
	public static final String OPTIONS = "options";
	public static final String EXIT = "exit";
	
	public UIMenu() {
		super();
		
		Button button = new Button(NEW_GAME, "New Game");
		button.setVisible(true);
		button.setSize(128, 32);
		button.setPosition(Alignment.CENTER, 0, 0);
		button.setScript(new ui_NewGame());
		this.add(button);
		
		button = new Button(LOAD, "Load Game");
		button.setVisible(true);
		button.setSize(128, 32);
		button.setPosition(Alignment.CENTER, 0, -34);
		this.add(button);
		
		button = new Button(OPTIONS, "Options");
		button.setVisible(true);
		button.setSize(128, 32);
		button.setPosition(Alignment.CENTER, 0, -68);
		this.add(button);
		
		button = new Button(EXIT, "Exit");
		button.setVisible(true);
		button.setSize(128, 32);
		button.setPosition(Alignment.CENTER, 0, -136);
		button.setScript(new ui_SwitchScene(SceneMng.SCENE_EXIT));
		this.add(button);
	}
}
