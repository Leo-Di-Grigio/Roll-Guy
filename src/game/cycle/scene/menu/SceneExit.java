package game.cycle.scene.menu;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.Version;
import game.cycle.scene.Scene;
import game.cycle.scene.ui.list.UIExit;
import game.resources.Fonts;
import game.resources.Resources;

public class SceneExit extends Scene {
	
	private BitmapFont font;
	
	public SceneExit() {
		this.ui = new UIExit();
		font = Resources.getFont(Fonts.fontDefault);
	}

	@Override
	public void update(OrthographicCamera camera) {
		
	}

	@Override
	public void draw(SpriteBatch batch, OrthographicCamera camera) {

	}

	@Override
	public void drawGui(SpriteBatch batch) {
		String selected = "ui selected: ";
		if(ui.selected ){
			selected = "UI: " + ui.widgetSelected.title;
		}
		
		drawTextLine(batch, font, "Tile RollBoy v" + Version.version + "." + Version.subversion, 0);
		drawTextLine(batch, font, "Exit scene", 1);
		drawTextLine(batch, font, selected, 2);
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
