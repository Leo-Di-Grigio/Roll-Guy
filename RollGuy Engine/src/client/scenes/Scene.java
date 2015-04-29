package client.scenes;

import client.scenes.ui.UI;
import client.scenes.ui.UINull;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public abstract class Scene implements Disposable {
	
	protected UI ui;
	protected String title;

	public Scene(String title) {
		this.ui = new UINull();
		this.title = title;
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
	
	protected void drawTextLine(SpriteBatch sprites, BitmapFont font, String text, int line){
		font.draw(sprites, text, 5, (font.getBounds(text).height + 3) * (line + 1));
	}
	
	abstract public void update(float delta, OrthographicCamera camera);
	abstract public void draw(SpriteBatch batch, OrthographicCamera camera);
	abstract public void drawgui(SpriteBatch batch);
	abstract public void sceneClick(int button);
	abstract public void sceneKey(int key);
}
