package game.cycle.scene.game.world.location;

import java.awt.Point;
import java.util.ArrayList;

import game.cycle.scene.game.world.database.GameConst;
import game.cycle.scene.game.world.location.creature.Creature;
import game.cycle.scene.game.world.location.creature.Player;
import game.cycle.scene.game.world.location.creature.items.Inventory;
import game.cycle.scene.game.world.location.go.GO;
import game.cycle.scene.game.world.skill.Skill;
import game.script.game.event.GameEvents;
import game.tools.Const;
import game.tools.Log;
import game.tools.Tools;

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
		this.pos = new Point(0, 0);
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
	
	public void resetAp(){
		this.ap = GameConst.ACTION_POINTS_MAX;
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
		if(skill != null){
			return useSkill(loc, skill, target.getPosition().x, target.getPosition().y);
		}
		else{
			return false;
		}
	}	
	
	public boolean useSkill(Location loc, Skill skill, int x, int y) { // target cast
		if(skill != null && !this.isMoved){
			if(ap >= skill.ap){
				if(loc.inBound(x, y)){
					LocationObject go = loc.map[x][y].go;
					if(go != null){
						return useSkill(loc, skill, go);
					}
					
					LocationObject creature = loc.map[x][y].creature;
					if(creature != null){
						return useSkill(loc, skill, creature);
					}
				}
				return false;
			}
			else{
				Log.debug("Not enough AP to cast " + skill.title);
				return false;
			}
		}
		else{
			return false;
		}
	}
	
	private boolean useSkill(Location loc, Skill skill, LocationObject target){
		if(!this.isMoved){
			float delta = Tools.getRange(this, target);
		
			if(skill.id == 2){ // Drag skill
				if(this.getDraggedObject() != null){
					GameEvents.characterDropObject(this);
				
					if(this.isPlayer()){
						GameEvents.playerUseSkill(null);
					}
				
					return true;
				}
			}
		
			if(delta <= skill.range){
				for(int i = 0; i < skill.effects.length; ++i){
					if(skill.effects[i] != null){
						skill.effects[i].execute(this, target);
					}
				}
			
				if(loc.isTurnBased()){
					this.ap -= skill.ap;
				}
			
				if(this.isPlayer()){
					GameEvents.playerUseSkill(null);
				}
			
				return true;
			}
		}
		return false;
	}

	public boolean containsLigtingEffects() {
		if(draggedObject != null && draggedObject.isGO()){
			GO go = (GO)draggedObject;
			
			if(go.proto.lighting){
				return true;
			}
		}
		return false;
	}
	
	abstract public void draw(SpriteBatch batch);
	abstract public void update(Location loc, OrthographicCamera camera, Player player, boolean losMode);
	abstract public boolean damage(int value);
}
