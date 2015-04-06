package game.cycle.scene.game.world;

import game.cycle.input.UserInput;
import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.database.GameConst;
import game.cycle.scene.game.world.event.LocationEvent;
import game.cycle.scene.game.world.location.Editor;
import game.cycle.scene.game.world.location.Location;
import game.cycle.scene.game.world.location.LocationObject;
import game.cycle.scene.game.world.location.LocationProto;
import game.cycle.scene.game.world.location.creature.Creature;
import game.cycle.scene.game.world.location.creature.Player;
import game.cycle.scene.game.world.location.go.GO;
import game.cycle.scene.game.world.location.manager.LocationManager;
import game.cycle.scene.game.world.skill.Skill;
import game.cycle.scene.ui.list.UIGame;
import game.resources.Cursors;
import game.resources.Resources;
import game.resources.Tex;
import game.script.game.event.GameEvents;
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
			LocationManager.createNew(proto, 32, 32, 1);
		}
		else{
			LocationManager.createNew(proto, 32, 32, 1);
		}
	}

	public void loadLocation(int id, int playerPosX, int playerPosY){
		if(currentLocation != null){
			currentLocation.dispose();
			currentLocation = null;
		}
		
		currentLocation = LocationManager.loadLocation(id);
		
		// place player
		if(currentLocation != null && currentLocation.inBound(playerPosX, playerPosY)){
			currentLocation.addCreature(player, playerPosX, playerPosY);
		}
		else{
			currentLocation.addCreature(player, 0, 0);
		}
		
		player.resetAp();
	}

	public void saveLocation() {
		if(currentLocation != null){
			LocationManager.saveLocation(currentLocation);
		}
	}
	
	public Location getLocation(){
		return currentLocation;
	}
	
	public void draw(SpriteBatch batch, OrthographicCamera camera, UIGame ui, boolean losMode) {
		if(currentLocation != null){
			// draw location
			currentLocation.draw(camera, batch, losMode, ui);
	
			// draw player waypoints
			if(player.isMoved()){
				if(player.getPath() != null){
					for(Point point: player.getPath()){
						tileWaypoint.setPosition((float)(point.getX()*GameConst.TILE_SIZE), (float)(point.getY()*GameConst.TILE_SIZE));
						tileWaypoint.draw(batch);	
					}
				}
			}
			
			// update cursor
			updateCursor(batch, ui);
		}
	}

	private void updateCursor(SpriteBatch batch, UIGame ui) {
		select.x = ((int)cursorPos.x) / GameConst.TILE_SIZE;
		select.y = ((int)cursorPos.y) / GameConst.TILE_SIZE;
		
		if(ui.selected){
			Cursors.setCursor(Cursors.cursorDefault);
		}
		else{
			if(player.getUsedSkill() != null){
				if(ui.getSkillMode()){
					Cursors.setCursor(Cursors.cursorCast);
					
					if(currentLocation.inBound(select.x, select.y)){
						batch.draw(tileSelectCursor, select.x*GameConst.TILE_SIZE, select.y*GameConst.TILE_SIZE, 32, 32);
					}
				}
				else{	
					setSceneCursor(batch);
				}
			}
			else{
				Cursors.setCursor(Cursors.cursorDefault);	
			}
		}
	}
	
	private void setSceneCursor(SpriteBatch batch) {
		if(currentLocation.inBound(select.x, select.y)){
			int posX = select.x * GameConst.TILE_SIZE;
			int posY = select.y * GameConst.TILE_SIZE;
			
			tileSelectCursor.setPosition(posX, posY);
			tileSelectCursor.draw(batch);
		
			if(isInterractive(select.x, select.y, player.getGUID())){
				Cursors.setCursor(Cursors.cursorTalking);
			}
			else{
				Cursors.setCursor(Cursors.cursorDefault);
			}
		}
		else{
			Cursors.setCursor(Cursors.cursorDefault);
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
		player.getSprite().translate(0.0f, 1.0f);
	}

	public void moveDown() {
		player.getSprite().translate(0.0f, -1.0f);
	}

	public void moveLeft() {
		player.getSprite().translate(-1.0f, 0.0f);
	}

	public void moveRight() {
		player.getSprite().translate(1.0f, 0.0f);
	}
	
	public void gameModeTurnBased(boolean playerTurn) {
		currentLocation.gameModeTurnBased(playerTurn);
	}

	public void gameModeRealTime() {
		currentLocation.gameModeRealTime(player);
	}

	public void update(OrthographicCamera camera) {		
		// pick a cursor position
		Ray ray = camera.getPickRay(UserInput.mouseX, UserInput.mouseY);
    	float distance = -ray.origin.z/ray.direction.z;
    	cursorPos = new Vector3();
    	cursorPos.set(ray.direction).scl(distance).add(ray.origin);
    	
    	// characters update
    	currentLocation.update(player, camera);
	}
	
	public boolean endTurn() {
		if(!player.isMoved()){
			currentLocation.cycle.npcTurn(player, currentLocation);
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
				Editor.goAdd(currentLocation, select.x, select.y, ui);
				break;
				
			case UIGame.modeGOEdit:
				Editor.goEdit(currentLocation, select.x, select.y, ui);
				break;
					
			case UIGame.modeNpcAdd:
				Editor.npcAdd(currentLocation, select.x, select.y, ui);
				break;
					
			case UIGame.modeNpcEdit:
				Editor.npcEdit(currentLocation, select.x, select.y, ui);
				break;
				
			case UIGame.modeTerrainBrush1:
			case UIGame.modeTerrainBrush2:
			case UIGame.modeTerrainBrush3:
			case UIGame.modeTerrainFill:
				Editor.editorTerrain(currentLocation, select.x, select.y, ui, ui.getMode());
				break;
					
			case UIGame.modeSkillNull:
			case UIGame.modeSkillMelee:
			case UIGame.modeSkillRange:
			case UIGame.modeSkillSpell:
				player.useSkill(currentLocation, player.getUsedSkill(), select.x, select.y);
				break;
				
			case Const.INVALID_ID:
			default:
				playerAction(ui);
				break;
		}
	}
	
	private void playerAction(UIGame ui) {
		if(currentLocation.inBound(select.x, select.y)){
			player.move(currentLocation, select.x, select.y);
			if(player.isMoved()){
				ui.openContainer(null);
				ui.openCorpse(null);
			}
			else{
				currentLocation.interactWithNpc(player, ui, select.x, select.y);
			}
		}
	}
	
	public void actionSecond(UIGame ui) {
		if(ui.getMode() == Const.INVALID_ID){
			if(currentLocation.inBound(select.x, select.y)){
				Creature creature = currentLocation.map[select.x][select.y].creature;
				if(creature != null){
					if(creature.isAlive()){
						player.useSkill(currentLocation, player.skills.get(0), select.x, select.y);
						return;
					}
				}
			
				GO go = currentLocation.map[select.x][select.y].go;
				if(go != null){
					if(go.proto.usable){
						currentLocation.useGO(player, go);
					}
					else if(go.proto.container){
						player.containerGO(go, ui);
					}
				}
			}
		}
		else{
			ui.setMode(Const.INVALID_ID);
			GameEvents.playerUseSkill(null);
		}
	}

	public void updateFreeCamera(OrthographicCamera camera) {
		camera.translate(-camera.position.x, -camera.position.y);
		camera.translate(player.getSprite().getX() + GameConst.TILE_SIZE/2, player.getSprite().getY());
	}

	public void destroy(LocationObject object) {
		if(object.isPlayer()){
			new ui_ExitGame().execute();
		}
		else{
			currentLocation.killObject(object);
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
	
	public void playerSelfCastSkill(Skill skill){
		player.useSkill(currentLocation, skill, player);
	}
	
	public void playerUseSkill(UIGame ui, Skill skill) {
		player.setUsedSkill(ui, skill);
	}

	public void resetPlayerSkill(UIGame ui) {
		player.setUsedSkill(ui, null);
	}
	
	public void resetPlayer() {
		player.resetAp();
	}
	
	public Vector3 getCursorPos(){
		return cursorPos;
	}
	
	public Player getPlayer() {
		return player;
	}
}
