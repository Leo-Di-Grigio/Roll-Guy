package client.scenes.game;

import client.input.UserInput;
import client.net.Network;
import client.resources.Resources;
import client.scenes.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import common.Version;
import common.resources.Fonts;

public class SceneGame extends Scene {

	private BitmapFont font;
	private Network net;
	private Location loc;

	public SceneGame(String title, Network net, Location loc) {
		super(title);
		this.ui = new UIGame(this);
		this.net = net;
		this.loc = loc;
		this.font = Resources.getFont(Fonts.DEFAULT);
	}

	@Override
	public void update(float delta, OrthographicCamera camera) {
		if(UserInput.key(Keys.W)){
			loc.moveUp();
		}
		if(UserInput.key(Keys.S)){
			loc.moveDown();
		}
		if(UserInput.key(Keys.A)){
			loc.moveLeft();
		}
		if(UserInput.key(Keys.D)){
			loc.moveRight();
		}
		
		loc.update(delta, camera);
	}

	@Override
	public void draw(SpriteBatch batch, OrthographicCamera camera) {
		loc.draw(batch);
	}

	@Override
	public void drawgui(SpriteBatch batch) {
		drawTextLine(batch, font, "Game " + Version.getTitle(), 0);
		drawTextLine(batch, font, "FPS: " + Gdx.graphics.getFramesPerSecond(), 1);
	}

	@Override
	public void sceneClick(int button) {
		
	}

	@Override
	public void sceneKey(int key) {

	}
	
	@Override
	public void dispose() {

	}
	
	public void showMainMenu(){
		net.showMainMenu();
	}
}
