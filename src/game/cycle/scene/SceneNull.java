package game.cycle.scene;

import game.resources.Fonts;
import game.resources.Resources;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SceneNull extends Scene {

	private BitmapFont font;
	
	public SceneNull() {
		font = Resources.getFont(Fonts.fontDefault);
	}
	
	@Override
	public void update(OrthographicCamera camera) {

	}

	@Override
	public void sceneClick(int button) {
		
	}

	@Override
	public void sceneKey(int key) {
		
	}
	
	@Override
	public void draw(SpriteBatch batch, OrthographicCamera camera) {
		
	}
	
	@Override
	public void drawGui(SpriteBatch batch) {
		drawTextLine(batch, font, "Null scene", 0);
	}
	
	@Override
	public void dispose() {
		
	}
}
