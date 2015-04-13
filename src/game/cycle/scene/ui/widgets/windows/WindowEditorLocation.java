package game.cycle.scene.ui.widgets.windows;

import java.util.ArrayList;
import java.util.HashMap;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.database.proto.LocationProto;
import game.cycle.scene.ui.list.UIGame;
import game.cycle.scene.ui.widgets.Button;
import game.cycle.scene.ui.widgets.List;
import game.cycle.scene.ui.widgets.ListItem;
import game.cycle.scene.ui.widgets.Window;
import game.resources.Resources;
import game.resources.tex.Tex;
import game.script.ui.editor.ui_LocationAddMenuCancel;
import game.script.ui.editor.ui_LocationDel;
import game.script.ui.editor.ui_LocationLoad;
import game.script.ui.editor.ui_UIGameEditor;
import game.tools.Const;

public class WindowEditorLocation extends Window {
	
	private UIGame uigame;
	
	public static final String uiLoad ="editor-location-load";
	public static final String uiAdd ="editor-location-add";
	public static final String uiDelete ="editor-location-delete";
	public static final String uiList = "editor-location-list";
	
	public Button load;
	public Button add;
	public Button delete;
	public List   list;
	
	public WindowEditorLocation(String title, UIGame ui, int layer, SceneGame scene) {
		super(title, ui, Alignment.CENTER, 326, 24, 0, 0, layer);
		this.setTexNormal(Resources.getTex(Tex.UI_LIST_LINE));
		this.uigame = ui;
		this.setText("Locations");
		
		loadWidgets(scene);
		loadLocationList();
	}

	private void loadWidgets(SceneGame scene) {
		this.closeButton(true);
		this.closeButton.setScript(new ui_UIGameEditor(uigame, UIGame.uiEditorLocation));
		this.lockButton(true);
		
		load = new Button(uiLoad, "Load");
		load.setSize(64, 32);
		load.setPosition(Alignment.UPRIGTH, -262, -24);
		load.setScript(new ui_LocationLoad(uigame, scene));
		this.add(load);
		
		add = new Button(uiAdd, "Add");
		add.setSize(64, 32);
		add.setPosition(Alignment.UPRIGTH, -262, -58);
		add.setScript(new ui_LocationAddMenuCancel(uigame));
		this.add(add);
	
		delete = new Button(uiDelete, "Delete");
		delete.setSize(64, 32);
		delete.setPosition(Alignment.UPRIGTH, -262, -92);
		delete.setScript(new ui_LocationDel(uigame));
		this.add(delete);

		list = new List(uiList);
		list.setSize(260, 300);
		list.setVisible(16);
		list.setPosition(Alignment.UPRIGTH, -0, -24);
		this.add(list);
	}
	
	public void loadLocationList() {
		list.clear();
		HashMap<Integer, LocationProto> base = Database.getBaseLocations();
		
		ArrayList<Boolean> mask = new ArrayList<Boolean>();
		mask.add(0, true);
		mask.add(1, false);
		mask.add(2, false);
		
		for(Integer key: base.keySet()){
			ArrayList<String> data = new ArrayList<String>();
			data.add(0, "" + key);
			data.add(1, "ID: " + key);
			data.add(2, " \""+base.get(key).title()+"\"");
			
			ListItem item = new ListItem(data, mask);
			item.setFormatter("");
			list.addElement(item);
		}
	}
	
	public int getSelectedListLocation() {
		ListItem item = list.getSelected();
		
		if(item != null){
			return Integer.parseInt(item.get(0));
		}
		else{
			return Const.INVALID_ID;
		}
	}
}
