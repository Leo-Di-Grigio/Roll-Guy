package game.cycle.scene.game.world.map;

import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.game.world.creature.NPC;
import game.cycle.scene.game.world.creature.Player;
import game.cycle.scene.ui.list.UIGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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
		
		int x = (int)(player.pos.x / tileSize);
		int y = (int)(player.pos.y / tileSize);
		int w = (Gdx.graphics.getWidth()/tileSize + 4)/2;
		int h = (Gdx.graphics.getHeight()/tileSize + 4)/2;
		
		int xmin = Math.max(0, x - w);
		int ymin = Math.max(0, y - h);
		int xmax = Math.min(sizeX, x + w);
		int ymax = Math.min(sizeY, y + h);
		
		for(int i = xmin; i < xmax; ++i){
			for(int j = ymin; j < ymax; ++j){
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
				
				if(node.creature != null){
					node.creature.draw(batch);
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
	
	public boolean inBound(int x, int y){
		return (x >= 0 && x < sizeX && y >= 0 && y < sizeY);
	}

	public void editorWall(int x, int y) {
		if(inBound(x, y)){
			map[x][y].passable = !map[x][y].passable;
		}
	}

	public void editorNpc(int x, int y) {
		if(inBound(x, y)){
			if(map[x][y].creature == null){
				map[x][y].creature = new NPC();
				map[x][y].creature.sprite.setPosition(x*tileSize, y*tileSize);
			}
			else{
				map[x][y].creature = null;
			}
		}
	}
	
	private float talkingRange = Location.tileSize * 1.45f;
	public void talkWithNpc(Player player, UIGame ui, int x, int y) {
		Creature npc = map[x][y].creature;
		
		if(npc.id != player.id){
			float delta = new Vector2(npc.sprite.getX() - player.sprite.getX(), npc.sprite.getY() - player.sprite.getY()).len();
			
			if(delta < talkingRange){
				ui.npcTalk(ui, player, npc);
			}
		}
	}
}
