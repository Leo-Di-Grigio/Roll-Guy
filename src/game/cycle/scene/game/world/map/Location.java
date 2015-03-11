package game.cycle.scene.game.world.map;

import game.cycle.scene.game.world.creature.Player;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class Location implements Disposable {
	
	public static final int tileSize = 32;
	
	public int sizeX;
	public int sizeY;
	public Node [][] map;
	public Sprite [] sprites;
	
	public Location() {
		
	}
	
	public int counter;
	public void draw(Player player, SpriteBatch batch) {
		Node node = null;
		counter = 0;
		
		for(int i = 0; i < sizeX; ++i){
			for(int j = 0; j < sizeY; ++j){
				counter++;
				node = map[i][j];
				
				if(node.passable){
					sprites[0].setPosition(i*tileSize, j*tileSize);
					sprites[0].draw(batch);
				}
				else{
					sprites[1].setPosition(i*tileSize, j*tileSize);
					sprites[1].draw(batch);
				}
			}
		}
	}

	@Override
	public void dispose() {
		for(int i = 0; i < sprites.length; ++i){
			sprites[i] = null;
			
		}
	}

	public void editWall(int x, int y) {
		if(inBound(x, y)){
			map[x][y].passable = !map[x][y].passable;
		}
	}
	
	public boolean inBound(int x, int y){
		return (x >= 0 && x < sizeX && y >= 0 && y < sizeY);
	}
}
