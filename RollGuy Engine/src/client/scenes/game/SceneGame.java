package client.scenes.game;

import client.net.Network;
import client.resources.Resources;
import client.scenes.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import common.Version;
import common.resources.Fonts;

public class SceneGame extends Scene {

	private BitmapFont font;

	public SceneGame(String title, Network net) {
		super(title);
		ui = new UIGame();
		font = Resources.getFont(Fonts.DEFAULT);
	}

	@Override
	public void update(float delta, OrthographicCamera camera) {

	}

	@Override
	public void draw(SpriteBatch batch, OrthographicCamera camera) {
		
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
}
