package game.cycle.scene.game.state.location;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.cycle.scene.game.state.database.GameConst;
import game.resources.tex.Tex;
import game.resources.tex.TexAtlas;

public class Chunk {
	
	private Node [][] nodes;
	
	private HashMap<Integer, LocationObject> objects;

	public Chunk(int x, int y, Node[][] map) {
		nodes = new Node[GameConst.MAP_CHUNK_SIZE][GameConst.MAP_CHUNK_SIZE];
		objects = new HashMap<Integer, LocationObject>();
		
		for(int i = 0; i < GameConst.MAP_CHUNK_SIZE; ++i){
			for(int j = 0; j < GameConst.MAP_CHUNK_SIZE; ++j){
				nodes[i][j] = map[x*GameConst.MAP_CHUNK_SIZE + i][y*GameConst.MAP_CHUNK_SIZE + j];
			}
		}
	}
	
	public void draw(SpriteBatch batch, int x, int y){
	
	}

	public boolean addObject(LocationObject object, float x, float y) {
		objects.put(object.getGUID(), object);
		return true;
	}
}