package game.cycle.scene.ui.list;

import cycle.scene.SceneMng;
import ui.Alignment;
import ui.UI;
import ui.widgets.used.Button;
import game.script.ui.app.ui_Exit;
import game.script.ui.app.ui_SwitchScene;

public class UIExit extends UI {

	public static final String uiCancel = "cancel";
	public static final String uiExit = "exit";
	
	public UIExit() {
		super();
		
		Button button = new Button(uiCancel, "Cancel");
		button.setVisible(true);
		button.setSize(128, 32);
		button.setPosition(Alignment.CENTER, -200, -136);
		button.setScript(new ui_SwitchScene(SceneMng.SCENE_MENU));
		this.add(button);
		
		button = new Button(uiExit, "Exit");
		button.setVisible(true);
		button.setSize(128, 32);
		button.setPosition(Alignment.CENTER, 200, -136);
		button.setScript(new ui_Exit());
		this.add(button);
	}
}
