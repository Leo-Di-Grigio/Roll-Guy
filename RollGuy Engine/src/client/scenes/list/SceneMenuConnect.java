package client.scenes.list;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import client.net.Network;
import client.scenes.Scene;

public class SceneMenuConnect extends Scene {

	public SceneMenuConnect(String title, Network net) {
		super(title);
		ui = new UIMenuConnect(net);
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

	@Override
	public void dispose() {
		
	}
}
