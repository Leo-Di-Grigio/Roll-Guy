package game.cycle.scene.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.Version;
import game.cycle.input.UserInput;
import game.cycle.scene.Scene;
import game.cycle.scene.game.world.World;
import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.ui.list.UIGame;
import game.resources.Fonts;
import game.resources.Resources;

public class SceneGame extends Scene {

	// mode
	private boolean freeCameraMode;
	
	// ui
	private UIGame uimenu;
	private BitmapFont font;
	
	// data
	private World world;
	private Database database;
	
	public SceneGame() {
		this.database = new Database();
		this.ui = uimenu = new UIGame(this);
		font = Resources.getFont(Fonts.fontDefault);
		world = new World(uimenu);
		
		// test
		loadLocation(0, 0, 0);
	}

	public void loadLocation(int id, int playerPosX, int playerPosY) {
		world.loadLocation(id, playerPosX, playerPosY);
	}

	public void saveLocation() {
		world.saveLocation();
	}

	public void gameModeTurnBased(boolean playerTurn) {
		uimenu.turnBased(true, world.getLocation().playerTurn);
		world.gameModeTurnBased(playerTurn);
	}

	public void gameModeRealTime() {
		uimenu.turnBased(false, false);
		world.gameModeRealTime();
	}
	
	public void endTurn() {
		uimenu.turnBased(true, world.getLocation().playerTurn);
		world.endTurn();
	}
	
	public void nextTurn() {
		uimenu.turnBased(true, world.getLocation().playerTurn);
	}
	
	public float speed = 5.0f;
	@Override
	public void update(OrthographicCamera camera) {
		if(freeCameraMode){
			if(UserInput.key(Keys.W)){
				camera.translate(0.0f, speed);
			}
			if(UserInput.key(Keys.S)){
				camera.translate(0.0f, -speed);
			}
			if(UserInput.key(Keys.A)){
				camera.translate(-speed, 0.0f);
			}
			if(UserInput.key(Keys.D)){
				camera.translate(speed, 0.0f);
			}
		}
		else{
			world.updateFreeCamera(camera);
		}
		
		camera.update();
		world.update(camera);
	}
	
	@Override
	public void sceneClick(int button) {
		if(!uimenu.isDialog()){
			if(button == Input.Buttons.LEFT){
				world.action(uimenu);
			}
			
			if(button == Input.Buttons.RIGHT){
				world.playerAttack(uimenu);
			}
		}
	}

	@Override
	public void sceneKey(int key) {
		
	}
	
	@Override
	public void draw(SpriteBatch batch, OrthographicCamera camera) {
		world.draw(batch, camera, ui);
	}

	@Override
	public void drawGui(SpriteBatch batch) {
		String selected = "ui selected: ";
		if(uimenu.selected ){
			selected = "UI: " + uimenu.widgetSelected.title;
		}
		
		drawTextLine(batch, font, "Tile RollBoy v" + Version.version + "." + Version.subversion, 0);
		drawTextLine(batch, font, "Game scene", 1);
		drawTextLine(batch, font, selected, 2);
		drawTextLine(batch, font, "FPS: " + Gdx.graphics.getFramesPerSecond(), 3);
		drawTextLine(batch, font, "Tiles: " + world.getLocation().counter, 5);
	}

	public World getWorld() {
		return world;
	}
	
	public void freeCameraMode(){
		freeCameraMode = !freeCameraMode;
	}
	
	@Override
	public void dispose() {
		database.dispose();
	}
}
