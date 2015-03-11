package game.cycle.scene.game.world.map;

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
		int sizeX = 45;
		int sizeY = 28;
		Node [][] map = new Node[sizeX][sizeY];
		
		for(int i = 0; i < sizeX; ++i){
			for(int j = 0; j < sizeY; ++j){
				map[i][j] = new Node();
				map[i][j].movement = true;
			}
		}
		
		Sprite [] sprites = new Sprite[2];
		sprites[0] = new Sprite(Resources.getTex(Tex.tileGrass));
		sprites[1] = new Sprite(Resources.getTex(Tex.tileWall));
		
		// wrap
		Location loc = new Location();
		loc.sizeX = sizeX;
		loc.sizeY = sizeY;
		loc.map = map;
		loc.sprites = sprites;
		
		return loc;
	}
}
