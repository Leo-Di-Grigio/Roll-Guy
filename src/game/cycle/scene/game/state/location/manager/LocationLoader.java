package game.cycle.scene.game.state.location.manager;

import game.cycle.scene.game.state.database.Database;
import game.cycle.scene.game.state.database.GameConst;
import game.cycle.scene.game.state.database.proto.CreatureProto;
import game.cycle.scene.game.state.database.proto.LocationProto;
import game.cycle.scene.game.state.location.Editor;
import game.cycle.scene.game.state.location.Location;
import game.cycle.scene.game.state.location.LocationObject;
import game.cycle.scene.game.state.location.Node;
import game.cycle.scene.game.state.location.creature.NPC;
import game.cycle.scene.game.state.location.go.GO;
import game.cycle.scene.game.state.location.go.GOFactory;
import game.cycle.scene.game.state.location.lighting.LocationLighting;
import game.lua.LuaEngine;
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
				Location loc = new Location(map, proto);
				proto.setSize(sizeX, sizeY);
				
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
			// read file
			ByteBuffer buffer = wrapLocationFile(proto);
			
			// read buffer
			readMetaData(proto, buffer);
			Location loc = readTerrain(proto, buffer);
			readEnvironment(loc, buffer);
			readGO(loc, buffer);
			readCreatures(loc, buffer);
			
			// end reading
			Log.debug("Loading complete");
			return loc;
		}
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
	
	private static void readMetaData(LocationProto proto, ByteBuffer buffer) {
		// load
		int sizeX = buffer.getInt();
		int sizeY = buffer.getInt();
		proto.setSize(sizeX, sizeY);
		
		// editor GUID data
		LocationObject.setStartGUID(buffer.getInt());
	}
	
	private static Location readTerrain(LocationProto proto, ByteBuffer buffer) {
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
		
		return new Location(map, proto);
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
				
				// read script
				int scriptDataSize = buffer.getInt();
				if(scriptDataSize != Const.INVALID_ID){
					String script = "";
					for(int j = 0; j < scriptDataSize; ++j){
						script += buffer.getChar();
					}
					
					go.script = script;
				}
				else{
					go.script = Database.getGO(go.proto.id()).script();
				}
				
				LuaEngine.load(go.script);
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
					npc.setSpawnPosition(posx, posy);
					npc.setSpritePosition(posx*GameConst.TILE_SIZE, posy*GameConst.TILE_SIZE);
					loc.addObject(npc, posx, posy, true);
				}
					
				// read equipment
				{
					int head = buffer.getInt();
					int chest = buffer.getInt();
					int hand1 = buffer.getInt();
					int hand2 = buffer.getInt();
					
					npc.equipment().loadSlots(head, chest, hand1, hand2);
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
}