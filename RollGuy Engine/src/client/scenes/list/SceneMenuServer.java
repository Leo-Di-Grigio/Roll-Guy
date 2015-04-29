package client.scenes.list;

import server.Server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import common.Version;
import common.resources.Fonts;
import client.net.Network;
import client.resources.Resources;
import client.scenes.Scene;

public class SceneMenuServer extends Scene {

	private BitmapFont font;

	public SceneMenuServer(String title, Network net, Server server) {
		super(title);
		ui = new UIMenuServer(net, server);
		font = Resources.getFont(Fonts.DEFAULT);
	}
	
	@Override
	public void dispose() {

	}

	@Override
	public void update(float delta, OrthographicCamera camera) {

	}

	@Override
	public void draw(SpriteBatch batch, OrthographicCamera camera) {

	}

	@Override
	public void drawgui(SpriteBatch batch) {
		drawTextLine(batch, font, "Menu Server " + Version.getTitle(), 0);
		drawTextLine(batch, font, "FPS: " + Gdx.graphics.getFramesPerSecond(), 1);
	}

	@Override
	public void sceneClick(int button) {

	}

	@Override
	public void sceneKey(int key) {

	}
}
