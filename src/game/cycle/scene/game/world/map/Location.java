package game.cycle.scene.game.world.map;

import java.awt.Point;
import java.util.HashMap;

import game.cycle.scene.game.world.LocationObject;
import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.game.world.creature.NPC;
import game.cycle.scene.game.world.creature.Player;
import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.database.GameConst;
import game.cycle.scene.game.world.go.GO;
import game.cycle.scene.game.world.go.GOFactory;
import game.cycle.scene.game.world.go.GOProto;
import game.cycle.scene.game.world.skill.Skill;
import game.cycle.scene.ui.list.UIGame;
import game.script.game.event.GameEvents;
import game.tools.Const;
import game.tools.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Location implements Disposable {
	
	public LocationProto proto;
	public static final int tileSize = 32;
	
	public Terrain [][] map;
	public Sprite [] sprites;
	
	public boolean isTurnBased;
	public boolean playerTurn;
	
	private HashMap<Integer, Creature> creatures;
	private HashMap<Integer, NPC> npcs;
	
	public Location() {
		isTurnBased = false;
		
		creatures = new HashMap<Integer, Creature>();
		npcs = new HashMap<Integer, NPC>();
	}
	
	// add	
	public void addCreature(NPC npc, int x, int y){
		map[x][y].creature = npc;
		npc.setPosition(x, y);
		npc.setSpritePosition(x*Location.tileSize, y*Location.tileSize);
		creatures.put(npc.getId(), npc);
		npcs.put(npc.getId(), npc);
	}
	
	public void addCreature(Player player, int x, int y){
		map[x][y].creature = player;
		player.setPosition(x, y);
		creatures.put(player.getId(), player);
	}

	// remove	
	public void removeObject(LocationObject object) {
		if(object.isCreature()){
			Creature creature = (Creature)object;
			removeCreature(creature);
		}
		else{
			Point pos = object.getPosition();
			map[pos.x][pos.y].go = null;
		}
	}
	
	private void removeCreature(Creature creature){
		if(creature != null){
			Point pos = creature.getPosition();
			int x = pos.x;
			int y = pos.y;
		
			if(inBound(x, y)){
				if(creature.isNPC()){
					npcs.remove(creature.getId());
				}
				
				creatures.remove(creature.getId());
				map[x][y].creature = null;
			}
		}
	}
	
	// Update
	public void update(Player player) {
		for(Creature creature: creatures.values()){
			creature.animationUpdate();
		}
		
		if(isTurnBased){
			if(playerTurn){
				player.update(this);
			}
			else{
				npcUpdate(player);
			}
		}
		else{
			player.update(this);
			npcUpdate(player);
		}
	}
	
	private void npcUpdate(Player player){
		if(isTurnBased){
			boolean update = false; // unupdated NPC check
			
			for(NPC npc: npcs.values()){
				if(!npc.aidata.updated){
					update = true;
					npc.update(this);
					break;
				}
			}
			
			if(update == false){ // no unupdated NPC
				playerTurn(player);
			}
		}
	}
	
	public void playerTurn(Player player){
		player.resetAp();
		this.playerTurn = true;
		GameEvents.nextTurn();
		Log.debug("Player turn");
	}

	public void npcTurn(Player player){
		player.resetPath();
		this.playerTurn = false;
		GameEvents.nextTurn();
		
		for(NPC npc: npcs.values()){
			npc.resetAI();
			npc.resetAp();
		}
	
		Log.debug("NPC turn");
	}
	
	// Switch game mode
	public void gameModeTurnBased(boolean playerTurn, Player player) {
		this.isTurnBased = true;
			
		if(playerTurn){
			playerTurn(player);
		}
		else{
			npcTurn(player);
		}
	}

	public void gameModeRealTime() {
		this.isTurnBased = false;
	}
	
	// Draw
	public int counter;
	public void draw(OrthographicCamera camera, SpriteBatch batch) {
		Terrain node = null;
		counter = 0;
		
		int x = (int)(camera.position.x / tileSize);
		int y = (int)(camera.position.y / tileSize);
		int w = (Gdx.graphics.getWidth()/tileSize + 4)/2;
		int h = (Gdx.graphics.getHeight()/tileSize + 4)/2;
		
		int xmin = Math.max(0, x - w);
		int ymin = Math.max(0, y - h);
		int xmax = Math.min(proto.sizeX, x + w);
		int ymax = Math.min(proto.sizeY, y + h);
		
		for(int i = xmin; i < xmax; ++i){
			for(int j = ymin; j < ymax; ++j){
				counter++;
				node = map[i][j];
				
				sprites[node.proto.texture].setPosition(i*tileSize, j*tileSize);
				sprites[node.proto.texture].draw(batch);
				
				if(node.go != null){
					node.go.draw(batch);
				}
			}
		}
		
		for(Creature creature: creatures.values()){
			creature.draw(batch);
		}
	}

	@Override
	public void dispose() {
		for(int i = 0; i < sprites.length; ++i){
			sprites[i] = null;
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
		int id = ui.getSelectedListNpc();
		
		if(id != Const.invalidId){
			if(map[x][y].creature == null){
				NPC npc = new NPC(Database.getCreature(id));
				npc.setPosition(x, y);
				npc.setSpritePosition(x*Location.tileSize, y*Location.tileSize);
				addCreature(npc, x, y);
			}
			else{
				removeCreature(map[x][y].creature);
			}
		}
		else{
			removeCreature(map[x][y].creature);
		}
	}
	
	public void npcEdit(int x, int y, UIGame ui){
		ui.npcEdit.setCreature(map[x][y].creature);
	}
	
	public void goAdd(int x, int y, UIGame ui) {
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

	public void goEdit(int x, int y, UIGame ui) {
		ui.goEdit.setGO(map[x][y].go);
	}
	
	public void talkWithNpc(Player player, UIGame ui, int x, int y) {
		Creature npc = map[x][y].creature;
		
		if(npc != null && npc.getId() != player.getId()){
			float delta = getRange(player, npc);
			
			if(delta < GameConst.interactRange){
				ui.npcTalk(ui, player, npc);
			}
		}
	}
	
	public void useGO(LocationObject user, int x, int y){
		GO go = map[x][y].go;
		
		if(go != null && go.script != null){
			if(go.proto.container || go.proto.teleport || go.proto.usable){
				float delta = getRange(user, go);
			
				if(delta < GameConst.interactRange){
					go.script.execute(user);
				}
			}
		}
	}
	
	public float getRange(LocationObject a, LocationObject b){
		int ax = a.getPosition().x;
		int ay = a.getPosition().y;
		int bx = b.getPosition().x;
		int by = b.getPosition().y;
		return new Vector2(ax - bx, ay - by).len();
	}
	
	public float getRange(int x, int y, int toX, int toY) {
		return new Vector2(x - toX, y - toY).len();
	}

	public boolean useSkill(Skill skill, LocationObject caster, int x, int y) {
		if(caster.ap >= skill.ap){
			LocationObject creature = map[x][y].creature;
			if(creature != null){
				return useSkill(skill, caster, creature);
			}
			
			LocationObject go = map[x][y].go;
			if(go != null){
				return useSkill(skill, caster, go);
			}
			
			return false;
		}
		else{
			Log.debug("Not enough AP to cast " + skill.title);
			return false;
		}
	}
	
	private boolean useSkill(Skill skill, LocationObject caster, LocationObject target){
		float delta = getRange(caster, target);
		
		if(delta < skill.range){
			if(isTurnBased){
				caster.ap -= skill.ap;
			}
			
			for(int i = 0; i < skill.effects.length; ++i){
				if(skill.effects[i] != null){
					skill.effects[i].execute(caster, target);
					return true;
				}
			}
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
}
