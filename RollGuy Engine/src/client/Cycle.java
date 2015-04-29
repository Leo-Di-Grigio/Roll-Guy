package client;

import client.input.UserInput;
import client.resources.Resources;
import client.scenes.SceneMng;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class Cycle implements ApplicationListener {

	// data
	private Resources resources;
	
	// graphics
	private static OrthographicCamera camera;
	private SpriteBatch batch;
	private SpriteBatch guibatch;
	
	// scenes
	private SceneMng scenes;
	
	@Override
	public void create() {
		// render configuration
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		// user input
		Gdx.input.setInputProcessor(new UserInput());
		
		// resources
		resources = new Resources();
		
		// batches
		batch = new SpriteBatch();
		guibatch = new SpriteBatch();
		
		// camera
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(Gdx.graphics.getWidth()*0.5f, Gdx.graphics.getHeight()*0.5f, 0);
		
		// scenes
		scenes = new SceneMng();
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// update scene
		scenes.update(Gdx.graphics.getDeltaTime(), camera);
		camera.update();
		batch.setProjectionMatrix(camera.projection);
		
		// draw scene
		batch.begin();
		scenes.draw(batch, camera);
		batch.end();
		
		// draw interface
		guibatch.begin();
		scenes.drawgui(guibatch);
		guibatch.end();
	}
	
	@Override
	public void dispose() {
		scenes.dispose();
		resources.dispose();
	}
	
	@Override
	public void resize(int w, int h) {

	}
	
	@Override
	public void pause() {

	}
	
	@Override
	public void resume() {

	}
}
