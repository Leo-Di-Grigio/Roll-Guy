package game.cycle.scene.game.world;

import game.cycle.input.UserInput;
import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.game.world.creature.NPC;
import game.cycle.scene.game.world.creature.Player;
import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.database.GameConst;
import game.cycle.scene.game.world.event.LocationEvent;
import game.cycle.scene.game.world.go.GO;
import game.cycle.scene.game.world.go.GOFactory;
import game.cycle.scene.game.world.map.Location;
import game.cycle.scene.game.world.map.LocationLoader;
import game.cycle.scene.game.world.map.LocationProto;
import game.cycle.scene.game.world.skill.Skill;
import game.cycle.scene.ui.list.UIGame;
import game.resources.Cursors;
import game.resources.Resources;
import game.resources.Tex;
import game.script.ui.app.ui_ExitGame;
import game.tools.Const;

import java.awt.Point;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Disposable;

public class World implements Disposable {	
	// data
	private Player player;
	private Location currentLocation;
	
	// cursor
	private int cursorImage = Cursors.cursorDefault;
	private Point select;
	private Vector3 cursorPos;
	private Sprite tileSelectCursor;
	private Sprite tileWaypoint;
	
	public World(UIGame uimenu) {
		player = new Player();
		uimenu.setPlayer(player);
		
		select = new Point();
		cursorPos = new Vector3();
		tileSelectCursor = new Sprite(Resources.getTex(Tex.tileSelect));
		tileWaypoint = new Sprite(Resources.getTex(Tex.tileWaypoint));
		
		LocationProto proto = Database.getLocation(0);
		
		if(proto == null){
			proto = new LocationProto();
			proto.title = "Default";
			proto.filePath = "default";
			proto.note = "";
			
			Database.insertLocation(proto);
			Database.loadLocations();
			LocationLoader.createNew(proto, 32, 32, 1);
		}
		else{
			LocationLoader.createNew(proto, 32, 32, 1);
		}
	}

	public void loadLocation(int id, int playerPosX, int playerPosY){
		player.resetAp();
		
		if(currentLocation != null){
			currentLocation.dispose();
			currentLocation = null;
		}
		
		currentLocation = LocationLoader.loadLocation(id);
		
		// place player
		if(currentLocation != null && currentLocation.inBound(playerPosX, playerPosY)){
			currentLocation.addCreature(player, playerPosX, playerPosY);
			player.sprite.setPosition(playerPosX * GameConst.tileSize, playerPosY * GameConst.tileSize);
		}
		else{
			currentLocation.addCreature(player, 0, 0);
			player.sprite.setPosition(0, 0);
		}
	}

	public void saveLocation() {
		if(currentLocation != null){
			LocationLoader.saveLocation(currentLocation);
		}
	}
	
	public Location getLocation(){
		return currentLocation;
	}
	
	public void draw(SpriteBatch batch, OrthographicCamera camera, UIGame ui) {
		if(currentLocation != null){
			// draw location
			currentLocation.draw(camera, batch);
	
			// draw player waypoints
			if(player.isMoved){
				if(player.path != null){
					for(Point point: player.path){
						tileWaypoint.setPosition((float)(point.getX()*GameConst.tileSize), (float)(point.getY()*GameConst.tileSize));
						tileWaypoint.draw(batch);	
					}
				}
			}
			
			// update cursor
			updateCursor(batch, ui);
		}
	}

	private void updateCursor(SpriteBatch batch, UIGame ui) {
		select.x = ((int)cursorPos.x) / GameConst.tileSize;
		select.y = ((int)cursorPos.y) / GameConst.tileSize;
		
		if(ui.selected){
			Cursors.setCursor(Cursors.cursorDefault);
		}
		else{
			switch (ui.getMode()) {
				case UIGame.modeSkillNull:
				case UIGame.modeSkillMelee:
				case UIGame.modeSkillRange:
				case UIGame.modeSkillSpell:
					cursorImage = Cursors.cursorCast;
					Cursors.setCursor(Cursors.cursorCast);
					break;
					
				default:
					setSceneCursor(batch);
					break;
			}	
		}
	}
	
	private void setSceneCursor(SpriteBatch batch) {
		if(currentLocation.inBound(select.x, select.y)){
			int posX = select.x * GameConst.tileSize;
			int posY = select.y * GameConst.tileSize;
			tileSelectCursor.setPosition(posX, posY);
			tileSelectCursor.draw(batch);
		
			if(isInterractive(select.x, select.y, player.id)){
				if(cursorImage != Cursors.cursorTalking){
					cursorImage = Cursors.cursorTalking;
					Cursors.setCursor(cursorImage);
				}
			}
			else{
				if(cursorImage != Cursors.cursorDefault){
					cursorImage = Cursors.cursorDefault;
					Cursors.setCursor(cursorImage);
				}
			}
		}
	}

