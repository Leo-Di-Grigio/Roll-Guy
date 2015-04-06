package game.script.ui.editor;

import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.location.Location;
import game.cycle.scene.game.world.location.LocationProto;
import game.cycle.scene.game.world.location.manager.LocationManager;
import game.cycle.scene.ui.list.UIGame;
import game.script.Script;
import game.tools.Log;

public class ui_LocationAdd implements Script {

	private UIGame ui;
	
	public ui_LocationAdd(UIGame ui) {
		this.ui = ui;
	}

	@Override
	public void execute() {
		String file = ui.locationCreate.file.getText();
		String title = ui.locationCreate.title.getText();
		String note = ui.locationCreate.note.getText();
		
		int sizeX = Integer.parseInt(ui.locationCreate.sizeX.getText());
		int sizeY = Integer.parseInt(ui.locationCreate.sizeY.getText());
		
		if(sizeX <= 1024 && sizeY <= 1024){
			int terrain = Integer.parseInt(ui.locationCreate.terrain.getText());
			
			LocationProto proto = new LocationProto();
			proto.id = Database.getBaseLocations().size();
			proto.title = title;
			proto.filePath = file;
			proto.note = note;
			Location location = LocationManager.createNew(proto, sizeX, sizeY, terrain);
			
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