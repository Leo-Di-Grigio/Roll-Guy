package game.script.ui.app;

import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.map.LocationLoader;
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
		
		if(id != Const.invalidId && id != 0){
			if(LocationLoader.deleteLocation(id)){
				Database.deleteLocation(id);
				Database.loadLocations();
				ui.loadLocationList();
			}
		}
	}
}