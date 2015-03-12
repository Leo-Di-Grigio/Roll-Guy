package game.script.ui.app;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.map.Location;
import game.cycle.scene.game.world.map.LocationLoader;
import game.cycle.scene.ui.list.UIGame;
import game.script.Script;

public class ui_LocationAdd implements Script {

	private UIGame ui;
	private SceneGame scene;
	
	public ui_LocationAdd(UIGame ui, SceneGame scene) {
		this.ui = ui;
		this.scene = scene;
	}

	@Override
	public void execute() {
		String file = "test";
		String title = "Test";
		String note = "Тестовая локация";
		
		Location location = LocationLoader.createNew(title, file, note, scene.getWorld().getPlayer());
		if(location != null){
			Database.insertLocation(location, title, file, note);
			Database.loadLocations();
			ui.loadLocationList();
		}
	}
}