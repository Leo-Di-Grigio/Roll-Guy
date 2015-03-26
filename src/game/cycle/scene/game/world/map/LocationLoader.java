package game.cycle.scene.game.world.map;

import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.game.world.creature.CreatureProto;
import game.cycle.scene.game.world.creature.NPC;
import game.cycle.scene.game.world.creature.items.Item;
import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.database.GameConst;
import game.cycle.scene.game.world.go.GO;
import game.cycle.scene.game.world.go.GOFactory;
import game.resources.Resources;
import game.resources.Tex;
import game.tools.Const;
import game.tools.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class LocationLoader {

	private static final String locationPath = "data/locations/";
	private static final String locationFileExtension = ".loc";
	
	// file keys
	private static final int locGOKey = Integer.MAX_VALUE - 1;
	private static final int locCreatureKey = Integer.MAX_VALUE - 2;
	
	@SuppressWarnings("unused")
	public static Location loadLocation(int id){
		LocationProto proto = Database.getLocation(id);
		
		if(proto != null){
			String file = proto.filePath;
			try {
				Location loc = new Location();
				Path path = Paths.get(locationPath + file + locationFileExtension);
				byte[] array = Files.readAllBytes(path);
				ByteBuffer buffer = ByteBuffer.wrap(array);
			
				// size
				int sizeX = buffer.getInt();
				int sizeY = buffer.getInt();
			
				// nodes
				Terrain [][] map = new Terrain[sizeX][sizeY];
				for(int i = 0; i < sizeX; ++i){
					for(int j = 0; j < sizeY; ++j){
						map[i][j] = new Terrain();
						int terrain = buffer.getInt();
					
						// terrain
						if(terrain != Const.invalidId){
							map[i][j].proto = Database.getTerrainProto(terrain);
						}
						else{
							map[i][j].proto = Database.getTerrainProto(1);
						}	
					}
				}
				// wrap

				proto.sizeX = sizeX;
				proto.sizeY = sizeY;
				loc.map = map;
				loc.proto = proto;
				loc.sprites = getSpriteSet();
				
				// read GO
				int goKey = buffer.getInt();
				int goSize = buffer.getInt();
				
				if(goKey == locGOKey){
					Log.debug("Read GO blocks");
					for(int i = 0; i < goSize; ++i){
						int protoId = buffer.getInt();
						int posx = buffer.getInt();
						int posy = buffer.getInt();
						int param1 = buffer.getInt();
						int param2 = buffer.getInt();
						int param3 = buffer.getInt();
						int param4 = buffer.getInt();
					
						map[posx][posy].go = GOFactory.getGo(protoId, posx, posy, param1, param2, param3, param4);
						
						int inventorySize = buffer.getInt();
						if(inventorySize != Const.invalidId){
							for(int j = 0; j < inventorySize; ++j){
								int itemId = buffer.getInt();
								int itemX = buffer.getInt();
								int itemY = buffer.getInt();
								
								map[posx][posy].go.inventory.addItem(itemId, itemX, itemY);
							}
						}
					}
				}
				else{
					Log.debug("GO blocks is broken");
				}
				
				// read Creature
				int creaturesKey = buffer.getInt();
				int creatursSize = buffer.getInt();
				
				if(creaturesKey == locCreatureKey){
					Log.debug("Read Creatures blocks");
					for(int i = 0; i < creatursSize; ++i){
						int creatureGuidId = buffer.getInt();
						int posx = buffer.getInt();
						int posy = buffer.getInt();
						int protoid = buffer.getInt();
					
						CreatureProto creatureProto = Database.getCreature(protoid);
						NPC npc = new NPC(creatureProto);
						loc.addCreature(npc, posx, posy);
						npc.setPosition(posx, posy);
						npc.setSpritePosition(posx*GameConst.tileSize, posy*GameConst.tileSize);
							
						// load equipment
						int head = buffer.getInt();
						if(head != Const.invalidId)
							npc.equipment.head = new Item(Database.getItem(head));

						int chest = buffer.getInt();
						if(chest != Const.invalidId)
							npc.equipment.chest = new Item(Database.getItem(chest));

						int hand1 = buffer.getInt();
						if(hand1 != Const.invalidId)
							npc.equipment.hand1 = new Item(Database.getItem(hand1));

						int hand2 = buffer.getInt();
						if(hand2 != Const.invalidId)
							npc.equipment.hand2 = new Item(Database.getItem(hand2));
						
						// load inventory
						int items = buffer.getInt();
						for(int j = 0; j < items; ++j){
							int itemId = buffer.getInt();
							int itemX = buffer.getInt();
							int itemY = buffer.getInt();
							
							npc.inventory.addItem(itemId, itemX, itemY);
						}
					}
				}
				else{
					Log.debug("Creatures block is broken");
				}
				Log.debug("go: " + goSize + " creatures: " + creatursSize);
				
				return loc;
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private static Sprite [] getSpriteSet(){
		Sprite [] sprites = new Sprite[10];
		sprites[0] = new Sprite(Resources.getTex(Tex.tileNull));
		sprites[1] = new Sprite(Resources.getTex(Tex.tileGrass));
		sprites[2] = new Sprite(Resources.getTex(Tex.tileWall));
		sprites[3] = new Sprite(Resources.getTex(Tex.tileWater));
		sprites[9] = new Sprite(Resources.getTex(Tex.tileFog));
		return sprites;
	}

	public static Location createNew(LocationProto proto, int sizeX, int sizeY, int terrain) {
		String fullPath = locationPath + proto.filePath + locationFileExtension;
		File file = new File(fullPath);
		
		if(!file.exists()){
			try {
				file.createNewFile();
				
				// test data
				Terrain [][] map = new Terrain[sizeX][sizeY];
			
				for(int i = 0; i < sizeX; ++i){
					for(int j = 0; j < sizeY; ++j){
						map[i][j] = new Terrain();
						map[i][j].proto = Database.getTerrainProto(terrain);
					}
				}
				
				// wrap
				Location loc = new Location();
				proto.sizeX = sizeX;
				proto.sizeY = sizeY;
				loc.map = map;
				loc.sprites = getSpriteSet();
				loc.proto = proto;
				
				LocationLoader.saveLocation(loc);
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

	public static boolean deleteLocation(int id) {
		String filePath = Database.getLocation(id).filePath;
		String fullPath = locationPath + filePath + locationFileExtension;
		File file = new File(fullPath);
		
		if(!file.exists()){
			return true;
		}
		else{
			file.delete();
			return true;
		}
	}
	
	public static void saveLocation(Location loc){
		try {
			File file = new File(locationPath + loc.proto.filePath + locationFileExtension);
			if(!file.exists()){
				file.createNewFile();
			}
			
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
			writeLocation(loc, out);
			out.flush();
			out.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void writeLocation(Location loc, BufferedOutputStream out) throws IOException {
		// buffer allaocate
		final int sizeX = loc.proto.sizeX;
		final int sizeY = loc.proto.sizeY;
		int nodeDataInt = 1; // terrainId
		int capacity = 4*(sizeX*sizeY*nodeDataInt + 2);
		
		// Buffers
		ByteBuffer buffer = ByteBuffer.allocate(capacity);
		ArrayList<GO> goBuffer = new ArrayList<GO>();
		ArrayList<Creature> creatureBuffer = new ArrayList<Creature>();
		
		// Write Terrain
		buffer.putInt(sizeX);
		buffer.putInt(sizeY);
		
		for(int i = 0; i < sizeX; ++i){
			for(int j = 0; j < sizeY; ++j){
				Terrain node = loc.map[i][j];
				
				// terrain id
				buffer.putInt(node.proto.id);
				
				// creature
				if(node.creature != null && !node.creature.isPlayer()){
					creatureBuffer.add(node.creature);
				}
				
				// go
				if(node.go != null){
					goBuffer.add(node.go);
				}
			}
		}
		
		byte [] data = buffer.array();
		out.write(data);
		buffer.clear();
		buffer = null;
		data = null;
		
		// Write GO
		int goDataInt = 7; // goId, x, y, param1, param2, param3, param4
		int creatureDataInt = 13; // charId,x,y,str,agi,stamina,pre,int,will,equipment(head, chest, h1, h2)
		int inventoryDataInt = 0;
		for(Creature creature: creatureBuffer){
			inventoryDataInt += creature.inventory.getItemsCount()*3 + 1; // baseid, x, y + inventorySize 
		}
		for(GO go: goBuffer){
			if(go.proto.container){
				inventoryDataInt += go.inventory.getItemsCount()*3 + 1; // baseid, x, y + inventorySize
			}
			else{
				inventoryDataInt += 1; // inventorySize = Const.invalidId
			}
		}
		
		capacity = 4*(goBuffer.size()*goDataInt + creatureBuffer.size()*creatureDataInt + inventoryDataInt + 4);
		buffer = ByteBuffer.allocate(capacity);
		
		buffer.putInt(locGOKey);
		buffer.putInt(goBuffer.size());
		
		for(GO go: goBuffer){
			buffer.putInt(go.proto.id);
			buffer.putInt((int)(go.getPosition().x));
			buffer.putInt((int)(go.getPosition().y));
			buffer.putInt(go.param1); // param1
			buffer.putInt(go.param2); // param2
			buffer.putInt(go.param3); // param3
			buffer.putInt(go.param4); // param4
			
			if(go.proto.container){
				int [] inventory = go.inventory.getIntArray();
				buffer.putInt(inventory[0]);
				for(int i = 1; i < inventory.length; i += 3){
					buffer.putInt(inventory[i]);
					buffer.putInt(inventory[i+1]);
					buffer.putInt(inventory[i+2]);
				}
			}
			else{
				buffer.putInt(Const.invalidId);
			}
		}
		
		// Write Creature
		buffer.putInt(locCreatureKey);
		buffer.putInt(creatureBuffer.size());
		
		for(Creature creature: creatureBuffer){
			buffer.putInt(creature.getId());
			buffer.putInt((int)(creature.getPosition().x));
			buffer.putInt((int)(creature.getPosition().y));
			buffer.putInt(creature.proto.id);
			
			// write equipment
			int [] equpment = creature.equipment.getIntArray();
			for(int i = 0; i < equpment.length; ++i){
				buffer.putInt(equpment[i]);
			}
			
			// write inventory
			int [] inventory = creature.inventory.getIntArray();
			buffer.putInt(inventory[0]);
			for(int i = 1; i < inventory.length; i += 3){
				buffer.putInt(inventory[i]);
				buffer.putInt(inventory[i+1]);
				buffer.putInt(inventory[i+2]);
			}
		}
		
		data = buffer.array();
		out.write(data);
		buffer.clear();
		buffer = null;
		data = null;
	}
}
