package game.cycle.scene.game.world.map;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import game.cycle.scene.game.world.LocationObject;
import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.game.world.creature.NPC;
import game.cycle.scene.game.world.creature.Player;
import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.database.GameConst;
import game.cycle.scene.game.world.event.LocationEvent;
import game.cycle.scene.game.world.go.GO;
import game.cycle.scene.game.world.go.GOFactory;
import game.cycle.scene.game.world.go.GOProto;
import game.cycle.scene.game.world.skill.Skill;
import game.cycle.scene.ui.list.UIGame;
import game.tools.Const;
import game.tools.Log;
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
	
	public Location() {
		cycle = new UpdateCycle();		
		creatures = new HashMap<Integer, Creature>();
		npcs = new HashMap<Integer, NPC>();
	}
	
	// add	
	public void addCreature(NPC npc, int x, int y){
		map[x][y].creature = npc;
		npc.setPosition(x, y);
		npc.setSpritePosition(x*GameConst.tileSize, y*GameConst.tileSize);
		creatures.put(npc.getId(), npc);
		npcs.put(npc.getId(), npc);
	}
	
	public void addCreature(Player player, int x, int y){
		map[x][y].creature = player;
		player.setPosition(x, y);
		player.setSpritePosition(x*GameConst.tileSize, y*GameConst.tileSize);
		creatures.put(player.getId(), player);
		
		// update fog of war
		player.updateLOS();
	}

	// remove
	public void removeObject(LocationObject object) {
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
						
						npcs.remove(creature.getId());
					}
					else{
						map[x][y].creature = null;
					}
					
					creatures.remove(creature.getId());
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

	// events
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
		cycle.realTime(player);
	}
	
	// DRAW
	public void draw(OrthographicCamera camera, SpriteBatch batch, boolean los) {
		ArrayList<Creature> drawCreature = new ArrayList<Creature>();;
		Terrain node = null;
		
		int x = (int)(camera.position.x / GameConst.tileSize);
		int y = (int)(camera.position.y / GameConst.tileSize);
		int w = (Gdx.graphics.getWidth()/GameConst.tileSize + 4)/2;
		int h = (Gdx.graphics.getHeight()/GameConst.tileSize + 4)/2;
		
		int xmin = Math.max(0, x - w);
		int ymin = Math.max(0, y - h);
		int xmax = Math.min(proto.sizeX, x + w);
		int ymax = Math.min(proto.sizeY, y + h);
		
		for(int i = xmin; i < xmax; ++i){
			for(int j = ymin; j < ymax; ++j){
				node = map[i][j];
				
				if(los){
					if(node.explored){
						sprites[node.proto.texture].setPosition(i*GameConst.tileSize, j*GameConst.tileSize);
						sprites[node.proto.texture].draw(batch);
				
						if(node.viewed){
							if(node.go != null){
								node.go.draw(batch);
							}
				
							if(node.creature != null){
								drawCreature.add(node.creature);
							}
						}
						else{
							sprites[9].setPosition(i*GameConst.tileSize, j*GameConst.tileSize); // fog
							sprites[9].draw(batch);	
						}
					}
				}
				else{
					sprites[node.proto.texture].setPosition(i*GameConst.tileSize, j*GameConst.tileSize);
					sprites[node.proto.texture].draw(batch);
			

					if(node.go != null){
						node.go.draw(batch);
					}
		
					if(node.creature != null){
						drawCreature.add(node.creature);
					}					
				}
			}
		}
		
		for(Creature creature: drawCreature){
			creature.draw(batch);
		}
	}
	
	public boolean inBound(int x, int y){
		return (x >= 0 && x < proto.sizeX && y >= 0 && y < proto.sizeY);
	}

	// EDITOR
	public void editorTerrain(int x, int y, UIGame ui, int brush) {
		if(inBound(x, y)){
			int terrainid = ui.getSelectedListTerrain();
			
			if(terrainid != Const.invalidId){
				int size = 1;
				switch (brush) {
					case UIGame.modeTerrainBrush1: size = 1; break;
					case UIGame.modeTerrainBrush2: size = 2; break;
					case UIGame.modeTerrainBrush3: size = 3; break;
				}
				
				for(int i = 0; i < size; ++i){
					for(int j = 0; j < size; ++j){
						if(inBound(i + x, j + y)){
							map[i + x][j + y].proto = Database.getTerrainProto(terrainid);
						}
					}
				}
			}
		}
	}

	public void npcAdd(int x, int y, UIGame ui) {
		if(inBound(x, y)){
			int id = ui.getSelectedListNpc();
		
			if(id != Const.invalidId){
				if(map[x][y].creature == null){
					NPC npc = new NPC(Database.getCreature(id));
					npc.setPosition(x, y);
					npc.setSpritePosition(x*GameConst.tileSize, y*GameConst.tileSize);
					addCreature(npc, x, y);
				}
				else{
					removeObject(map[x][y].creature);
				}
			}
			else{
				removeObject(map[x][y].creature);
			}
		}
	}
	
	public void npcEdit(int x, int y, UIGame ui){
		if(inBound(x, y)){
			ui.npcEdit.setCreature(map[x][y].creature);
		}
	}
	
	public void goAdd(int x, int y, UIGame ui) {
		if(inBound(x, y)){
			int id = ui.getSelectedListGO();
		
			if(id != Const.invalidId){
				if(map[x][y].go == null){
					map[x][y].go = GOFactory.getGo(id, x, y, 0, 0, 0, 0);
				}
				else{
					map[x][y].go = null;
				}
			}
			else{
				map[x][y].go = null;
			}
		}
	}

	public void goEdit(int x, int y, UIGame ui) {
		if(inBound(x, y)){
			ui.goEdit.setGO(map[x][y].go);
		}
	}
	
	public void interactWithNpc(Player player, UIGame ui, int x, int y) {
		Creature creature = map[x][y].creature;
		
		if(creature != null && creature.getId() != player.getId()){
			if(creature.isAlive()){
				if(creature.isNPC()){
					NPC npc = (NPC)creature;
					
					if(!npc.aidata.combat){
						float delta = getRange(player, npc);
				
						if(delta < GameConst.interactRange){
							ui.npcTalk(ui, player, npc);
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
				float delta = getRange(user, go);
			
				if(delta < GameConst.interactRange){
					go.script.execute(user);
				}
			}
		}
	}

	// Interact
	public void containerGO(Player player, GO go, UIGame ui) {
		if(getRange(go, player) <= GameConst.interactRange){
			if(go.inventory != null){
				ui.openContainer(go.inventory);
			}
		}
	}
	
	public float getRange(LocationObject a, LocationObject b){
		int x1 = a.getPosition().x;
		int y1 = a.getPosition().y;
		int x2 = b.getPosition().x;
		int y2 = b.getPosition().y;
		return Tools.getRange(x1, y1, x2, y2);
	}

	public boolean useSkill(Skill skill, Creature target) { // self cast
		if(skill != null){
			return useSkill(skill, target, target.getPosition().x, target.getPosition().y);
		}
		else{
			return false;
		}
	}
	
	public boolean useSkill(Skill skill, LocationObject caster, int x, int y) { // target cast
		if(skill != null){
			if(caster.ap >= skill.ap){
				if(inBound(x, y)){
					LocationObject creature = map[x][y].creature;
					if(creature != null){
						return useSkill(skill, caster, creature);
					}
			
					LocationObject go = map[x][y].go;
					if(go != null){
						return useSkill(skill, caster, go);
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
	
	private boolean useSkill(Skill skill, LocationObject caster, LocationObject target){
		float delta = getRange(caster, target);
		
		if(delta <= skill.range){
			for(int i = 0; i < skill.effects.length; ++i){
				if(skill.effects[i] != null){
					skill.effects[i].execute(caster, target);
				}
			}
			
			if(cycle.isTurnBased()){
				caster.ap -= skill.ap;
			}
			return true;
		}
		
		return false;
	}
	
	public boolean isInteractive(int x, int y, int playerid) {
		if(map[x][y].creature != null){
			if(map[x][y].creature.getId() != playerid){
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
		String guid = "";
		if(inBound(x, y)){			
			if(map[x][y].creature != null){
				guid += map[x][y].creature.getId();
			}
			else{
				guid = "NULL";
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
	
	// CLEAR
	@Override
	public void dispose() {
		for(int i = 0; i < sprites.length; ++i){
			sprites[i] = null;
		}
	}

	public boolean isTurnBased() {
		return cycle.isTurnBased();
	}
}