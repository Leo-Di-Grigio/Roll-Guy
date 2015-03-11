package game.cycle.scene.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.Version;
import game.cycle.input.UserInput;
import game.cycle.scene.Scene;
import game.cycle.scene.game.world.World;
import game.cycle.scene.ui.list.UIGame;
import game.resources.Fonts;
import game.resources.Resources;

public class SceneGame extends Scene {

	// ui
	private UIGame uimenu;
	private BitmapFont font;
	
	// data
	private World world;
	
	public SceneGame() {
		font = Resources.getFont(Fonts.fontDefault);
		this.ui = uimenu = new UIGame();
		
		world = new World();
	}
	
	@Override
	public void update(OrthographicCamera camera) {
		if(UserInput.key(Keys.W)){
			world.moveUp();
		}
		if(UserInput.key(Keys.S)){
			world.moveDown();
		}
		if(UserInput.key(Keys.A)){
			world.moveLeft();
		}
		if(UserInput.key(Keys.D)){
			world.moveRight();
		}
		
		world.update(camera);
	}
	
	@Override
	public void sceneClick(int button) {
		
	}

	@Override
	public void sceneKey(int key) {
		
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		world.draw(batch);
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
		drawTextLine(batch, font, "Node selected x: " + world.selectedNodeX + " y: " + world.selectedNodeY, 4);
		drawTextLine(batch, font, "Tiles: " + world.getLocation().counter, 5);
	}

	@Override
	public void dispose() {

	}
}
