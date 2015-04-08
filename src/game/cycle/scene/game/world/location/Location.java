package game.cycle.scene.game.world.location;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import game.cycle.scene.game.world.database.GameConst;
import game.cycle.scene.game.world.event.LocationEvent;
import game.cycle.scene.game.world.event.LocationEvent.Event;
import game.cycle.scene.game.world.event.LocationEvent.Type;
import game.cycle.scene.game.world.location.creature.Creature;
import game.cycle.scene.game.world.location.creature.NPC;
import game.cycle.scene.game.world.location.creature.Player;
import game.cycle.scene.game.world.location.go.GO;
import game.cycle.scene.game.world.location.go.GOProto;
import game.cycle.scene.ui.list.UIGame;
import game.resources.Resources;
import game.resources.Tex;
import game.resources.TexLighting;
import game.tools.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class Location implements Disposable {
	
	public LocationProto proto;
	public Terrain [][] map;
	public Sprite [] sprites;
	
	public UpdateCycle cycle;
	
	public HashMap<Integer, Creature> creatures;
	public HashMap<Integer, NPC> npcs;
	public HashMap<Integer, GO> gos;
	public HashMap<Integer, GO> waypoints;
	
	public TexLighting lighting;
	private boolean lightingUpdate;
	
	public Location() {
		cycle = new UpdateCycle();		
		creatures = new HashMap<Integer, Creature>();
		npcs = new HashMap<Integer, NPC>();
		gos = new HashMap<Integer, GO>();
		waypoints = new HashMap<Integer, GO>();
		
		lighting = (TexLighting)(Resources.getTexWrap(Tex.lightingColors));
	}
	
	// add	
	public void addCreature(NPC npc, int x, int y){
		map[x][y].creature = npc;
		npc.setPosition(x, y);
		npc.setSpritePosition(x*GameConst.TILE_SIZE, y*GameConst.TILE_SIZE);
		creatures.put(npc.getGUID(), npc);
		npcs.put(npc.getGUID(), npc);
	}
	
	public void addCreature(Player player, int x, int y){
		map[x][y].creature = player;
		player.setPosition(x, y);
		player.setSpritePosition(x*GameConst.TILE_SIZE, y*GameConst.TILE_SIZE);
		creatures.put(player.getGUID(), player);
		
		// update fog of war
		player.updateLOS();
	}

	// remove
	public void killObject(LocationObject object) {
		if(object.isCreature()){
			Creature creature = (Creature)object;
			
			if(creature != null){
				Point pos = creature.getPosition();
				int x = pos.x;
				int y = pos.y;
			
				if(inBound(x, y)){
					if(creature.isNPC()){
						if(creature.proto.leaveCorpse){
							creature.kill();
						}
						else{
							map[x][y].creature = null;
						}
						
						npcs.remove(creature.getGUID());
					}
					else{
						map[x][y].creature = null;
					}
					
					creatures.remove(creature.getGUID());
				}
			}
		}
		else{
			Point pos = object.getPosition();
			map[pos.x][pos.y].go = null;
		}
	}
	
	protected void deleteObject(LocationObject object){
		if(object.isCreature()){
			Creature creature = (Creature)object;
			
			if(creature != null){
				Point pos = creature.getPosition();
				int x = pos.x;
				int y = pos.y;
			
				if(inBound(x, y)){
					map[x][y].creature = null;
					npcs.remove(creature.getGUID());
				}
			}
		}
		else{
			Point pos = object.getPosition();
			map[pos.x][pos.y].go = null;
		}
	}
	
	// Update
	public void update(Player player, OrthographicCamera camera) {
		cycle.update(player, this, camera);
		
		if(this.lightingUpdate){
			this.lightingUpdate = false;
			this.updateLighting();
			player.updateLOS(this, camera);
		}
	}

	public void updateLocation(){
		this.lightingUpdate = true;
	}
	
	private void updateLighting(){
		// clear location lighting
		for(int i = 0; i < proto.sizeX; ++i){
			for(int j = 0; j < proto.sizeY; ++j){
				map[i][j].lighting = 0;
			}
		}
		
		// go's lighting
		for(GO go: gos.values()){
			if(go.proto.lighting){
				lighting(go, go.getPosition().x, go.getPosition().y);
			}
		}
	}
	
	private void lighting(GO go, int x, int y) {
		int power = (int)(Math.sqrt(go.proto.lightingPower));
		
		if(power > 0){
			int minx = Math.max(0, x - power);
			int miny = Math.max(0, y - power);
			int maxx = Math.min(proto.sizeX - 1, x + power);
			int maxy = Math.min(proto.sizeY - 1, y + power);
			float range = 0.0f;
			
			for(int i = minx; i < maxx; ++i){
				for(int j = miny; j < maxy; ++j){
					Point endFOV = checkVisiblity(x, y, i, j);
					
					if(endFOV == null){
						Terrain node = map[i][j];
						
						range = (float)Tools.getRange(i, j, x, y);
						node.lighting += power*100/(range*range);
					}
					else{
						int fovX = endFOV.x;
						int fovY = endFOV.y;
						
						if(inBound(fovX, fovY)){
							Terrain node = map[fovX][fovY];
							range = (float)Tools.getRange(fovX, fovY, x, y);
							node.lighting += power*100/(range*range);
						}
					}
				}
			}
		}
	}
	
	// Events
	public void addLocationEvent(LocationEvent event) {
		for(NPC npc: npcs.values()){
			npc.aiEvent(this, event);
		}
	}
	
	// Switch game mode
	public void gameModeTurnBased(boolean playerTurn) {
		cycle.turnBase(playerTurn);
	}

	public void gameModeRealTime(Player player) {
		cycle.realTime(player, this);
	}

	public boolean isTurnBased() {
		return cycle.isTurnBased();
	}
	
	// Data
	public boolean inBound(int x, int y){
		return (x >= 0 && x < proto.sizeX && y >= 0 && y < proto.sizeY);
	}
	
	// Draw
	public void draw(OrthographicCamera camera, SpriteBatch batch, boolean los, UIGame ui){
		ArrayList<LocationObject> drawLocationObject = new ArrayList<LocationObject>();;
		Terrain node = null;
		
		int x = (int)(camera.position.x / GameConst.TILE_SIZE);
		int y = (int)(camera.position.y / GameConst.TILE_SIZE);
		int w = (Gdx.graphics.getWidth()/GameConst.TILE_SIZE + 4)/2;
		int h = (Gdx.graphics.getHeight()/GameConst.TILE_SIZE + 4)/2;
		
		int xmin = Math.max(0, x - w);
		int ymin = Math.max(0, y - h);
		int xmax = Math.min(proto.sizeX, x + w);
		int ymax = Math.min(proto.sizeY, y + h);
		
		for(int i = xmin; i < xmax; ++i){
			for(int j = ymin; j < ymax; ++j){
				node = map[i][j];
				
				if(los){
					if(node.explored){
						if(node.viewed){
							sprites[node.proto.texture].setPosition(i*GameConst.TILE_SIZE, j*GameConst.TILE_SIZE);
							sprites[node.proto.texture].draw(batch);
					
							// lighting
							int power = node.lighting/10;
							power = Math.max(0, power);
							power = Math.min(10, power);
							
							if(power > 0){
								if(node.go != null && (node.go.proto.visible || ui.getGoEditMode())){
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
							
							// draw lighting
							batch.draw(lighting.power[power], i*GameConst.TILE_SIZE, j*GameConst.TILE_SIZE);
						}
						else{
							batch.draw(lighting.power[0], i*GameConst.TILE_SIZE, j*GameConst.TILE_SIZE);
						}
					}
				}
				else{
					sprites[node.proto.texture].setPosition(i*GameConst.TILE_SIZE, j*GameConst.TILE_SIZE);
					sprites[node.proto.texture].draw(batch);

					if(node.go != null && (node.go.proto.visible || ui.getGoEditMode())){
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

	// EDITO
	public void interactWithNpc(Player player, UIGame ui, int x, int y) {
		Creature creature = map[x][y].creature;
		
		if(creature != null && creature.getGUID() != player.getGUID()){
			if(creature.isAlive()){
				if(creature.isNPC()){
					NPC npc = (NPC)creature;
					
					if(!npc.aidata.combat){
						float delta = Tools.getRange(player, npc);
				
						if(delta < GameConst.INTERACT_RANGE){
							addLocationEvent(new LocationEvent(Type.SOUND, Event.DIALOG_BEGIN, player, npc));
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
		if(go.script != null){
			if(go.proto.container || go.proto.teleport || go.proto.usable){
				float delta = Tools.getRange(user, go);
			
				if(delta < GameConst.INTERACT_RANGE){
					go.script.execute(user);
					go.event(new LocationEvent(Type.TRIGGER, Event.GO_USE, user, go), 0);
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
			
			if(go.container || go.teleport || go.usable){
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
		if(map[x][y].proto.losBlock || (map[x][y].go != null && map[x][y].go.losBlock)){
			return false;
		}
		else{
			return true;
		}
	}

	public void swap(Integer a, Integer b){
		int tmp = a;
		a = b;
		b = tmp;
	}
	
	public GO getWayPoint(int guid){
		return waypoints.get(guid);
	}
	
	public int getWayPointsCount() {
		return waypoints.size();
	}
	
	// CLEAR
	@Override
	public void dispose() {
		for(int i = 0; i < sprites.length; ++i){
			sprites[i] = null;
		}
	}
}