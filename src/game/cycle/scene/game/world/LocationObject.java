package game.cycle.scene.game.world;

import java.awt.Point;
import java.util.ArrayList;

import game.cycle.scene.game.world.creature.items.Inventory;
import game.cycle.scene.game.world.database.GameConst;
import game.cycle.scene.game.world.map.Location;
import game.tools.Const;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

abstract public class LocationObject implements Disposable {
	
	// id
	private static int GUID = 0;
	protected int guid;
	
	// additional
	protected boolean go;
	protected boolean creature;
	protected boolean npc;
	protected boolean player;
	
	// position
	protected Point spawnPos;
	protected Point pos;
	protected Vector2 direct;
	protected Sprite sprite;

	// movement
	protected boolean isMoved;
	protected boolean isDirected;
	protected float speed = 2.0f;
	protected int movementBlocked = 0;
	protected ArrayList<Point> path;
	protected Vector2 endSpritePos;
	protected Point endPos;
	
	// actions points
	public int ap;
	
	// fraction
	public int fraction;
	
	// container 
	public Inventory inventory;
	
	public LocationObject(int guid, int fraction) {
		if(guid == Const.invalidId){
			this.guid = GUID++;
		}
		else{
			this.guid = guid;
		}
		
		this.spawnPos = new Point(0, 0);
		this.pos = new Point(0, 0);
		this.direct = new Vector2();
		this.fraction = fraction;
		this.inventory = new Inventory(GameConst.inventorySizeX, GameConst.inventorySizeY);
	}
	
	public static int getStartGUID(){
		return LocationObject.GUID;
	}
	public static void setStartGUID(int guid){
		LocationObject.GUID = guid; 
	}
	
	public int getGUID(){
		return guid;
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void setPosition(int x, int y){
		this.pos.x = x;
		this.pos.y = y;
	}
	
	public void setSpawnPosition(int x, int y){
		this.spawnPos.x = x;
		this.spawnPos.y = y;
	}
	
	public Point getPosition(){
		return pos;
	}
	
	public Point getSpawnPosition(){
		return spawnPos;
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
	
	public int containsItemId(int itemId) {
		return inventory.containsItemId(itemId);
	}
	
	abstract public void draw(SpriteBatch batch);
	abstract public void update(Location loc, OrthographicCamera camera);
	abstract public boolean damage(int value);
}
