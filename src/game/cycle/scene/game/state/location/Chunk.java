package game.cycle.scene.game.state.location;

import game.cycle.scene.game.state.database.GameConst;

public class Chunk {
	
	private Node [][] nodes;

	public Chunk(int x, int y, Node[][] map) {
		nodes = new Node[GameConst.MAP_CHUNK_SIZE][GameConst.MAP_CHUNK_SIZE];
		
		for(int i = 0; i < GameConst.MAP_CHUNK_SIZE; ++i){
			for(int j = 0; j < GameConst.MAP_CHUNK_SIZE; ++j){
				nodes[i][j] = map[x*GameConst.MAP_CHUNK_SIZE + i][y*GameConst.MAP_CHUNK_SIZE + j];
			}
		}
	}
}
