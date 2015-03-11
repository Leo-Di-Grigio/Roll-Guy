package game.cycle.scene.ui.list;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.ui.UI;
import game.cycle.scene.ui.Widget.Alignment;
import game.cycle.scene.ui.widgets.Button;
import game.script.ui.app.ui_ExitGame;
import game.script.ui.app.ui_GameClickMode;

public class UIGame extends UI {

	private SceneGame scene;
	
	// common UI
	public static final String uiMainMenu = "main-menu";
	
	// player UI
	public static final String uiPlayerMove = "player-move";
	
	// editor Ui
	public static final String uiEditor = "editor";
	
	public UIGame(SceneGame sceneGame) {
		super();
		this.scene = sceneGame;
		
		Button button = new Button(uiMainMenu, "Main menu");
		button.visible = true;
		button.setSize(128, 32);
		button.setPosition(Alignment.UPRIGTH, 0, 0);
		button.setScript(new ui_ExitGame());
		this.add(button);
		
		button = new Button(uiPlayerMove, "Move");
		button.visible = true;
		button.setSize(128, 32);
		button.setPosition(Alignment.DOWNRIGHT, 0, 0);
		button.setScript(new ui_GameClickMode(scene, SceneGame.clickPlayerMove));
		this.add(button);
		
		button = new Button(uiEditor, "Editor");
		button.visible = true;
		button.setSize(128, 32);
		button.setPosition(Alignment.UPRIGTH, 0, -34);
		button.setScript(new ui_GameClickMode(scene, SceneGame.clickEditor));
		this.add(button);
	}
	
	@Override
	public void onload() {
		
	}

	@Override
	public void onclose() {

	}
}
