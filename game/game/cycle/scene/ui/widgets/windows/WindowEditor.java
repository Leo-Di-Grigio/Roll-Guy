package game.cycle.scene.ui.widgets.windows;

import resources.Resources;
import resources.tex.Tex;
import ui.Alignment;
import ui.Window;
import ui.widgets.used.Button;
import game.SceneGame;
import game.cycle.scene.ui.list.UIGame;
import game.script.ui.editor.ui_LocationSave;
import game.script.ui.editor.ui_ShowEditor;
import game.script.ui.editor.ui_UIGameEditor;

public class WindowEditor extends Window {
	
	private UIGame uigame;
	
	public static final String uiEditorTerrain = "editor-terrain";
	public static final String uiEditorNpc = "editor-npc";
	public static final String uiEditorGO = "editor-go";
	public static final String uiEditorLocation ="editor-location";
	public static final String uiEditorItems ="editor-items";
	public static final String uiEditorSave = "editor-save";
	
	public Button terrain;
	public Button npc;
	public Button go;
	public Button location;
	public Button items;
	public Button save;
	
	public WindowEditor(String title, UIGame ui, int layer, SceneGame scene) {
		super(title, ui, Alignment.CENTERRIGHT, 128, 24, 0, 60, layer);
		this.uigame = ui;
		this.setTexNormal(Resources.getTex(Tex.UI_LIST_LINE));
		this.setText("Editor");
		
		loadWidgets(scene);
	}

	private void loadWidgets(SceneGame scene) {
		this.closeButton(true);
		this.closeButton.setScript(new ui_ShowEditor(uigame));
		this.lockButton(true);
		
		terrain = new Button(uiEditorTerrain, "Terrain");
		terrain.setSize(128, 32);
		terrain.setPosition(Alignment.UPRIGTH, 0, -24);
		terrain.setScript(new ui_UIGameEditor(uigame, UIGame.EDITOR_TERRAIN));
		this.add(terrain);
		
		npc = new Button(uiEditorNpc, "NPC");
		npc.setSize(128, 32);
		npc.setPosition(Alignment.UPRIGTH, 0, -58);
		npc.setScript(new ui_UIGameEditor(uigame, UIGame.EDITOR_NPC));
		this.add(npc); 
		
		go = new Button(uiEditorGO, "Game Object");
		go.setSize(128, 32);
		go.setPosition(Alignment.UPRIGTH, 0, -92);
		go.setScript(new ui_UIGameEditor(uigame, UIGame.EDITOR_GO));
		this.add(go);
		
		location = new Button(uiEditorLocation, "Locations");
		location.setSize(128, 32);
		location.setPosition(Alignment.UPRIGTH, 0, -126);
		location.setScript(new ui_UIGameEditor(uigame, UIGame.EDITOR_LOCATION));
		this.add(location);
		
		items = new Button(uiEditorItems, "Items");
		items.setSize(128, 32);
		items.setPosition(Alignment.UPRIGTH, 0, -160);
		items.setScript(new ui_UIGameEditor(uigame, UIGame.EDITOR_ITEM));
		this.add(items);
		
		save = new Button(uiEditorSave, "Save");
		save.setSize(128, 32);
		save.setPosition(Alignment.UPRIGTH, 0, -194);
		save.setScript(new ui_LocationSave(scene));
		this.add(save);
	}
}
