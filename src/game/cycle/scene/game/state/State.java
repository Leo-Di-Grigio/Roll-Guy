package game.cycle.scene.game.state;

import game.cycle.input.UserInput;
import game.cycle.scene.game.state.database.Database;
import game.cycle.scene.game.state.database.GameConst;
import game.cycle.scene.game.state.database.proto.LocationProto;
import game.cycle.scene.game.state.event.Event;
import game.cycle.scene.game.state.location.Editor;
import game.cycle.scene.game.state.location.Location;
import game.cycle.scene.game.state.location.creature.Player;
import game.cycle.scene.game.state.location.go.GO;
import game.cycle.scene.game.state.location.manager.LocationManager;
import game.cycle.scene.game.state.skill.Skill;
import game.cycle.scene.ui.list.UIGame;
import game.lua.LuaEngine;
import game.resources.Cursors;
import game.resources.Resources;
import game.resources.tex.Tex;
import game.tools.Const;

import java.awt.Point;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Disposable;

public class State implements Disposable {	
	// data
	private Globals globals;
	private Location currentLocation;
	
	// cursor
	private Vector2 cursorPos2;
	private Vector3 cursorPos3;
	private Sprite tileSelectCursor;
	private Sprite tileWaypoint;
	
	public State(UIGame uimenu) {
		globals = new Globals();
		uimenu.setPlayer(globals.getPlayer());
		
		cursorPos2 = new Vector2();
		cursorPos3 = new Vector3();
		tileSelectCursor = new Sprite(Resources.getTex(Tex.GAMEPLAY_SELECT));
		tileWaypoint = new Sprite(Resources.getTex(Tex.GAMEPLAY_WP));
		
		LocationProto proto = Database.getLocation(0);
		
		if(proto == null){
			proto = new LocationProto("Default", "default", "", null);
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
			LuaEngine.executeLocationEvent(new Event(Event.EVENT_LOCATION_CHANGE, null, null));
			currentLocation.dispose();
			currentLocation = null;
		}
		
		currentLocation = LocationManager.loadLocation(id);
		
		// place player
		if(currentLocation != null && currentLocation.inBound(playerPosX, playerPosY)){
			currentLocation.addObject(getPlayer(), playerPosX, playerPosY, false);
		}
		else{
			currentLocation.addObject(getPlayer(), 0, 0, false);
			playerPosX = 0;
			playerPosY = 0;
		}
		
		if(getPlayer().getDraggedObject() != null){
			if(getPlayer().getDraggedObject().isGO()){
				GO go = (GO)getPlayer().getDraggedObject();
				Editor.goAdd(currentLocation, go, playerPosX, playerPosY);
			}
		}
		
		if(currentLocation != null && currentLocation.proto.eventScript() != null){
			LuaEngine.load(currentLocation.proto.eventScript());
		}
		
		currentLocation.requestUpdate();
		LuaEngine.executeLocationEvent(new Event(Event.EVENT_LOCATION_LOAD, null, null));
	}

	public void saveLocation() {
		if(currentLocation != null){
			LocationManager.saveLocation(currentLocation);
		}
	}
	
	public Location getLocation(){
		return currentLocation;
	}
	
	public Globals getGlobals(){
		return globals;
	}
	
	public Player getPlayer() {
		return globals.getPlayer();
	}
	
	// Update
	public void update(OrthographicCamera camera, UIGame ui, boolean losMode) {		
		// pick a cursor position
		Ray ray = camera.getPickRay(UserInput.mouseX, UserInput.mouseY);
    	float distance = -ray.origin.z/ray.direction.z;
    	cursorPos3.set(ray.direction).scl(distance).add(ray.origin);
    	cursorPos2.set(cursorPos3.x, cursorPos3.y);
    	
    	// characters update
    	currentLocation.update(getPlayer(), camera, ui, losMode);
	}

	public void updateFreeCamera(OrthographicCamera camera) {
		camera.translate(-camera.position.x, -camera.position.y);
		camera.translate(getPlayer().getSprite().getX() + GameConst.TILE_SIZE/2, getPlayer().getSprite().getY());
	}
	
