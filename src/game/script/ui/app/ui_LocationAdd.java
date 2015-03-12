package game.script.ui.app;

import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.map.Location;
import game.cycle.scene.game.world.map.LocationLoader;
import game.cycle.scene.ui.list.UIGame;
import game.script.Script;

public class ui_LocationAdd implements Script {

	private UIGame ui;
	
	public ui_LocationAdd(UIGame ui) {
		this.ui = ui;
	}

	@Override
	public void execute() {
		Location location = LocationLoader.createNew();
		Database.insertLocation(location, "Test", "test", "Тестовая локация");
		Database.loadLocations();
		ui.loadLocationList();
	}
}
