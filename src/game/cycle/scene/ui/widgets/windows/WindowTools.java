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
import game.script.ui.app.ui_LOSMode;
import game.script.ui.editor.ui_ShowEditor;

public class WindowTools extends Window {
	
	private UIGame uigame;
	
	// common UI
	public static final String uiMainMenu = "main-menu";
	public static final String uiFreeCamera = "free-camera";
	public static final String uiLOS = "los";
	public static final String uiEditor = "editor";
	
	public Button mainMenu;
	public Button freeCamera;
	public Button los;
	public Button editor;
	
	public WindowTools(String title, UIGame ui, int layer, SceneGame scene) {
		super(title, ui, Alignment.UPRIGTH, 128, 24, 0, 0, layer);
		this.uigame = ui;
		this.setTexNormal(Resources.getTex(Tex.uiListLine));
		
		loadWidgets(scene);
		setVisible(true);
	}

	private void loadWidgets(SceneGame scene) {
		this.lockButton(true);
		
		mainMenu = new Button(uiMainMenu, "Main menu");
		mainMenu.setVisible(true);
		mainMenu.setSize(128, 32);
		mainMenu.setPosition(Alignment.UPLEFT, 0, -24);
		mainMenu.setScript(new ui_ExitGame());
		this.add(mainMenu);
		
		freeCamera = new Button(uiFreeCamera, "Free camera");
		freeCamera.setVisible(true);
		freeCamera.setSize(128, 32);
		freeCamera.setPosition(Alignment.UPLEFT, 0, -58);
		freeCamera.setScript(new ui_FreeCameraMode(scene, freeCamera));
		this.add(freeCamera);
		
		los = new Button(uiLOS, "LOS");
		los.setVisible(true);
		los.setSize(128, 32);
		los.setPosition(Alignment.UPLEFT, 0, -92);
		los.setScript(new ui_LOSMode(scene, los));
		this.add(los);
		
		editor = new Button(uiEditor, "Editor");
		editor.setVisible(true);
		editor.setSize(128, 32);
		editor.setPosition(Alignment.UPLEFT, 0, -126);
		editor.setScript(new ui_ShowEditor(uigame));
		this.add(editor);
	}
	
	@Override
	public void draw(SpriteBatch sprites) {
		super.draw(sprites);
	}
}
