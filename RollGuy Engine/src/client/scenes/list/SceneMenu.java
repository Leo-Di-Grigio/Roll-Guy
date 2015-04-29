package client.scenes.list;

import client.net.Network;
import client.resources.Resources;
import client.scenes.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import common.resources.Fonts;

public class SceneMenu extends Scene {

	private BitmapFont font;
	private Network net;
	
	public SceneMenu(String title) {
		super(title);
		net = new Network();
		ui = new UIMenu(net);
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
		drawTextLine(batch, font, "Menu", 0);
		drawTextLine(batch, font, "FPS: " + Gdx.graphics.getFramesPerSecond(), 1);
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void sceneClick(int button) {
		
	}

	@Override
	public void sceneKey(int key) {

	}
}
