package game.cycle.scene.game.state.location;

import java.awt.Point;
import java.util.ArrayList;

import game.cycle.scene.game.state.database.GameConst;
import game.cycle.scene.game.state.location.creature.Creature;
import game.cycle.scene.game.state.location.creature.Player;
import game.cycle.scene.game.state.location.creature.items.Inventory;
import game.cycle.scene.game.state.location.go.GO;
import game.cycle.scene.game.state.skill.Skill;
import game.tools.Const;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
	
	// drag
	protected LocationObject draggedObject;
	
	public LocationObject(int guid, int fraction) {
		if(guid == Const.INVALID_ID){
			this.guid = GUID++;
		}
		else{
			this.guid = guid;
		}
		
		this.spawnPos = new Point(0, 0);
		this.direct = new Vector2();
		this.fraction = fraction;
		this.inventory = new Inventory(GameConst.INVENTORY_SIZE_X, GameConst.INVENTORY_SIZE_Y);
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
	
	public void setSprite(Texture tex){
		this.sprite.setTexture(tex);
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void setSpawnPosition(int x, int y){
		this.spawnPos.x = x;
		this.spawnPos.y = y;
	}

	public void setSpawnPosition(Point point) {
		this.spawnPos = point;
	}

	public Point getSpawnPosition(){
		return spawnPos;
	}
	
	public void setPosition(float x, float y) {
		this.sprite.setPosition(x, y);
	}
	
	protected Vector2 getDirect(){
		return direct;
	}
	
	public Sprite getSprite(){
		return sprite;
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

	public boolean isMoved() {
		return isMoved;
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

	public void dragObject(LocationObject target) {
		this.draggedObject = target;
	}
	
	public void dropObject(){
		this.draggedObject = null; 
	}

	public LocationObject getDraggedObject() {
		return this.draggedObject;
	}

	public float getDirectAngle() {
		return direct.angle();
	}
	
	// Skill use
	public boolean useSkill(Location loc, Skill skill, Creature target) { // self cast
		return false;
	}
	
	public boolean useSkill(Location loc, Skill skill, Vector2 pos){
		return false;
	}
	
	public boolean useSkill(Location loc, Skill skill, int x, int y) { // target cast
		return false;
	}

	public boolean containsLigtingEffects() {
		if(draggedObject != null && draggedObject.isGO()){
			GO go = (GO)draggedObject;
			
			if(go.proto.light()){
				return true;
			}
		}
		return false;
	}
	
	public void moveUp() {
		direct.add(0.0f, speed);
	}

	public void moveDown() {
		direct.add(0.0f, -speed);
	}

	public void moveLeft() {
		direct.add(-speed, 0.0f);
	}

	public void moveRight() {
		direct.add(speed, 0.0f);
	}

	public void updateMovement() {
		direct.nor();
		sprite.translate(direct.x*speed, direct.y*speed);
		direct.set(0.0f, 0.0f);
	}
	
	abstract public void draw(SpriteBatch batch);
	abstract public void update(Location loc, OrthographicCamera camera, Player player, boolean losMode);
	abstract public void kill();
	abstract public boolean damage(int value);
}
