package game.cycle.scene;

import ui.UI;
import game.cycle.scene.ui.list.UINull;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

abstract public class Scene implements Disposable {

	protected boolean pause;
	protected UI ui;
	
	public Scene() {
		ui = new UINull();
	}

	public void pause(boolean pause) {
		this.pause = pause;
	}
	
	protected void drawTextLine(SpriteBatch sprites, BitmapFont font, String text, int line){
		font.draw(sprites, text, 5, (font.getBounds(text).height + 3) * (line + 1));
	}
	
	abstract public void update(OrthographicCamera camera);
	abstract public void draw(SpriteBatch batch, OrthographicCamera camera);
	abstract public void drawGui(SpriteBatch batch);
	abstract public void sceneClick(int button);
	abstract public void sceneKey(int key);
}