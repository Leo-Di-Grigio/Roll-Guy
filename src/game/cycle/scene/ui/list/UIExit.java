package game.cycle.scene.ui.list;

import game.cycle.scene.SceneMng;
import game.cycle.scene.ui.UI;
import game.cycle.scene.ui.Widget.Alignment;
import game.cycle.scene.ui.widgets.Button;
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
		button.setScript(new ui_SwitchScene(SceneMng.sceneKeyMenu));
		this.add(button);
		
		button = new Button(uiExit, "Exit");
		button.setVisible(true);
		button.setSize(128, 32);
		button.setPosition(Alignment.CENTER, 200, -136);
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
