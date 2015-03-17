package game.cycle.scene.game.world;

import java.awt.Point;
import java.util.ArrayList;

import game.cycle.scene.game.world.map.Location;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

abstract public class LocationObject implements Disposable {
	
	protected Vector2 direct;
	protected Sprite sprite;

	// movement
	protected boolean isMoved;
	protected boolean isDirected;
	protected ArrayList<Point> path;
	protected Vector2 endPoint;
	protected float speed = 2.0f;
	
	public LocationObject() {
		this.direct = new Vector2();
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void setPostion(int x, int y) {
		this.sprite.setPosition(x, y);
	}
	
	public Vector2 getDirect(){
		return direct;
	}
	
	public Point getPosition(){
		return new Point((int)((sprite.getX() + direct.x*Location.tileSize)/Location.tileSize),
						 (int)((sprite.getY() + direct.y*Location.tileSize)/Location.tileSize));
	}

	public float getSpriteX(){
		return sprite.getX();
	}
	
	public float getSpriteY(){
		return sprite.getY();
	}
	
	public void resetPath(){
		this.path = null;
	}
	
	public void resetMovement() {
		this.path = null;
		this.isDirected = false;
		this.isMoved = false;
	}
	
	abstract public void draw(SpriteBatch batch);
	abstract public void update(Location loc);
}
