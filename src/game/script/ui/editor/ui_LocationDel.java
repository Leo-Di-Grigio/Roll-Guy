package game.script.ui.editor;

import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.location.manager.LocationManager;
import game.cycle.scene.ui.list.UIGame;
import game.script.Script;
import game.tools.Const;

public class ui_LocationDel implements Script {

	private UIGame ui;
	
	public ui_LocationDel(UIGame ui) {
		this.ui = ui;
	}

	@Override
	public void execute() {
		int id = ui.getSelectedListLocation();
		
		if(id != Const.INVALID_ID && id != 0){
			if(LocationManager.deleteLocation(id)){
				Database.deleteLocation(id);
				Database.loadLocations();
				ui.location.loadLocationList();
			}
		}
	}
}