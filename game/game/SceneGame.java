package game;

import resources.Cursors;
import resources.Fonts;
import resources.Resources;
import ui.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import cycle.input.KeyBinds;
import cycle.input.UserInput;
import cycle.scene.Scene;
import game.Version;
import game.cycle.scene.ui.list.UIGame;
import game.script.game.event.Logic;
import game.state.State;
import game.state.database.Database;
import game.state.location.creature.items.Item;
import game.state.skill.Skill;

public class SceneGame extends Scene {

	// mode
	private boolean freeCameraMode;
	private boolean losMode;
	
	// ui
	private UIGame uimenu;
	private BitmapFont font;
	
	// data
	private State state;
	private Database database;
	
	public SceneGame() {
		this.database = new Database();
		this.ui = uimenu = new UIGame(this);
		this.font = Resources.getFont(Fonts.FONT_DEFAULT);
		this.state = new State(uimenu);
		new Logic(this, uimenu);
		
		// test
		loadLocation(0, 0, 0);
	}

	public State getState() {
		return state;
	}
	
	public void loadLocation(int id, int playerPosX, int playerPosY) {
		state.loadLocation(id, playerPosX, playerPosY);
	}

	public void saveLocation() {
		state.saveLocation();
	}
	
	public boolean cameraMoved;
	public float speed = 5.0f;
	
	@Override
	public void update(OrthographicCamera camera) {
		if(!UI.isConsoleVisible()){
			if(freeCameraMode){
				cameraMoved = false;
				
				if(UserInput.key(Keys.W)){
					camera.translate(0.0f, speed);
					cameraMoved = true;
				}
				if(UserInput.key(Keys.S)){
					camera.translate(0.0f, -speed);
					cameraMoved = true;
				}
				if(UserInput.key(Keys.A)){
					camera.translate(-speed, 0.0f);
					cameraMoved = true;
				}
				if(UserInput.key(Keys.D)){
					camera.translate(speed, 0.0f);
					cameraMoved = true;
				}
				
				if(cameraMoved){
					speed += 0.1f;
					if(speed >= 30.0f){
						speed = 30.0f;
					}
				}
				else{
					speed = 5.0f;
				}
			}
			else{
				state.updateFreeCamera(camera);
			}
		}
		
		camera.update();
		state.update(camera, uimenu, losMode);
	}
	
	@Override
	public void sceneClick(int button) {
		if(!uimenu.isDialog()){
			if(button == Input.Buttons.LEFT){
				if(Cursors.getSelectedSkill() != null){
					Cursors.setSelectedSkill(null);
				}
				else{
					state.actionFirst(uimenu);
				}
			}
			else if(button == Input.Buttons.RIGHT){
				state.actionSecond(uimenu);
			}
		}
	}

	@Override
	public void sceneKey(int key) {
		if(key == KeyBinds.keyActionBar0){
			uimenu.actionBar.clickAction(0);
		}
		else if(key == KeyBinds.keyActionBar1){
			uimenu.actionBar.clickAction(1);
		}
		else if(key == KeyBinds.keyActionBar2){
			uimenu.actionBar.clickAction(2);
		}
		else if(key == KeyBinds.keyActionBar3){
			uimenu.actionBar.clickAction(3);
		}
		else if(key == KeyBinds.keyActionBar4){
			uimenu.actionBar.clickAction(4);
		}
		else if(key == KeyBinds.keyActionBar5){
			uimenu.actionBar.clickAction(5);
		}
		else if(key == KeyBinds.keyActionBar6){
			uimenu.actionBar.clickAction(6);
		}
		else if(key == KeyBinds.keyActionBar7){
			uimenu.actionBar.clickAction(7);
		}
		else if(key == KeyBinds.keyActionBar8){
			uimenu.actionBar.clickAction(8);
		}
		else if(key == KeyBinds.keyActionBar9){
			uimenu.actionBar.clickAction(9);
		}
		else if(key == KeyBinds.keyActionBar10){
			uimenu.actionBar.clickAction(10);
		}
		else if(key == KeyBinds.keyActionBar11){
			uimenu.actionBar.clickAction(11);
		}
		else if(key == KeyBinds.inventory){
			uimenu.showInventory();
		}
	}
	
	@Override
	public void draw(SpriteBatch batch, OrthographicCamera camera) {
		state.draw(batch, camera, uimenu, losMode);
	}

	@Override
	public void drawGui(SpriteBatch batch) {
		String selected = "ui selected: ";
		if(uimenu.isSelected()){
			selected = "UI: " + uimenu.getSelected().getTitle();
		}
		
		drawTextLine(batch, font, "Tile RollBoy v" + Version.version + "." + Version.subversion, 0);
		drawTextLine(batch, font, "Game scene", 1);
		drawTextLine(batch, font, selected, 2);
		drawTextLine(batch, font, "["+state.getSelectedNode().x+":"+state.getSelectedNode().y+"]: "+state.getSelectedCreature(), 6);
		
		int fps = Gdx.graphics.getFramesPerSecond();
		if(fps >= 45){
			font.setColor(0, 1, 0, 1);
		}
		else if(fps >= 30){
			font.setColor(1, 1, 0, 1);
		}
		else {
			font.setColor(1, 0, 0, 1);
		}
		
		drawTextLine(batch, font, "FPS: " + fps, 3);
		font.setColor(1, 1, 1, 1);
		
		updateSelectedItem(batch);
		updateSelectedSkill(batch);
	}
	
	private void updateSelectedItem(SpriteBatch batch) {
		Item item = Cursors.getSelectedItem();
		
		if(item != null){
			int texX = item.proto.sizeX()*32;
			int texY = item.proto.sizeY()*32;
			int x = UserInput.mouseX;
			int y = Gdx.graphics.getHeight() - UserInput.mouseY;
			batch.draw(item.tex, x, y - texY, texX, texY);
		}
	}
	
	private void updateSelectedSkill(SpriteBatch batch) {
		Skill skill = Cursors.getSelectedSkill();
		
		if(skill != null){
			int x = UserInput.mouseX;
			int y = Gdx.graphics.getHeight() - UserInput.mouseY;
			batch.draw(skill.tex, x + 16, y - 16, 32, 32);
		}
	}
	
	public void freeCameraMode(){
		freeCameraMode = !freeCameraMode;
	}

	public void losMode() {
		losMode = !losMode;
	}
	
	@Override
	public void dispose() {
		database.dispose();
	}
}
