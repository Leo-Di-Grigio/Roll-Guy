package game.cycle.scene.game.world.creature;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
	
	public Creature creature;
	
	public Player() {
		creature = new Creature();
	}

	public void update() {
		creature.update();
	}
	
	public void draw(SpriteBatch batch){
		creature.draw(batch);
	}
}
