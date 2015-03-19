package game.cycle.scene.game.world;

import java.awt.Point;
import java.util.ArrayList;

import game.cycle.scene.game.world.database.GameConst;
import game.cycle.scene.game.world.map.Location;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

abstract public class LocationObject implements Disposable {
	
	// id
	private static int ID = 0;
	protected int id;
	
	// additional
	protected boolean go;
	protected boolean creature;
	protected boolean npc;
	protected boolean player;
	
	// position
	protected Point pos;
	protected Vector2 direct;
	protected Sprite sprite;

	// movement
	protected boolean isMoved;
	protected boolean isDirected;
	protected float speed = 2.0f;
	protected ArrayList<Point> path;
	protected Vector2 endSpritePos;
	protected Point endPos;
	
	// actions points
	public int ap;
	
	public LocationObject() {
		this.pos = new Point(0, 0);
		this.direct = new Vector2();
		this.id = ID++;
	}
	
	public int getId(){
		return id;
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void setPosition(int x, int y){
		this.pos.x = x;
		this.pos.y = y;
		//this.sprite.setPosition(x*Location.tileSize, y*Location.tileSize);
	}
	
	public Point getPosition(){
		return pos;
	}
	
	public void setSpritePosition(float x, float y) {
		this.sprite.setPosition(x, y);
	}
	
	protected Vector2 getDirect(){
		return direct;
	}

	public float getSpriteX(){
		return sprite.getX();
	}
	
	public float getSpriteY(){
		return sprite.getY();
	}
	
	public void resetPath(){
		this.path = null;
		this.isDirected = false;
		this.isMoved = false;
		this.direct.set(0.0f, 0.0f);
	}
	
	public void resetAp(){
		this.ap = GameConst.apMax;
	}
	
	public boolean isCreature(){
		return creature;
	}
	
	public boolean isGO(){
		return go;
	}
	
	public boolean isNPC(){
		return npc;
	}
	
	public boolean isPlayer() {
		return player;
	}
	
	public ArrayList<Point> getPath(){
		return path;
	}
	
	abstract public void draw(SpriteBatch batch);
	abstract public void update(Location loc);
	abstract public boolean damage(int value);
}
