package game.cycle.scene.game.world;

import game.cycle.input.UserInput;
import game.cycle.scene.game.world.creature.Player;
import game.cycle.scene.game.world.map.Location;
import game.cycle.scene.game.world.map.LocationLoader;
import game.cycle.scene.ui.list.UIGame;
import game.resources.Resources;
import game.resources.Tex;
import game.tools.Log;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Disposable;

public class World implements Disposable {
	// location list
	private static final String locationsList = "data/locations.txt";
	private HashMap<Integer, String> locations;
	
	// data
	private Player player;
	private Location currentLocation;
	
	// cursor
	public int selectedNodeX;
	public int selectedNodeY;
	private Vector3 cursorPos;
	private Sprite tileSelectCursor;
	private Sprite tileWaypoint;
	
	public World() {
		locations = new HashMap<Integer, String>();
		loadList();
		loadMap(0);
		
		player = new Player();
		currentLocation.map[0][0].creature = player;
		
		cursorPos = new Vector3();
		tileSelectCursor = new Sprite(Resources.getTex(Tex.tileSelect));
		tileWaypoint = new Sprite(Resources.getTex(Tex.tileWaypoint));
	}
	
	private void loadList() {
		try{
			File titles = new File(locationsList);
			Scanner in = new Scanner(titles);
			String line = null;
			
			while(in.hasNextLine()){
				line = in.nextLine();
				
				if(!line.startsWith("#")){
					String [] arr = line.split(";");
					
					int id = Integer.parseInt(arr[0]);
					String path = arr[1];
					
					locations.put(id, path);
				}
				else{
					Log.debug(line);
				}
			}
			
			in.close();
		} 
		catch (FileNotFoundException e) {
			Log.err(locationsList + " does not exist");
		}		
	}

	public void loadMap(int id){
		String filePath = locations.get(id);
		
		if(currentLocation != null){
			currentLocation.dispose();
			currentLocation = null;
		}
		
		currentLocation = LocationLoader.loadMap(filePath);
	}

	public Location getLocation(){
		return currentLocation;
	}
	
	public void draw(SpriteBatch batch) {
		if(currentLocation != null){
			currentLocation.draw(player, batch);
			
			selectedNodeX = ((int)cursorPos.x) / Location.tileSize;
			selectedNodeY = ((int)cursorPos.y) / Location.tileSize;
			
			if(currentLocation.inBound(selectedNodeX, selectedNodeY)){
				int posX = selectedNodeX * Location.tileSize;
				int posY = selectedNodeY * Location.tileSize;
				tileSelectCursor.setPosition(posX, posY);
				tileSelectCursor.draw(batch);
			}
			
			if(player.isMoved){
				if(player.path != null){
					for(Point point: player.path){
						tileWaypoint.setPosition((float)(point.getX()*Location.tileSize), (float)(point.getY()*Location.tileSize));
						tileWaypoint.draw(batch);	
					}
				}
			}
			
			player.draw(batch);
		}
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
		player.pos.add(0.0f, 1.0f);
	}

	public void moveDown() {
		player.pos.add(0.0f, -1.0f);
	}

	public void moveLeft() {
		player.pos.add(-1.0f, 0.0f);
	}

	public void moveRight() {
		player.pos.add(1.0f, 0.0f);
	}

	public void update(OrthographicCamera camera) {
		camera.translate(-camera.position.x, -camera.position.y);
		camera.translate(player.pos.x + Location.tileSize/2, player.pos.y);
		camera.update();
		
		// pick a cursor position
		Ray ray = camera.getPickRay(UserInput.mouseX, UserInput.mouseY);
    	float distance = -ray.origin.z/ray.direction.z;
    	cursorPos = new Vector3();
    	cursorPos.set(ray.direction).scl(distance).add(ray.origin);
    	
    	// characters update
    	player.update(currentLocation.map);
	}

	public void editorWall() {
		currentLocation.editorWall(selectedNodeX, selectedNodeY);
	}

	public void editorNpc() {
		currentLocation.editorNpc(selectedNodeX, selectedNodeY);
	}

	public void playerMove(UIGame ui) {
		if(currentLocation.inBound(selectedNodeX, selectedNodeY)){
			if(currentLocation.map[selectedNodeX][selectedNodeY].creature != null){
				currentLocation.talkWithNpc(player, ui, selectedNodeX, selectedNodeY);
			}
			else{
				player.move(currentLocation.map, currentLocation.sizeX, currentLocation.sizeY, selectedNodeX, selectedNodeY);
			}
		}
		else{
			player.path = null;
		}
	}

	public void playerAttack() {
		
	}
}
