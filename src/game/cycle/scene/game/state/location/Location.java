package game.cycle.scene.game.state.location;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import game.cycle.scene.game.state.database.GameConst;
import game.cycle.scene.game.state.database.proto.GOProto;
import game.cycle.scene.game.state.database.proto.LocationProto;
import game.cycle.scene.game.state.event.Event;
import game.cycle.scene.game.state.location.creature.Creature;
import game.cycle.scene.game.state.location.creature.NPC;
import game.cycle.scene.game.state.location.creature.Player;
import game.cycle.scene.game.state.location.creature.ai.Perception;
import game.cycle.scene.game.state.location.go.GO;
import game.cycle.scene.game.state.location.lighting.LocationLighting;
import game.cycle.scene.ui.list.UIGame;
import game.lua.LuaEngine;
import game.resources.Resources;
import game.resources.tex.Tex;
import game.resources.tex.TexLighting;
import game.script.game.event.Logic;
import game.script.ui.app.ui_ExitGame;
import game.tools.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class Location implements Disposable {
	
	public LocationProto proto;
	public Node [][] map;
	public Sprite [] sprites;
	
	private UpdateCycle cycle;
	
	// Data
	private HashMap<Integer, LocationObject> permanentObjects;
	private HashMap<Integer, LocationObject> objectMap;
	private HashMap<Integer, Creature> creatureMap;
	private HashMap<Integer, NPC> npcMap;
	private HashMap<Integer, GO> goMap;
	
	private LocationLighting light;
	private TexLighting lightingTex;
	
	// updates
	private boolean requestUpdate;
	
	public Location() {
		cycle = new UpdateCycle();
		
		permanentObjects = new HashMap<Integer, LocationObject>();
		objectMap = new HashMap<Integer, LocationObject>();
		creatureMap = new HashMap<Integer, Creature>();
		npcMap = new HashMap<Integer, NPC>();
		goMap = new HashMap<Integer, GO>();
		
		lightingTex = (TexLighting)(Resources.getTexWrap(Tex.LIGHT));
		light = new LocationLighting();
	}
	
	// add
	public void addObject(LocationObject object, int x, int y, boolean permanent){
		if(object != null && this.inBound(x, y)){
			// set sprite
			object.setPosition(x, y);
			object.setSpritePosition(x*GameConst.TILE_SIZE, y*GameConst.TILE_SIZE);
			
			// register
			objectMap.put(object.getGUID(), object);
			if(permanent){
				permanentObjects.put(object.getGUID(), object);
			}
			
			if(object.isCreature()){
				addCreature((Creature)object, x, y);
			}
			else if(object.isGO()){
				addGO((GO)object, x, y);
			}
		}
	}
	
	private void addCreature(Creature creature, int x, int y){
		map[x][y].creature = creature;
		creatureMap.put(creature.getGUID(), creature);
		
		if(creature.isNPC()){
			addNPC((NPC)creature, x, y);
		}
		else if(creature.isPlayer()){
			addPlayer((Player)creature, x, y);
		}
	}
	
	private void addNPC(NPC npc, int x, int y){
		npcMap.put(npc.getGUID(), npc);
	}
	
	private void addPlayer(Player player, int x, int y){
		player.updateLOS(); // update fog of war
	}

	private void addGO(GO go, int x, int y){
		map[x][y].go = go;
		goMap.put(go.getGUID(), go);
	}
	
	// remove
	public void removeObject(int guid, boolean permanent){
		removeObject(getObject(guid), permanent);
	}
	
	public void removeObject(LocationObject object, boolean permanent){
		if(object != null){
			objectMap.remove(object.getGUID());
			if(permanent){
				permanentObjects.remove(object.getGUID());
			}
			
			int x = object.getPosition().x;
			int y = object.getPosition().y;
			
			if(object.isNPC()){
				removeNPC((Creature)object, x, y);
			}
			else if(object.isGO()){
				removeGO((GO)object, x, y);
			}
		}
	}
	
	private void removeNPC(Creature creature, int x, int y){
		creatureMap.remove(creature.getGUID());
		npcMap.remove(creature.getGUID());
		
		if(this.inBound(x, y)){
			map[x][y].creature = null;
		}
	}

	private void removeGO(GO go, int x, int y) {
		goMap.remove(go.getGUID());
		
		if(this.inBound(x, y)){
			map[x][y].go = null;
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
		killObject(getObject(guid));
	}
	
	public void killObject(LocationObject object) {
		if(object.isCreature()){
			Creature creature = (Creature)object;
			
			if(creature != null){
				Point pos = creature.getPosition();
				int x = pos.x;
				int y = pos.y;
			
				if(inBound(x, y)){
					if(creature.isNPC()){
						if(creature.proto().leaveCorpse()){
							creature.kill();
						}
						else{
							map[x][y].creature = null;
						}
						
						npcMap.remove(creature.getGUID());
						creatureMap.remove(creature.getGUID());
					}
					else if(creature.isPlayer()){
						LuaEngine.executeLocationEvent(new Event(Event.EVENT_PLAYER_DEAD, creature, null));
						
						if(proto.eventScript() == null){
							new ui_ExitGame().execute(); // default option
						}
					}
				}
			}
			
			requestUpdate();
		}
		else{
			Point pos = object.getPosition();
			map[pos.x][pos.y].go = null;
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
	public boolean inBound(int x, int y){
		return (x >= 0 && x < proto.sizeX() && y >= 0 && y < proto.sizeY());
	}

	// EDIT
	public void interactWithNpc(Player player, UIGame ui, int x, int y) {
		Creature creature = map[x][y].creature;
		
		if(creature != null && creature.getGUID() != player.getGUID()){
			if(creature.isAlive()){
				if(creature.isNPC()){
					NPC npc = (NPC)creature;
					
					if(!npc.aidata.combat){
						float delta = Tools.getRange(player, npc);
				
						if(delta < GameConst.INTERACT_RANGE){
							addEvent(new Event(Event.EVENT_SOUND_DIALOG_BEGIN, player, npc));
							Logic.dialogBegin(npc);
						}
					}
				}	
			}
			else{
				ui.openCorpse(creature);
			}
		}
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
	public boolean isInteractive(int x, int y, int playerid) {
		if(map[x][y].creature != null){
			if(map[x][y].creature.getGUID() != playerid){
				return true;
			}
		}
		
		if(map[x][y].go != null){
			GOProto go = map[x][y].go.proto;
			
			if(go.container() || go.teleport() || go.usable()){
				return true;
			}
		}
		
		return false;
	}

	public String getSelectedCreature(int x, int y) {
		String guid = "Creature = ";
		
		if(inBound(x, y)){			
			if(map[x][y].creature != null){
				guid += map[x][y].creature.getGUID();
			}
			else{
				guid += "NULL";
			}
			
			guid += ", GO = ";
			if(map[x][y].go != null){
				guid += map[x][y].go.getGUID();
			}
			else{
				guid += "NULL";
			}
		}
		else{
			guid = "NULL";
		}
		
		return guid;
	}
	
	public Point checkVisiblity(LocationObject a, LocationObject b){
		int x0 = a.getPosition().x;
		int y0 = a.getPosition().y;
		
		int x1 = b.getPosition().x;
		int y1 = b.getPosition().y;
		
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
		if(map[x][y].proto.los() || (map[x][y].go != null && map[x][y].go.isLos())){
			return false;
		}
		else{
			return true;
		}
	}

	// LIGHT
	public LocationLighting getLight(){
		return light;
	}
	
	// CYCLE
	public void switchMode(boolean playerInit) {
		this.cycle.switchMode(playerInit);
	}

	public void turnMode(boolean playerInit) {
		this.cycle.turnMode(playerInit);
	}

	public void endTurn() {
		this.cycle.endTurn();
	}
	
	public boolean isTurnBased() {
		return cycle.isTurnBased();
	}
	
	public boolean isPlayerTurn() {
		return cycle.isPlayerTurn();
	}
	
	// CLEAR
	@Override
	public void dispose() {
		for(int i = 0; i < sprites.length; ++i){
			sprites[i] = null;
		}
	}
	
	// Draw
	public void draw(OrthographicCamera camera, SpriteBatch batch, boolean los, UIGame ui, Player player){
		ArrayList<LocationObject> drawLocationObject = new ArrayList<LocationObject>();;
		Node node = null;
		
		int x = (int)(camera.position.x / GameConst.TILE_SIZE);
		int y = (int)(camera.position.y / GameConst.TILE_SIZE);
		int w = (Gdx.graphics.getWidth()/GameConst.TILE_SIZE + 4)/2;
		int h = (Gdx.graphics.getHeight()/GameConst.TILE_SIZE + 4)/2;
		
		int xmin = Math.max(0, x - w);
		int ymin = Math.max(0, y - h);
		int xmax = Math.min(proto.sizeX(), x + w);
		int ymax = Math.min(proto.sizeY(), y + h);
		
		for(int i = xmin; i < xmax; ++i){
			for(int j = ymin; j < ymax; ++j){
				node = map[i][j];
				
				if(los){
					if(node.explored){
						if(node.viewed){
							sprites[node.proto.tex()].setPosition(i*GameConst.TILE_SIZE, j*GameConst.TILE_SIZE);
							sprites[node.proto.tex()].draw(batch);
					
							// lighting
							int power = node.lighting/10;
							power = Math.max(0, power);
							power = Math.min(10, power);
							
							if(node.go != null && (node.go.proto.visible() || ui.getEditMode())){
								if(Perception.isVisible(player, node.lighting)){
									drawLocationObject.add(node.go);
								
									if(node.go.getDraggedObject() != null){
										drawLocationObject.add(node.go.getDraggedObject());
									}	
								}
							}

							if(node.creature != null){
								if(Perception.isVisible(player, node.lighting)){
									drawLocationObject.add(node.creature);
								
									if(node.creature.getDraggedObject() != null){
										drawLocationObject.add(node.creature.getDraggedObject());
									}
								}
							}
							
							// draw lighting
							batch.draw(lightingTex.power[power], i*GameConst.TILE_SIZE, j*GameConst.TILE_SIZE);
						}
						else{
							batch.draw(lightingTex.power[0], i*GameConst.TILE_SIZE, j*GameConst.TILE_SIZE);
						}
					}
				}
				else{
					sprites[node.proto.tex()].setPosition(i*GameConst.TILE_SIZE, j*GameConst.TILE_SIZE);
					sprites[node.proto.tex()].draw(batch);

					if(node.go != null && (node.go.proto.visible() || ui.getEditMode())){
						drawLocationObject.add(node.go);
							
						if(node.go.getDraggedObject() != null){
							drawLocationObject.add(node.go.getDraggedObject());
						}
					}
			
					if(node.creature != null){
						drawLocationObject.add(node.creature);
							
						if(node.creature.getDraggedObject() != null){
							drawLocationObject.add(node.creature.getDraggedObject());
						}
					}
				}
			}
		}

		for(LocationObject object: drawLocationObject){
			object.draw(batch);
		}
	}
}