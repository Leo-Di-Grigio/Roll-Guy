package game.script.ui.app;

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
		
		if(id != Const.invalidId){
			scene.loadLocation(id, 0, 0);
		}
	}
}
