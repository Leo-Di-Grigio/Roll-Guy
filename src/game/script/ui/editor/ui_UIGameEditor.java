package game.script.ui.editor;

import game.cycle.scene.ui.list.UIGame;
import game.script.Script;

public class ui_UIGameEditor implements Script {
	
	private UIGame ui;
	private String option;
	
	public ui_UIGameEditor(UIGame ui, String option) {
		this.ui = ui;
		this.option = option;
	}

	@Override
	public void execute() {
		switch(option) {
			case UIGame.uiEditorTerrain:
				ui.showTerrain();
				break;
				
			case UIGame.uiEditorNpc:
				ui.showNpc();
				break;
				
			case UIGame.uiEditorGO:
				ui.showGO();
				break;
				
			case UIGame.uiEditorLocation:
				ui.showLocation();
				break;
				
			default:
				break;
		}
	}
}
