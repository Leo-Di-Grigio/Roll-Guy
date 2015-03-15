package game.script.ui.editor;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.map.Location;
import game.cycle.scene.game.world.map.LocationLoader;
import game.cycle.scene.game.world.map.LocationProto;
import game.cycle.scene.ui.list.UIGame;
import game.script.Script;
import game.tools.Log;

public class ui_LocationAdd implements Script {

	private UIGame ui;
	private SceneGame scene;
	
	public ui_LocationAdd(UIGame ui, SceneGame scene) {
		this.ui = ui;
		this.scene = scene;
	}

	@Override
	public void execute() {
		String file = ui.locationCreate.createLocationFile.getText();
		String title = ui.locationCreate.createLocationTitle.getText();
		String note = ui.locationCreate.createLocationNote.getText();
		
		int sizeX = Integer.parseInt(ui.locationCreate.createLocationSizeX.getText());
		int sizeY = Integer.parseInt(ui.locationCreate.createLocationSizeY.getText());
		
		if(sizeX <= 1024 && sizeY <= 1024){
			int terrain = Integer.parseInt(ui.locationCreate.createLocationStartTerrain.getText());
			
			LocationProto proto = new LocationProto();
			proto.id = Database.getBaseLocations().size();
			proto.title = title;
			proto.filePath = file;
			proto.note = note;
			Location location = LocationLoader.createNew(proto, sizeX, sizeY, terrain, scene.getWorld().getPlayer());
			
			if(location != null){
				Database.insertLocation(proto);
				Database.loadLocations();
				ui.location.loadLocationList();
				ui.setVisibleCreteLocation(false);
			}	
		}
		else{
			Log.err("ui_LocationAdd - invalid location size");
		}
	}
}