package game.state.location.manager;

import game.state.database.Database;
import game.state.database.proto.LocationProto;
import game.state.location.Location;

import java.io.File;

public class LocationManager {
	
	// file default path
	protected static final String locationPath = "data/locations/";
	protected static final String locationFileExtension = ".loc";
	
	// file data blocks
	protected static final int LOCATION_GO_DATA_BLOCK = Integer.MAX_VALUE - 1;
	protected static final int LOCATION_CREATURE_DATA_BLOCK = Integer.MAX_VALUE - 2;
	protected static final int LOCATION_ENVIRONMENT_DATA_BLOCK = Integer.MAX_VALUE - 3;
	
	public static Location loadLocation(int id){
		return LocationLoader.loadLocation(id);
	}
	
	public static Location createNew(LocationProto proto, int sizeX, int sizeY, int terrain) {
		return LocationLoader.createNew(proto, sizeX, sizeY, terrain);
	}
	
	public static void saveLocation(Location loc){
		LocationWriter.saveLocation(loc);
	}
	
	public static boolean deleteLocation(int id) {
		String filePath = Database.getLocation(id).file();
		String fullPath = LocationManager.locationPath + filePath + LocationManager.locationFileExtension;
		File file = new File(fullPath);
		
		if(!file.exists()){
			return true;
		}
		else{
			file.delete();
			return true;
		}
	}
}
