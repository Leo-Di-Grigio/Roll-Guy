package game.cycle.scene.game.world.map;

import game.cycle.scene.game.world.creature.Creature;
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
	
	public static Location loadLocation(int id){
		LocationProto proto = Database.getLocation(id);
		
		if(proto != null){
			String file = proto.filePath;
			try {
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
						int creature = buffer.getInt();
						int go = buffer.getInt();
					
						// terrain
						if(terrain != Const.invalidId){
							map[i][j].proto = Database.getTerrainProto(terrain);
						}
						else{
							map[i][j].proto = Database.getTerrainProto(1);
						}
					
						// creature
						if(creature != Const.invalidId){
							map[i][j].creature = new NPC();
							map[i][j].creature.sprite.setPosition(i*Location.tileSize, j*Location.tileSize);
						}
					
						// go
						if(go != Const.invalidId){
							map[i][j].go = GOFactory.getGo(go, i, j);
						}	
					}
				}
			
				// wrap
				Location loc = new Location();
				loc.sizeX = sizeX;
				loc.sizeY = sizeY;
				loc.map = map;
				loc.sprites = getSpriteSet();
				loc.proto = proto;
			
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
			out.write(convertToArray(loc, player));
			out.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static byte [] convertToArray(Location loc, Player player) {
		// buffer allaocate
		final int sizeX = loc.sizeX;
		final int sizeY = loc.sizeY;
		int nodeDataInt = 3;
		int capacity = 4*(sizeX*sizeY*nodeDataInt + 2);
		
		ByteBuffer buffer = ByteBuffer.allocate(capacity);
		ArrayList<GO> goBuffer = new ArrayList<GO>();
		ArrayList<Creature> creatureBuffer = new ArrayList<Creature>();
		
		// write
		buffer.putInt(sizeX);
		buffer.putInt(sizeY);
		
		for(int i = 0; i < sizeX; ++i){
			for(int j = 0; j < sizeY; ++j){
				Terrain node = loc.map[i][j];
				
				// terrain id
				buffer.putInt(node.proto.id);
				
				// creature
				if(node.creature == null){
					buffer.putInt(Const.invalidId);
				}
				else{
					if(node.creature.id != player.id){
						buffer.putInt(node.creature.id);
					}
					else{
						buffer.putInt(Const.invalidId);
						creatureBuffer.add(node.creature);
					}
				}
				
				// go
				if(node.go == null){
					buffer.putInt(Const.invalidId);
				}
				else{
					buffer.putInt(node.go.proto.id);
					goBuffer.add(node.go);
				}
			}
		}
		
		byte [] data = buffer.array();
		buffer.clear();
		buffer = null;
		return data;
	}
}
