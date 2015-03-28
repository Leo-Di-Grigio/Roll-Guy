package game.cycle.scene;

import game.cycle.scene.ui.UI;
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
	
	public boolean isUiSelected() {
		return ui.selected;
	}
	
	public void uiupdate() {
		ui.update();
	}

	public void click(int button) {
		ui.click(button);
	}
	
	public void scroll(int amount) {
		ui.scroll(amount);
	}

	public void inputChar(char key) {
		ui.inputChar(key);
	}
	
	public void uiopen() {
		ui.onload();
	}
	
	public void uiclose() {
		ui.onclose();
	}
	
	public void pause() {
		pause = !pause;
	}
	
	protected void drawTextLine(SpriteBatch sprites, BitmapFont font, String text, int line){
		font.draw(sprites, text, 5, (font.getBounds(text).height + 3) * (line + 1));
	}
	
	abstract public void update(OrthographicCamera camera);
	abstract public void draw(SpriteBatch batch, OrthographicCamera camera);
	abstract public void drawGui(SpriteBatch batch);
	abstract public void sceneClick(int button);
	abstract public void sceneKey(int key);
	
	@Override
	abstract public void dispose();
}