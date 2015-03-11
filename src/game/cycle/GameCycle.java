package game.cycle;

import game.cycle.input.UserInput;
import game.cycle.scene.SceneMng;
import game.resources.Resources;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameCycle implements ApplicationListener {

	private boolean pause;
	
	private static OrthographicCamera camera;
	private SpriteBatch batch;
	private SpriteBatch guibatch;
	
	private Resources resources;
	private SceneMng scenes;
	
	@Override
	public void create() {
		resources = new Resources();
		scenes = new SceneMng();
		batch = new SpriteBatch();
		guibatch = new SpriteBatch();
		
		// render config
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		// User Input
		Gdx.input.setInputProcessor(new UserInput());
		
		// camera
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(width, height);
		camera.position.set(width*0.5f, height*0.5f, 0);
		
		SceneMng.switchScene(SceneMng.sceneKeyMenu);
	}

	@Override
	public void dispose() {
		batch.dispose();
		guibatch.dispose();
		scenes.dispose();
		resources.dispose();
	}

	@Override
	public void render() {
		if(!pause){
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			// update
			scenes.update(camera);
			camera.update();
			batch.setProjectionMatrix(camera.combined);
			
			// draw scene
			batch.begin();
			scenes.draw(batch);
			batch.end();
			
			// draw gui
			guibatch.begin();
			scenes.drawGui(guibatch);
			guibatch.end();
		}
	}

	@Override
	public void resize(int w, int h) {

	}

	@Override
	public void pause() {
		pause = true;
	}
	
	@Override
	public void resume() {
		pause = false;
	}
}
