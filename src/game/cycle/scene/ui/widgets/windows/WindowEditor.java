package game.cycle.scene.ui.widgets.windows;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.ui.UI;
import game.cycle.scene.ui.widgets.Button;
import game.cycle.scene.ui.widgets.Window;
import game.resources.Resources;
import game.resources.Tex;
import game.script.ui.app.ui_GameClickMode;
import game.script.ui.app.ui_LocationSave;

public class WindowEditor extends Window {
	
	public static final String uiEditorTerrain = "editor-terrain";
	public static final String uiEditorNpc = "editor-npc";
	public static final String uiEditorGO = "editor-go";
	public static final String uiEditorLocation ="editor-location";
	public static final String uiEditorSave = "editor-save";
	
	public Button editorTerrain;
	public Button editorNpc;
	public Button editorGO;
	public Button editorLocation;
	public Button editorSave;
	
	public WindowEditor(String title, UI ui, int layer, SceneGame scene) {
		super(title, ui, Alignment.CENTERRIGHT, 128, 178, 0, 0, layer);
		this.setTexNormal(Resources.getTex(Tex.uiBackgroundNormal));
		loadWidgets(scene);
		setVisible(true);
	}

	private void loadWidgets(SceneGame scene) {
		editorTerrain = new Button(uiEditorTerrain, "Terrain");
		editorTerrain.setSize(128, 32);
		editorTerrain.setPosition(Alignment.UPRIGTH, 0, -12);
		editorTerrain.setScript(new ui_GameClickMode(scene, SceneGame.clickTerrain));
		this.add(editorTerrain);
		
		editorNpc = new Button(uiEditorNpc, "NPC");
		editorNpc.setSize(128, 32);
		editorNpc.setPosition(Alignment.UPRIGTH, 0, -46);
		editorNpc.setScript(new ui_GameClickMode(scene, SceneGame.clickEditorNpc));
		this.add(editorNpc); 
		
		editorGO = new Button(uiEditorGO, "Game Object");
		editorGO.setSize(128, 32);
		editorGO.setPosition(Alignment.UPRIGTH, 0, -80);
		editorGO.setScript(new ui_GameClickMode(scene, SceneGame.clickEditorGO));
		this.add(editorGO);
		
		editorLocation = new Button(uiEditorLocation, "Locations");
		editorLocation.setSize(128, 32);
		editorLocation.setPosition(Alignment.UPRIGTH, 0, -114);
		editorLocation.setScript(new ui_GameClickMode(scene, SceneGame.clickEditorLocation));
		this.add(editorLocation);
		
		editorSave = new Button(uiEditorSave, "Save");
		editorSave.setSize(128, 32);
		editorSave.setPosition(Alignment.UPRIGTH, 0, -148);
		editorSave.setScript(new ui_LocationSave(scene));
		this.add(editorSave);
	}
}
