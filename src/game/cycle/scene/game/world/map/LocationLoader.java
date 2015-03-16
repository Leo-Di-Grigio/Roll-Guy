package game.cycle.scene.game.world.map;

import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.game.world.creature.CreatureProto;
import game.cycle.scene.game.world.creature.NPC;
import game.cycle.scene.game.world.creature.Player;
import game.cycle.scene.game.world.database.Database;
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
				loc.map = map;
				loc.sizeX = sizeX;
				loc.sizeY = sizeY;
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
						npc.sprite.setPosition(posx*Location.tileSize, posy*Location.tileSize);
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
		return sprites;
	}

	public static Location createNew(LocationProto proto, int sizeX, int sizeY, int terrain, Player player) {
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
				loc.sizeX = sizeX;
				loc.sizeY = sizeY;
				loc.map = map;
				loc.sprites = getSpriteSet();
				loc.proto = proto;
				
				LocationLoader.saveLocation(loc, player);
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
	
	public static void saveLocation(Location loc, Player player){
		try {
			File file = new File(locationPath + loc.proto.filePath + locationFileExtension);
			if(!file.exists()){
				file.createNewFile();
			}
			
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
			writeLocation(loc, player, out);
			out.flush();
			out.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void writeLocation(Location loc, Player player, BufferedOutputStream out) throws IOException {
		// buffer allaocate
		final int sizeX = loc.sizeX;
		final int sizeY = loc.sizeY;
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
				if(node.creature != null && node.creature.id != player.id){
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
		int creatureDataInt = 9; // charId, x, y, (str, agi, stamina, pre, int, will)
		capacity = 4*(goBuffer.size()*goDataInt + creatureBuffer.size()*creatureDataInt + 4);
		buffer = ByteBuffer.allocate(capacity);
		
		buffer.putInt(locGOKey);
		buffer.putInt(goBuffer.size());
		
		for(GO go: goBuffer){
			buffer.putInt(go.proto.id);
			buffer.putInt((int)(go.sprite.getX()/Location.tileSize));
			buffer.putInt((int)(go.sprite.getY()/Location.tileSize));
			buffer.putInt(go.param1); // param1
			buffer.putInt(go.param2); // param2
			buffer.putInt(go.param3); // param3
			buffer.putInt(go.param4); // param4
		}
		
		// Write Creature
		buffer.putInt(locCreatureKey);
		buffer.putInt(creatureBuffer.size());
		
		for(Creature creature: creatureBuffer){
			buffer.putInt(creature.id);
			buffer.putInt((int)(creature.sprite.getX()/Location.tileSize));
			buffer.putInt((int)(creature.sprite.getY()/Location.tileSize));
			buffer.putInt(creature.proto.id);
		}
		
		data = buffer.array();
		out.write(data);
		buffer.clear();
		buffer = null;
		data = null;
	}
}
