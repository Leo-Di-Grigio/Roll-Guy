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
			case UIGame.EDITOR_TERRAIN:
				ui.showTerrain();
				break;
				
			case UIGame.EDITOR_NPC:
				ui.showNpc();
				break;
				
			case UIGame.EDITOR_GO:
				ui.showGO();
				break;
				
			case UIGame.EDITOR_LOCATION:
				ui.showLocation();
				break;
				
			case UIGame.EDITOR_ITEM:
				ui.showItems();
				break;
				
			default:
				break;
		}
	}
}
