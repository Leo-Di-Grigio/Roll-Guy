package client.scenes.list;

import server.Server;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import client.net.Network;
import client.scenes.Scene;

public class SceneMenuServer extends Scene {

	public SceneMenuServer(String title, Network net, Server server) {
		super(title);
		ui = new UIMenuServer(net, server);
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

	}

	@Override
	public void sceneClick(int button) {

	}

	@Override
	public void sceneKey(int key) {

	}
}
