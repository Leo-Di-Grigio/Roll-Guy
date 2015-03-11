package game.cycle.scene.menu;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.Version;
import game.cycle.scene.Scene;
import game.cycle.scene.ui.list.UIMenu;
import game.resources.Fonts;
import game.resources.Resources;

public class SceneMenu extends Scene {

	private UIMenu uimenu;
	private BitmapFont font;
	
	public SceneMenu() {
		font = Resources.getFont(Fonts.fontDefault);
		this.ui = uimenu = new UIMenu();
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
	public void draw(SpriteBatch batch) {
		
	}

	@Override
	public void drawGui(SpriteBatch batch) {
		String selected = "ui selected: ";
		if(uimenu.selected ){
			selected = "UI: " + uimenu.widgetSelected.title;
		}
		
		drawTextLine(batch, font, "Tile RollBoy v" + Version.version + "." + Version.subversion, 0);
		drawTextLine(batch, font, "Menu scene", 1);
		drawTextLine(batch, font, selected, 2);
	}

	@Override
	public void dispose() {
		
	}
}
