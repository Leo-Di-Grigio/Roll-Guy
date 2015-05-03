package game.cycle.scene.ui.widgets.windows;

import java.util.ArrayList;
import java.util.HashMap;

import resources.Resources;
import resources.tex.Tex;
import tools.Const;
import ui.Alignment;
import ui.Window;
import ui.widgets.ListItem;
import ui.widgets.used.Button;
import ui.widgets.used.List;
import game.SceneGame;
import game.cycle.scene.ui.list.UIGame;
import game.script.ui.editor.ui_EditorMode;
import game.script.ui.editor.ui_UIGameEditor;
import game.state.database.Database;
import game.state.database.proto.NodeProto;

public class WindowEditorTerrain extends Window {
	
	private UIGame uigame;
	
	public static final String uiBrush1 = "editor-terrain-brush1";
	public static final String uiBrush2 = "editor-terrain-brush2";
	public static final String uiBrush3 = "editor-terrain-brush3";
	public static final String uiFill = "editor-terrain-fill";
	public static final String uiList = "editor-terrain-list";
	
	public Button brush1;
	public Button brush2;
	public Button brush3;
	public Button fill;
	public List list;
	
	public WindowEditorTerrain(String title, UIGame ui, int layer, SceneGame scene) {
		super(title, ui, Alignment.CENTER, 326, 24, 0, 0, layer);
		this.uigame = ui;
		this.setTexNormal(Resources.getTex(Tex.UI_LIST_LINE));
		this.setText("Terrain");
		
		loadWidgets(scene);
		loadTerrainList();
	}

	private void loadWidgets(SceneGame scene) {		
		this.closeButton(true);
		this.closeButton.setScript(new ui_UIGameEditor(uigame, UIGame.EDITOR_TERRAIN));
		this.lockButton(true);
		
		brush1 = new Button(uiBrush1, "1x1");
		brush1.setSize(64, 32);
		brush1.setPosition(Alignment.UPRIGTH, -262, -24);
		brush1.setScript(new ui_EditorMode(uigame, UIGame.MODE_TERRAIN_BRUSH_1));
		this.add(brush1);
		
		brush2 = new Button(uiBrush2, "2x2");
		brush2.setSize(64, 32);
		brush2.setPosition(Alignment.UPRIGTH, -262, -58);
		brush2.setScript(new ui_EditorMode(uigame, UIGame.MODE_TERRAIN_BRUSH_2));
		this.add(brush2);
		
		brush3 = new Button(uiBrush3, "3x3");
		brush3 .setSize(64, 32);
		brush3 .setPosition(Alignment.UPRIGTH, -262, -92);
		brush3.setScript(new ui_EditorMode(uigame, UIGame.MODE_TERRAIN_BRUSH_3));
		this.add(brush3);
		
		fill = new Button(uiFill, "Fill");
		fill .setSize(64, 32);
		fill .setPosition(Alignment.UPRIGTH, -262, -126);
		fill.setScript(new ui_EditorMode(uigame, UIGame.MODE_TERRAIN_FILL));
		this.add(fill);
		
		list = new List(uiList);
		list.setSize(260, 300);
		list.setVisible(16);
		list.setPosition(Alignment.UPRIGTH, 0, -24);
		this.add(list);
	}
	
	private void loadTerrainList() {
		list.clear();
		HashMap<Integer, NodeProto> base = Database.getBaseTerrain();
		
		ArrayList<Boolean> mask = new ArrayList<Boolean>();
		mask.add(0, true);
		mask.add(1, false);
		
		for(Integer key: base.keySet()){
			ArrayList<String> data = new ArrayList<String>();
			
			data.add(0, ""+key);
			data.add(1, base.get(key).title());
			
			ListItem item = new ListItem(data, mask);
			item.setFormatter("");
			list.addElement(item);
		}
	}
	
	public int getSelectedListTerrain() {
		ListItem item = list.getSelected();
		if(item != null){
			return Integer.parseInt(item.get(0));
		}
		else{
			return Const.INVALID_ID;
		}
	}
}
