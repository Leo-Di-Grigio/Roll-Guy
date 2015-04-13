package game.cycle.scene.ui.widgets.windows;

import java.util.ArrayList;
import java.util.HashMap;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.database.proto.ItemProto;
import game.cycle.scene.ui.list.UIGame;
import game.cycle.scene.ui.widgets.Button;
import game.cycle.scene.ui.widgets.List;
import game.cycle.scene.ui.widgets.ListItem;
import game.cycle.scene.ui.widgets.Window;
import game.resources.Resources;
import game.resources.tex.Tex;
import game.script.ui.editor.ui_ItemsAdd;
import game.script.ui.editor.ui_UIGameEditor;
import game.tools.Const;

public class WindowEditorItems extends Window {
	
	public static final String uiAdd = "editor-items-add";
	public static final String uiList = "editor-items-list";
	
	public Button add;
	public List list;
	
	public WindowEditorItems(String title, UIGame ui, int layer, SceneGame scene) {
		super(title, ui, Alignment.CENTER, 326, 24, 0, 0, layer);
		this.setTexNormal(Resources.getTex(Tex.UI_LIST_LINE));
		this.setText("Items");
		
		loadWidgets(ui, scene);
		loadItemsList();
	}

	private void loadWidgets(UIGame ui, SceneGame scene) {
		this.closeButton(true);
		this.closeButton.setScript(new ui_UIGameEditor(ui, UIGame.uiEditorItems));
		this.lockButton(true);
		
		add = new Button(uiAdd, "Add");
		add.setSize(64, 32);
		add.setPosition(Alignment.UPRIGTH, -262, -24);
		add.setScript(new ui_ItemsAdd(this, ui, scene));
		this.add(add);
		
		list = new List(uiList);
		list.setSize(260, 300);
		list.setVisible(16);
		list.setPosition(Alignment.UPRIGTH, 0, -24);
		this.add(list);
	}

	private void loadItemsList() {
		list.clear();
		HashMap<Integer, ItemProto> base = Database.getBaseItems();
		
		for(Integer key: base.keySet()){
			ArrayList<String> data = new ArrayList<String>();
			data.add(0, ""+key);
			data.add(1, base.get(key).title());
			
			ListItem item = new ListItem(data);
			list.addElement(item);
		}
	}

	public int getSelectedItem() {
		ListItem item = list.getSelected();
		
		if(item != null){
			return Integer.parseInt(item.get(0));
		}
		else{
			return Const.INVALID_ID;
		}
	}
}
