package game.script.ui.editor;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.ui.list.UIGame;
import game.script.Script;
import game.tools.Const;

public class ui_LocationLoad implements Script {

	private UIGame ui;
	private SceneGame scene;
	
	public ui_LocationLoad(UIGame ui, SceneGame scene) {
		this.ui = ui;
		this.scene = scene;
	}

	@Override
	public void execute() {
		int id = ui.getSelectedListLocation();
		
		if(id != Const.INVALID_ID){
			scene.loadLocation(id, 0, 0);
		}
	}
}
