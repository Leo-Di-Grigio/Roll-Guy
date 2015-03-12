package game.cycle.scene.game.world.map;

import game.cycle.scene.game.world.database.Database;
import game.resources.Resources;
import game.resources.Tex;
import game.tools.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class LocationLoader {

	private static final String locationPath = "data/locations/";
	private static final String locationFileExtension = ".loc";
	
	public static Location loadMap(String file){
		try {
			File titles = new File(locationPath + file + locationFileExtension);
			Scanner in = new Scanner(titles);
			
			// read file here
			
			// ..
			
			in.close();
		}
		catch (FileNotFoundException e) {
			Log.err(locationPath + file + " does not exist");
		}
	
		// test data
		int sizeX = 100;
		int sizeY = 100;
		Terrain [][] map = new Terrain[sizeX][sizeY];
		
		for(int i = 0; i < sizeX; ++i){
			for(int j = 0; j < sizeY; ++j){
				map[i][j] = new Terrain();
				map[i][j].proto = Database.getTerrainProto(1);
			}
		}
		
		Sprite [] sprites = new Sprite[10];
		sprites[0] = new Sprite(Resources.getTex(Tex.tileNull));
		sprites[1] = new Sprite(Resources.getTex(Tex.tileGrass));
		sprites[2] = new Sprite(Resources.getTex(Tex.tileWall));
		
		// wrap
		Location loc = new Location();
		loc.sizeX = sizeX;
		loc.sizeY = sizeY;
		loc.map = map;
		loc.sprites = sprites;
		
		return loc;
	}

	public static Location createNew() {
		// test data
		int sizeX = 100;
		int sizeY = 100;
		Terrain [][] map = new Terrain[sizeX][sizeY];
			
		for(int i = 0; i < sizeX; ++i){
			for(int j = 0; j < sizeY; ++j){
				map[i][j] = new Terrain();
				map[i][j].proto = Database.getTerrainProto(1);
			}
		}
				
		Sprite [] sprites = new Sprite[10];
		sprites[0] = new Sprite(Resources.getTex(Tex.tileNull));
		sprites[1] = new Sprite(Resources.getTex(Tex.tileGrass));
		sprites[2] = new Sprite(Resources.getTex(Tex.tileWall));
				
		// wrap
		Location loc = new Location();
		loc.sizeX = sizeX;
		loc.sizeY = sizeY;
		loc.map = map;
		loc.sprites = sprites;
				
		return loc;
	}
}
