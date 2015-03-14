package game.cycle.scene.game.world;

import game.cycle.input.UserInput;
import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.world.creature.Player;
import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.go.GOProto;
import game.cycle.scene.game.world.map.Location;
import game.cycle.scene.game.world.map.LocationLoader;
import game.cycle.scene.game.world.map.LocationProto;
import game.cycle.scene.ui.UI;
import game.cycle.scene.ui.list.UIGame;
import game.resources.Cursors;
import game.resources.Resources;
import game.resources.Tex;

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
	public int selectedNodeX;
	public int selectedNodeY;
	private Vector3 cursorPos;
	private Sprite tileSelectCursor;
	private Sprite tileWaypoint;
	
	public World() {
		player = new Player();
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
			LocationLoader.createNew(proto, 32, 32, 1, player);
		}
		else{
			LocationLoader.createNew(proto, 32, 32, 1, player);
		}
	}

	public void loadLocation(int id, int playerPosX, int playerPosY){
		if(currentLocation != null){
			currentLocation.dispose();
			currentLocation = null;
		}
		
		currentLocation = LocationLoader.loadLocation(id);
		
		// place player
		if(currentLocation != null && currentLocation.inBound(playerPosX, playerPosY)){
			currentLocation.map[playerPosX][playerPosY].creature = player;
			player.sprite.setPosition(playerPosX * Location.tileSize, playerPosY * Location.tileSize);
		}
		else{
			currentLocation.map[0][0].creature = player;
			player.sprite.setPosition(0, 0);
		}
	}

	public void saveLocation() {
		if(currentLocation != null){
			LocationLoader.saveLocation(currentLocation, player);
		}
	}
	
	public Location getLocation(){
		return currentLocation;
	}
	
	public void draw(SpriteBatch batch, OrthographicCamera camera, UI ui) {
		if(currentLocation != null){
			currentLocation.draw(camera, batch);
			
			if(!ui.selected){
				selectedNodeX = ((int)cursorPos.x) / Location.tileSize;
				selectedNodeY = ((int)cursorPos.y) / Location.tileSize;
			
				if(currentLocation.inBound(selectedNodeX, selectedNodeY)){
					int posX = selectedNodeX * Location.tileSize;
					int posY = selectedNodeY * Location.tileSize;
					tileSelectCursor.setPosition(posX, posY);
					tileSelectCursor.draw(batch);
				
					if(isInterractive(selectedNodeX, selectedNodeY, player.id)){
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
			
			if(player.isMoved){
				if(player.path != null){
					for(Point point: player.path){
						tileWaypoint.setPosition((float)(point.getX()*Location.tileSize), (float)(point.getY()*Location.tileSize));
						tileWaypoint.draw(batch);	
					}
				}
			}
		}
	}
	
	private boolean isInterractive(int x, int y, int playerid) {
		if(currentLocation.map[x][y].creature != null){
			if(currentLocation.map[x][y].creature.id != playerid){
				return true;
			}
		}
		if(currentLocation.map[x][y].go != null){
			GOProto go = currentLocation.map[x][y].go.proto;
			
			if(go.container || go.teleport || go.usable){
				return true;
			}
		}
		return false;
	}

	@Override
	public void dispose() {
		currentLocation.dispose();
		tileSelectCursor = null;
	}

	public Player getPlayer(){
		return player;
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

	public void update(OrthographicCamera camera) {		
		// pick a cursor position
		Ray ray = camera.getPickRay(UserInput.mouseX, UserInput.mouseY);
    	float distance = -ray.origin.z/ray.direction.z;
    	cursorPos = new Vector3();
    	cursorPos.set(ray.direction).scl(distance).add(ray.origin);
    	
    	// characters update
    	player.update(currentLocation.map);
	}

	public void editorWall(UIGame ui) {
		currentLocation.editorTerrain(selectedNodeX, selectedNodeY, ui);
	}

	public void editorNpc(UIGame uimenu, int currentClickMode) {
		if(currentClickMode == SceneGame.clickEditorNpcEdit){
			uimenu.setVisibleNPCParamsEdit(currentLocation.map[selectedNodeX][selectedNodeY].creature);
		}
		else{
			currentLocation.editorNpc(selectedNodeX, selectedNodeY, uimenu);
		}
	}

	public void editorGO(UIGame ui, int currentClickMode){ 
		switch(currentClickMode){
			case SceneGame.clickEditorGO:
			case SceneGame.clickEditorGOAdd:
				currentLocation.editorGO(selectedNodeX, selectedNodeY, ui);
				break;
				
			case SceneGame.clickEditorGOEdit:
				currentLocation.editorGOParams(selectedNodeX, selectedNodeY, ui);
				break;
		}
	}

	public void playerAction(UIGame ui) {
		if(currentLocation.inBound(selectedNodeX, selectedNodeY)){
			player.move(currentLocation.map, currentLocation.sizeX, currentLocation.sizeY, selectedNodeX, selectedNodeY);
			currentLocation.talkWithNpc(player, ui, selectedNodeX, selectedNodeY);
			currentLocation.useGO(player, selectedNodeX, selectedNodeY);
		}
	}
	
	public void playerAttack() {
		currentLocation.attack(selectedNodeX, selectedNodeY, player);
	}
}
