package game.cycle.scene.game.world.creature;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Creature {

	public int id;
	public String name;
	
	public Stats stats;
	public Inventory inventory;
	public Features features;
	
	public int mapId;
	public Vector2 pos;
	
	public Creature() {
		pos = new Vector2();
	}
	
	public void draw(SpriteBatch batch){
		
	}
}
