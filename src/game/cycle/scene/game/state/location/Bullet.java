package game.cycle.scene.game.state.location;

import game.cycle.scene.game.state.database.GameConst;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Bullet implements Poolable {

	private static final float SPEED = 5.0f;
	private static final float HALF_OF_TILE = GameConst.TILE_SIZE/2;
	
	// data
	private PooledEffect effect;
	private Vector2 pos;
	private Vector2 endPos;
	private Vector2 speed;
	private boolean isContinue;
	
	public Bullet() {
		this.pos = new Vector2();
		this.endPos = new Vector2();
		this.speed = new Vector2();
	}

	public void init(PooledEffect effect, float posx, float posy, float endx, float endy) {
		this.pos.set(posx + HALF_OF_TILE, posy + HALF_OF_TILE);
		this.endPos.set(endx + HALF_OF_TILE, endy + HALF_OF_TILE);
		this.effect = effect;
		this.effect.setPosition(pos.x, pos.y);
		
		speed.set(endx - posx, endy - posy);
		speed.nor();
		speed.set(speed.x * SPEED, speed.y * SPEED);
		
		this.isContinue = true;
    }
	
	public void draw(SpriteBatch batch, float delta){
		if(isContinue){
			pos.add(speed.x, speed.y);
			effect.setPosition(pos.x, pos.y);
	
			if(Math.abs(endPos.x - pos.x) <= SPEED && Math.abs(endPos.y - pos.y) <= SPEED){
				this.isContinue = false;
			}
		}
		
		effect.draw(batch, delta);
	}
	
	public boolean isEnded(){
		return effect.isComplete();
	}
	
	@Override
	public void reset() {
		effect.free();
	}
}