package game.cycle.scene.ui.list;

import game.cycle.scene.ui.UI;
import game.cycle.scene.ui.Widget.Alignment;
import game.cycle.scene.ui.widgets.Button;
import game.script.ui.app.ui_ExitGame;

public class UIGame extends UI {

	public static final String uiMainMenu = "main-menu";
	
	public UIGame() {
		super();
		
		Button button = new Button(uiMainMenu, "Main menu");
		button.visible = true;
		button.setSize(128, 32);
		button.setPosition(Alignment.UPRIGTH, 0, 0);
		button.setScript(new ui_ExitGame());
		this.add(button);
	}
	
	@Override
	public void onload() {
		
	}

	@Override
	public void onclose() {

	}
}
