package game.cycle.scene.game.world.location.manager;

import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.database.GameConst;
import game.cycle.scene.game.world.database.proto.CreatureProto;
import game.cycle.scene.game.world.database.proto.LocationProto;
import game.cycle.scene.game.world.location.Editor;
import game.cycle.scene.game.world.location.Location;
import game.cycle.scene.game.world.location.LocationObject;
import game.cycle.scene.game.world.location.Node;
import game.cycle.scene.game.world.location.creature.NPC;
import game.cycle.scene.game.world.location.go.GO;
import game.cycle.scene.game.world.location.go.GOFactory;
import game.cycle.scene.game.world.location.lighting.LocationLighting;
import game.resources.Resources;
import game.tools.Const;
import game.tools.Log;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocationLoader {
	
	protected static Location createNew(LocationProto proto, int sizeX, int sizeY, int terrain) {
		String fullPath = LocationManager.locationPath + proto.file() + LocationManager.locationFileExtension;
		File file = new File(fullPath);
		
		if(!file.exists()){
			try {
				file.createNewFile();
				
				// test data
				Node [][] map = new Node[sizeX][sizeY];
			
				for(int i = 0; i < sizeX; ++i){
					for(int j = 0; j < sizeY; ++j){
						map[i][j] = new Node();
						map[i][j].proto = Database.getTerrainProto(terrain);
					}
				}
				
				// wrap
				Location loc = new Location();
				proto.setSize(sizeX, sizeY);
				loc.map = map;
				loc.sprites = Resources.getLocationSpriteSet();
				loc.proto = proto;
				
				LocationWriter.saveLocation(loc);
				return loc;
			}
			catch (IOException e) {
				Log.err("Error: cant create or write Location file " + fullPath);
				return null;
			}
		}
		else{
			return null;
		}
	}
	
	protected static Location loadLocation(int id){
		// search file path at "data.db"
		LocationProto proto = Database.getLocation(id);
		
		if(proto == null){
			return null;
		}
		else{
			Location loc = new Location();
			
			// read file
			ByteBuffer buffer = wrapLocationFile(proto);
			
			// read buffer
			readMetaData(loc, proto, buffer);
			readTerrain(loc, proto, buffer);
			readEnvironment(loc, buffer);
			readGO(loc, buffer);
			readCreatures(loc, buffer);
			
			// end reading
			Log.debug("Loading complete");
			
			return loc;	
		}
	}
	
	private static void readMetaData(Location loc, LocationProto proto, ByteBuffer buffer) {
		// load
		int sizeX = buffer.getInt();
		int sizeY = buffer.getInt();
		proto.setSize(sizeX, sizeY);
		
		// editor GUID data
		LocationObject.setStartGUID(buffer.getInt());
	}
	
	private static ByteBuffer wrapLocationFile(LocationProto proto) {
		try {
			Path path = Paths.get(LocationManager.locationPath + proto.file() + LocationManager.locationFileExtension);
			byte [] array = Files.readAllBytes(path);
			return ByteBuffer.wrap(array);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static void readTerrain(Location loc, LocationProto proto, ByteBuffer buffer) {
		// load
		Node [][] map = new Node[proto.sizeX()][proto.sizeY()];
		for(int i = 0; i < proto.sizeX(); ++i){
			for(int j = 0; j < proto.sizeY(); ++j){
				map[i][j] = new Node();
				int terrain = buffer.getInt();
			
				// terrain
				if(terrain != Const.INVALID_ID){
					map[i][j].proto = Database.getTerrainProto(terrain);
				}
				else{
					map[i][j].proto = Database.getTerrainProto(1);
				}	
			}
		}
		
		loc.map = map;
		loc.proto = proto;
		loc.sprites = Resources.getLocationSpriteSet();
	}

	private static void readGO(Location loc, ByteBuffer buffer) {
		// read GO block
		int goKey = buffer.getInt();
		int goCount = buffer.getInt();
		
		if(goKey == LocationManager.LOCATION_GO_DATA_BLOCK){
			Log.debug("Load GO...");
			
			for(int i = 0; i < goCount; ++i){
				// read go data
				int guid = buffer.getInt();
				int protoId = buffer.getInt();
				int posx = buffer.getInt();
				int posy = buffer.getInt();
				int param1 = buffer.getInt();
				int param2 = buffer.getInt();
				int param3 = buffer.getInt();
				int param4 = buffer.getInt();
				
				// place go at location
				GO go = GOFactory.getGo(guid, protoId, posx, posy, param1, param2, param3, param4);
				Editor.goAdd(loc, go, posx, posy);
				
				// read triggers
				{
					for(int j = 0; j < GameConst.GO_TRIGGERS_COUNT; ++j){
						go.triggerType[j] = buffer.getInt();
						go.triggerParam[j] = buffer.getInt();
						go.scripts[j] = buffer.getInt();
						go.params1[j] = buffer.getInt();
						go.params2[j] = buffer.getInt();
						go.params3[j] = buffer.getInt();
						go.params4[j] = buffer.getInt();
					}
					go.loadTriggers();
				}
				
				// read container
				{
					int containerSize = buffer.getInt();
					if(containerSize != Const.INVALID_ID){
						for(int j = 0; j < containerSize; ++j){
							int itemId = buffer.getInt();
							int itemX = buffer.getInt();
							int itemY = buffer.getInt();
						
							loc.map[posx][posy].go.inventory.addItem(itemId, itemX, itemY);
						}
					}
				}
			}
		}
		else{
			Log.debug("GO block is broken");
		}
	}

	private static void readCreatures(Location loc, ByteBuffer buffer) {
		// read creatures block
		int creaturesKey = buffer.getInt();
		int creatursCount = buffer.getInt();
		
		if(creaturesKey == LocationManager.LOCATION_CREATURE_DATA_BLOCK){
			Log.debug("Load Creatures...");
			
			for(int i = 0; i < creatursCount; ++i){
				// read creature data
				int guid = buffer.getInt();
				int posx = buffer.getInt();
				int posy = buffer.getInt();
				int protoid = buffer.getInt();
			
				// build creature
				CreatureProto creatureProto = Database.getCreature(protoid);
				NPC npc = new NPC(guid, creatureProto);
				{
					npc.setPosition(posx, posy);
					npc.setSpawnPosition(posx, posy);
					npc.setSpritePosition(posx*GameConst.TILE_SIZE, posy*GameConst.TILE_SIZE);
					loc.addCreature(npc, posx, posy);
				}
					
				// read equipment
				{
					int head = buffer.getInt();
					int chest = buffer.getInt();
					int hand1 = buffer.getInt();
					int hand2 = buffer.getInt();
					
					npc.equipment.loadSlots(head, chest, hand1, hand2);
				}
				
				// read inventory
				int itemsCount = buffer.getInt();
				for(int j = 0; j < itemsCount; ++j){
					int itemId = buffer.getInt();
					int itemX = buffer.getInt();
					int itemY = buffer.getInt();
					
					npc.inventory.addItem(itemId, itemX, itemY);
				}
				
				// waypoints data
				int size = buffer.getInt();
				if(size != Const.INVALID_ID){
					int [] arr = new int[size];
					
					for(int j = 0; j < arr.length; ++j){
						arr[j] = buffer.getInt();
					}
					
					npc.aidata.setWayPointsIntArray(loc, arr);
				}
			}
		}
	}
	
	private static void readEnvironment(Location loc, ByteBuffer buffer) {
		// read evn block
		int envKey = buffer.getInt();
		int lightingsCount = buffer.getInt();
		
		loc.proto.setLight(buffer.getInt());
		
		if(envKey == LocationManager.LOCATION_ENVIRONMENT_DATA_BLOCK){
			Log.debug("Load environment...");
			
			for(int i = 0; i < lightingsCount; ++i){
				int key = buffer.getInt();
				int x0 = buffer.getInt();
				int y0 = buffer.getInt();
				int x1 = buffer.getInt();
				int y1 = buffer.getInt();
				int power = buffer.getInt();
				LocationLighting.addArea(loc, key, x0, y0, x1, y1, power);
			}
			
			loc.requestUpdate();
		}
	}
}