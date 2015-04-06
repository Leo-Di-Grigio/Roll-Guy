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
import game.tools.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Location implements Disposable {
	
	public LocationProto proto;
	public Terrain [][] map;
	public Sprite [] sprites;
	
	public UpdateCycle cycle;
	
	public HashMap<Integer, Creature> creatures;
	public HashMap<Integer, NPC> npcs;
	public HashMap<Integer, GO> waypoints;
	
	public Location() {
		cycle = new UpdateCycle();		
		creatures = new HashMap<Integer, Creature>();
		npcs = new HashMap<Integer, NPC>();
		waypoints = new HashMap<Integer, GO>();
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
						sprites[node.proto.texture].setPosition(i*GameConst.TILE_SIZE, j*GameConst.TILE_SIZE);
						sprites[node.proto.texture].draw(batch);
				
						if(node.viewed){
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
						else{
							sprites[9].setPosition(i*GameConst.TILE_SIZE, j*GameConst.TILE_SIZE); // fog
							sprites[9].draw(batch);	
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

	public boolean checkVisiblity(LocationObject a, LocationObject b) {
		Vector2 point = new Vector2(a.getPosition().x, a.getPosition().y);
		Vector2 direct = new Vector2(b.getPosition().x - a.getPosition().x, b.getPosition().y - a.getPosition().y);
		direct.nor();
		
		while(true){
			point.add(direct);
			int x = (int)point.x;
			int y = (int)point.y;
			
			if(inBound(x, y)){
				if(map[x][y].proto.losBlock || (map[x][y].go != null && map[x][y].go.losBlock)){
					return false;
				}
				else{
					if(x == b.getPosition().x && y == b.getPosition().y){
						return true;
					}
				}
			}
			else{
				return false;
			}
		}
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