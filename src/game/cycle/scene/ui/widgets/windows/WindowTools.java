package game.cycle.scene.ui.widgets.windows;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.ui.list.UIGame;
import game.cycle.scene.ui.widgets.Button;
import game.cycle.scene.ui.widgets.Window;
import game.resources.Resources;
import game.resources.Tex;
import game.script.ui.app.ui_ExitGame;
import game.script.ui.app.ui_FreeCameraMode;
import game.script.ui.app.ui_ShowEditor;

public class WindowTools extends Window {
	
	private UIGame uigame;
	
	// common UI
	public static final String uiMainMenu = "main-menu";
	public static final String uiFreeCamera = "free-camera";
	public static final String uiEditor = "editor";
	
	public WindowTools(String title, UIGame ui, int layer, SceneGame scene) {
		super(title, ui, Alignment.UPRIGTH, 128, 112, 0, 0, layer);
		this.uigame = ui;
		this.setTexNormal(Resources.getTex(Tex.uiBackgroundNormal));
		loadWidgets(scene);
		setVisible(true);
	}

	private void loadWidgets(SceneGame scene) {
		Button button = new Button(uiMainMenu, "Main menu");
		button.setVisible(true);
		button.setSize(128, 32);
		button.setPosition(Alignment.UPLEFT, 0, -12);
		button.setScript(new ui_ExitGame());
		this.add(button);
		
		button = new Button(uiFreeCamera, "Free camera");
		button.setVisible(true);
		button.setSize(128, 32);
		button.setPosition(Alignment.UPLEFT, 0, -46);
		button.setScript(new ui_FreeCameraMode(scene, button));
		this.add(button);
		
		button = new Button(uiEditor, "Editor");
		button.setVisible(true);
		button.setSize(128, 32);
		button.setPosition(Alignment.UPLEFT, 0, -80);
		button.setScript(new ui_ShowEditor(uigame));
		this.add(button);
	}
	
	@Override
	public void draw(SpriteBatch sprites) {
		super.draw(sprites);
	}
}