	// Draw
	public void draw(SpriteBatch batch, OrthographicCamera camera, UIGame ui, boolean losMode) {
		if(currentLocation != null){
			// draw location
			currentLocation.draw(camera, batch, losMode, ui, getPlayer());
	
			// draw player waypoints
			if(getPlayer().isMoved()){
				if(getPlayer().getPath() != null){
					for(Point point: getPlayer().getPath()){
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
		if(ui.selected){
			Cursors.setCursor(Cursors.cursorDefault);
		}
		else{
			if(getPlayer().getUsedSkill() != null){
				if(ui.getSkillMode()){
					Cursors.setCursor(Cursors.cursorCast);
					
					if(currentLocation.inBound(cursorPos2)){
						batch.draw(tileSelectCursor, cursorPos2.x, cursorPos2.y);
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
		if(currentLocation.inBound(cursorPos2)){
			tileSelectCursor.setPosition(cursorPos2.x, cursorPos2.y);
			tileSelectCursor.draw(batch);
		
			if(isInterractive(cursorPos2, getPlayer().getGUID())){
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

	private boolean isInterractive(Vector2 pos, int playerid) {
		return currentLocation.isInteractive(pos, playerid);
	}

	@Override
	public void dispose() {
		currentLocation.dispose();
		tileSelectCursor = null;
	}

	public void moveUp() {
		getPlayer().getSprite().translate(0.0f, 1.0f);
	}

	public void moveDown() {
		getPlayer().getSprite().translate(0.0f, -1.0f);
	}

	public void moveLeft() {
		getPlayer().getSprite().translate(-1.0f, 0.0f);
	}

	public void moveRight() {
		getPlayer().getSprite().translate(1.0f, 0.0f);
	}

	// Click event
	public void actionFirst(UIGame ui) {
		switch(ui.getMode()) {
			case UIGame.modeGOAdd:
				Editor.goAdd(currentLocation, ui.getSelectedListGO(), cursorPos2, true);
				break;
				
			case UIGame.modeGOEdit:
				Editor.goEdit(currentLocation, cursorPos2, ui);
				break;
					
			case UIGame.modeNpcAdd:
				Editor.npcAdd(currentLocation, ui.getSelectedListNpc(), cursorPos2, true);
				break;
					
			case UIGame.modeNpcEdit:
				Editor.npcEdit(currentLocation, cursorPos2, ui);
				break;
				
			case UIGame.modeTerrainBrush1:
			case UIGame.modeTerrainBrush2:
			case UIGame.modeTerrainBrush3:
			case UIGame.modeTerrainFill:
				Editor.editorTerrain(currentLocation, cursorPos2, ui, ui.getMode());
				break;
					
			case UIGame.modeSkillNull:
			case UIGame.modeSkillMelee:
			case UIGame.modeSkillRange:
			case UIGame.modeSkillSpell:
				getPlayer().useSkill(currentLocation, getPlayer().getUsedSkill(), cursorPos2);
				break;
				
			case Const.INVALID_ID:
			default:
				playerAction(ui);
				break;
		}
	}
	
	private void playerAction(UIGame ui) {
		if(currentLocation.inBound(cursorPos2)){
			if(getPlayer().isMoved()){
				ui.openContainer(null);
				ui.openCorpse(null);
			}
			else{
				currentLocation.interactWithNpc(getPlayer(), ui, cursorPos2);
			}
		}
	}
	
	public void actionSecond(UIGame ui) {

	}
	
	public void playerSelfCastSkill(Skill skill){
		getPlayer().useSkill(currentLocation, skill, getPlayer());
	}
	
	public void playerUseSkill(UIGame ui, Skill skill) {
		getPlayer().setUsedSkill(ui, skill);
	}
	
	public Vector3 getCursorPos(){
		return cursorPos3;
	}

	public String getSelectedCreature() {
		return currentLocation.getSelectedCreature(cursorPos2);
	}
	
	public Vector2 selected(){
		return cursorPos2;
	}
}
