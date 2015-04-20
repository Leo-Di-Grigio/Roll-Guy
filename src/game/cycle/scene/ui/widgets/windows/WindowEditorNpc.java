package game.cycle.scene.ui.widgets.windows;

import java.util.ArrayList;
import java.util.HashMap;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.state.database.Database;
import game.cycle.scene.game.state.database.proto.CreatureProto;
import game.cycle.scene.ui.list.UIGame;
import game.cycle.scene.ui.widgets.Button;
import game.cycle.scene.ui.widgets.List;
import game.cycle.scene.ui.widgets.ListItem;
import game.cycle.scene.ui.widgets.Window;
import game.resources.Resources;
import game.resources.tex.Tex;
import game.script.ui.editor.ui_EditorMode;
import game.script.ui.editor.ui_NpcEditorProto;
import game.script.ui.editor.ui_UIGameEditor;
import game.tools.Const;

public class WindowEditorNpc extends Window {
	
	private UIGame uigame;
	
	public static final String uiAdd = "editor-npc-add";
	public static final String uiEdit = "editor-npc-edit";
	public static final String uiEditProto = "editor-npc-edit-proto";
	public static final String uiList = "editor-npc-list";
	
	public Button add;
	public Button edit;
	public Button proto;
	public List   list;
	
	public WindowEditorNpc(String title, UIGame ui, int layer, SceneGame scene) {
		super(title, ui, Alignment.CENTER, 326, 24, 0, 0, layer);
		this.setTexNormal(Resources.getTex(Tex.UI_LIST_LINE));
		this.uigame = ui;
		this.setText("NPC");
		
		loadWidgets(scene);
		loadNpcList();
	}

	private void loadWidgets(SceneGame scene) {
		this.closeButton(true);
		this.closeButton.setScript(new ui_UIGameEditor(uigame, UIGame.uiEditorNpc));
		this.lockButton(true);
		
		add = new Button(uiAdd, "Add");
		add.setSize(64, 32);
		add.setPosition(Alignment.UPRIGTH, -262, -24);
		add.setScript(new ui_EditorMode(uigame, UIGame.modeNpcAdd));
		this.add(add);
		
		edit = new Button(uiEdit, "Edit");
		edit.setSize(64, 32);
		edit.setPosition(Alignment.UPRIGTH, -262, -58);
		edit.setScript(new ui_EditorMode(uigame, UIGame.modeNpcEdit));
		this.add(edit);
		
		proto = new Button(uiEditProto, "Proto");
		proto.setSize(64, 32);
		proto.setPosition(Alignment.UPRIGTH, -262, -92);
		proto.setScript(new ui_NpcEditorProto(uigame));
		this.add(proto);
		
		list = new List(uiList);
		list.setSize(260, 300);
		list.setVisible(16);
		list.setPosition(Alignment.UPRIGTH, 0, -24);
		this.add(list);
	}
	
	public void loadNpcList() {
		list.clear();
		
		HashMap<Integer, CreatureProto> base = Database.getBaseCreature();
		
		for(Integer key: base.keySet()){
			if(key != 0){
				ArrayList<String> data = new ArrayList<String>();
				data.add(0, ""+key);
				data.add(1, base.get(key).name());
			
				ListItem item = new ListItem(data);
				list.addElement(item);
			}
		}
	}
	
	public int getSelectedListNpc() {
		ListItem item = list.getSelected();
		
		if(item != null){
			return Integer.parseInt(item.get(0));
		}
		else{
			return Const.INVALID_ID;
		}
	}
}