	private boolean isInterractive(int x, int y, int playerid) {
		return currentLocation.isInteractive(x, y, playerid);
	}

	@Override
	public void dispose() {
		currentLocation.dispose();
		tileSelectCursor = null;
	}

	public void moveUp() {
		player.sprite.translate(0.0f, 1.0f);
	}

	public void moveDown() {
		player.sprite.translate(0.0f, -1.0f);
	}

	public void moveLeft() {
		player.sprite.translate(-1.0f, 0.0f);
	}

	public void moveRight() {
		player.sprite.translate(1.0f, 0.0f);
	}
	
	public void gameModeTurnBased(boolean playerTurn) {
		currentLocation.gameModeTurnBased(playerTurn, player);
	}

	public void gameModeRealTime() {
		currentLocation.gameModeRealTime();
		player.resetAp();
	}

	public void update(OrthographicCamera camera) {		
		// pick a cursor position
		Ray ray = camera.getPickRay(UserInput.mouseX, UserInput.mouseY);
    	float distance = -ray.origin.z/ray.direction.z;
    	cursorPos = new Vector3();
    	cursorPos.set(ray.direction).scl(distance).add(ray.origin);
    	
    	// characters update
    	currentLocation.update(player);
	}
	
	public boolean endTurn() {
		if(!player.isMoved){
			currentLocation.npcTurn(player);
			return true;
		}
		else{
			return false;
		}
	}

	// Click event
	public void actionFirst(UIGame ui) {
		switch(ui.getMode()) {
			case UIGame.modeGOAdd:
				currentLocation.goAdd(select.x, select.y, ui);
				break;
				
			case UIGame.modeGOEdit:
				currentLocation.goEdit(select.x, select.y, ui);
				break;
					
			case UIGame.modeNpcAdd:
				currentLocation.npcAdd(select.x, select.y, ui);
				break;
					
			case UIGame.modeNpcEdit:
				currentLocation.npcEdit(select.x, select.y, ui);
				break;
				
			case UIGame.modeTerrainBrush1:
			case UIGame.modeTerrainBrush2:
			case UIGame.modeTerrainBrush3:
				currentLocation.editorTerrain(select.x, select.y, ui, ui.getMode());
				break;
					
			case UIGame.modeSkillNull:
			case UIGame.modeSkillMelee:
			case UIGame.modeSkillRange:
			case UIGame.modeSkillSpell:
				currentLocation.useSkill(player.getUsedSkill(), player, select.x, select.y);
				break;
				
			case Const.invalidId:
			default:
				playerAction(ui);
				break;
		}
	}
	
	private void playerAction(UIGame ui) {
		if(currentLocation.inBound(select.x, select.y)){
			player.move(currentLocation, select.x, select.y);
			currentLocation.talkWithNpc(player, ui, select.x, select.y);
		}
	}
	
	public void actionSecond(UIGame ui) {
		if(ui.getMode() == Const.invalidId){
			if(currentLocation.inBound(select.x, select.y)){
				Creature creature = currentLocation.map[select.x][select.y].creature;
				if(creature != null){
					currentLocation.useSkill(player.skills.get(0), player, select.x, select.y);
					return;
				}
			
				GO go = currentLocation.map[select.x][select.y].go;
				if(go != null){
					if(go.proto.usable){
						currentLocation.useGO(player, go);
					}
					else if(go.proto.container){
						currentLocation.containerGO(player, go, ui);
					}
				}
			}
		}
		else{
			ui.setMode(Const.invalidId);
		}
	}

	public void updateFreeCamera(OrthographicCamera camera) {
		camera.translate(-camera.position.x, -camera.position.y);
		camera.translate(player.sprite.getX() + GameConst.tileSize/2, player.sprite.getY());
	}

	public void destroy(LocationObject object) {
		if(object.isPlayer()){
			new ui_ExitGame().execute();
		}
		else if(object.isNPC()){
			// place a corpse 
			NPC npc = (NPC)object;
			GO go = GOFactory.getGo(4, npc.pos.x, npc.pos.y, 0, 0, 0, 0);
			go.inventory = npc.inventory;
			currentLocation.goAdd(npc.pos.x, npc.pos.y, go);
			
			// remove npc
			currentLocation.removeObject(npc);
		}
		else{
			currentLocation.removeObject(object);
		}
	}

	public Point getSelectedNode(){
		return select;
	}

	public String getSelectedCreature() {
		return currentLocation.getSelectedCreature(select.x, select.y);
	}

	public void addLocationEvent(LocationEvent event) {
		currentLocation.addLocationEvent(event);
	}

	public void resetPlayerSkill() {
		player.setUsedSkill(null);
	}
	
	public void selfcastSkill(Creature target, Skill skill){
		currentLocation.useSkill(skill, target);
	}
	
	public Vector3 getCursorPos(){
		return cursorPos;
	}
}
