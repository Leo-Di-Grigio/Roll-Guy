package game.cycle.scene.game.state.location;

import java.awt.Point;
import java.util.Collection;
import java.util.HashMap;

import game.cycle.scene.game.state.database.GameConst;
import game.cycle.scene.game.state.database.proto.LocationProto;
import game.cycle.scene.game.state.event.Event;
import game.cycle.scene.game.state.location.creature.Creature;
import game.cycle.scene.game.state.location.creature.NPC;
import game.cycle.scene.game.state.location.creature.Player;
import game.cycle.scene.game.state.location.go.GO;
import game.cycle.scene.game.state.location.lighting.LocationLighting;
import game.cycle.scene.game.state.skill.Skill;
import game.cycle.scene.ui.list.UIGame;
import game.tools.Tools;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Location implements Disposable {
	
	public LocationProto proto;
	public Node [][] map;
	private Map locMap;
	
	// Data
	private HashMap<Integer, LocationObject> permanentObjects;
	private HashMap<Integer, LocationObject> objectMap;
	private HashMap<Integer, Creature> creatureMap;
	private HashMap<Integer, NPC> npcMap;
	private HashMap<Integer, GO> goMap;
	
	private LocationLighting light;
	
	// updates
	private UpdateCycle cycle;
	private boolean requestUpdate;

	public Location(Node [][] map, LocationProto proto) {
		this.proto = proto;
		this.map = map;
		
		locMap = new Map(this.proto, this.map);
		cycle = new UpdateCycle();
	
		light = new LocationLighting();
		
		// data
		permanentObjects = new HashMap<Integer, LocationObject>();
		objectMap = new HashMap<Integer, LocationObject>();
		creatureMap = new HashMap<Integer, Creature>();
		npcMap = new HashMap<Integer, NPC>();
		goMap = new HashMap<Integer, GO>();
	}

	// add
	public boolean addObject(LocationObject object, Vector2 pos, boolean permanent){
		return addObject(object, pos.x, pos.y, permanent);
	}
	
	public boolean addObject(LocationObject object, float x, float y, boolean permanent){
		if(object != null && this.inBound(x, y)){
			// set sprite
			object.setPosition(x, y);
			
			boolean result = locMap.addObject(object, x, y);
			
			// register
			if(result){
				objectMap.put(object.getGUID(), object);
				
				if(permanent){
					permanentObjects.put(object.getGUID(), object);
				}
			}
			
			return result;
		}
		else{
			return false;
		}
	}
	
	// remove
	public void removeObject(int guid, boolean permanent){
		removeObject(getObject(guid), permanent);
	}
	
	public void removeObject(LocationObject object, boolean permanent){
		if(object != null){
			locMap.removeObject(object.getGUID());
			objectMap.remove(object.getGUID());
			if(permanent){
				permanentObjects.remove(object.getGUID());
			}
		}
	}
	
	// get
	public boolean isPermanent(int guid) {
		return permanentObjects.containsKey(guid);
	}
	
	public LocationObject getObject(int guid){
		return objectMap.get(guid);
	}
	
	public Creature getCreature(int guid){
		return creatureMap.get(guid);
	}
	
	public NPC getNPC(int guid){
		return npcMap.get(guid);
	}
	
	public GO getGO(int guid){
		return goMap.get(guid);
	}
	
	// get values
	public Collection<LocationObject> getPermanentValues() {
		return permanentObjects.values();
	}
	
	public Collection<LocationObject> objectValues(){
		return objectMap.values();
	}
	
	public Collection<Creature> creaturesValues() {
		return creatureMap.values();
	}
	
	public Collection<NPC> npcValues() {
		return npcMap.values();
	}
	
	public Collection<GO> goValues(){
		return goMap.values();
	}
	
	// kill
	public void killObject(int guid){
		LocationObject obj = objectMap.get(guid);
		if(obj != null){
			obj.kill();
		}
	}

	// Update
	public void update(Player player, OrthographicCamera camera, UIGame ui, boolean losMode) {
		cycle.update(player, this, camera, ui, losMode);
		
		// requested
		if(this.requestUpdate){
			this.requestUpdate = false;
			LocationLighting.updateLighting(this);
			player.updateLOS(this, camera);
		}
	}

	public void requestUpdate(){
		this.requestUpdate = true;
	}
	
	// Events
	public void addEvent(Event event) {
		for(NPC npc: npcMap.values()){
			npc.aiEvent(this, event);
		}
	}
	
	// Data
	public boolean inBound(Vector2 pos){
		return inBound(pos.x, pos.y);
	}
	
	public boolean inBound(float x, float y){
		return (x >= 0 && x < proto.sizeX()*GameConst.TILE_SIZE && y >= 0 && y < proto.sizeY()*GameConst.TILE_SIZE);
	}

	// EDIT
	public void interactWithNpc(Player player, UIGame ui, Vector2 pos) {
		
	}
	
	public void useGO(LocationObject user, GO go){
		if(go.proto.script() != null){
			if(go.proto.container() || go.proto.teleport() || go.proto.usable()){
				float delta = Tools.getRange(user, go);
			
				if(delta < GameConst.INTERACT_RANGE){
					go.event(new Event(Event.EVENT_SCRIPT_GO_USE, user, go));
				}
			}
		}
	}

	// Interact
	public boolean isInteractive(Vector2 pos, int playerid) {
		return false;
	}

	public String getSelectedCreature(Vector2 pos) {
		return "null";
	}
	
	public Point checkVisiblity(LocationObject a, LocationObject b){
		int x0 = (int)a.getSpriteX();
		int y0 = (int)a.getSpriteY();
		
		int x1 = (int)b.getSpriteX();
		int y1 = (int)b.getSpriteY();
		
		return checkVisiblity(x0, y0, x1, y1);
	}
	
	public Point checkVisiblity(int x0, int y0, int x1, int y1){
		// draw line
	    boolean steep = Math.abs(y1 - y0) > Math.abs(x1 - x0);
	    int tmp = 0;
	    
	    if (steep){ // swap
	    	tmp = y0;
			y0 = x0;
			x0 = tmp;
			
			tmp = y1;
			y1 = x1;
			x1 = tmp;
	    }
		
	    if (x0 > x1){ // swap
	    	tmp = x0;
			x0 = x1;
			x1 = tmp;
			
			tmp = y0;
			y0 = y1;
			y1 = tmp;
	    }
	    
	    int dx = x1 - x0;
	    int dy = Math.abs(y1 - y0);
	    int error = dx / 2;
	    int ystep = (y0 < y1) ? 1 : -1;
	    int y = y0;
	    
	    for (int x = x0; x <= x1; x++){
	        if(!checkLOS(steep ? y : x, steep ? x : y)){
	        	return new Point(steep ? y : x, steep ? x : y);
	        }
	        
	        error -= dy;
	        if (error < 0) {
	            y += ystep;
	            error += dx;
	        }
	    }
	    
		return null;
	}
	
	private boolean checkLOS(int x, int y) {
		return true;
	}

	// LIGHT
	public LocationLighting getLight(){
		return light;
	}

	// CLEAR
	@Override
	public void dispose() {
		locMap.dispose();
	}
	
	// Draw
	public void draw(OrthographicCamera camera, SpriteBatch batch, boolean los, UIGame ui, Player player){
		locMap.draw(proto, camera, batch, los, ui, player);
		
		for(LocationObject obj: objectMap.values()){
			obj.draw(batch);
		}
	}
	
	public void addEffect(Skill skill, LocationObject caster, LocationObject target) {
		locMap.addEffect(skill, caster, target);
	}
}