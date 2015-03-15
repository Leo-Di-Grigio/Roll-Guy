package game.cycle.scene.ui.widgets.windows;

import java.util.ArrayList;
import java.util.HashMap;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.world.creature.CreatureProto;
import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.ui.list.UIGame;
import game.cycle.scene.ui.widgets.Button;
import game.cycle.scene.ui.widgets.List;
import game.cycle.scene.ui.widgets.ListItem;
import game.cycle.scene.ui.widgets.Window;
import game.resources.Resources;
import game.resources.Tex;
import game.script.ui.app.ui_GameClickMode;
import game.script.ui.app.ui_NpcEditorProto;
import game.tools.Const;

public class WindowEditorNpc extends Window {
	
	private UIGame uigame;
	
	public static final String uiEditorNpcEdit = "editor-npc-edit";
	public static final String uiEditorNpcEditProto = "editor-npc-edit-proto";
	public static final String uiEditorNpcList = "editor-npc-list";
	
	public Button editorNpcEdit;
	public Button editorNpcEditProto;
	public List   editorListNpc;
	
	public WindowEditorNpc(String title, UIGame ui, int layer, SceneGame scene) {
		super(title, ui, Alignment.CENTER, 326, 24, 0, 0, layer);
		this.setTexNormal(Resources.getTex(Tex.uiListLine));
		this.uigame = ui;
		this.closeButton(true);
		loadWidgets(scene);
		loadNpcList();
	}

	private void loadWidgets(SceneGame scene) {
		editorNpcEdit = new Button(uiEditorNpcEdit, "Edit");
		editorNpcEdit.setSize(64, 32);
		editorNpcEdit.setPosition(Alignment.UPRIGTH, -262, -24);
		editorNpcEdit.setScript(new ui_GameClickMode(scene, SceneGame.clickEditorNpcEdit));
		this.add(editorNpcEdit);
		
		editorNpcEditProto = new Button(uiEditorNpcEditProto, "Proto");
		editorNpcEditProto.setSize(64, 32);
		editorNpcEditProto.setPosition(Alignment.UPRIGTH, -262, -58);
		editorNpcEditProto.setScript(new ui_NpcEditorProto(uigame));
		this.add(editorNpcEditProto);
		
		editorListNpc = new List(uiEditorNpcList);
		editorListNpc.setSize(260, 300);
		editorListNpc.setVisible(16);
		editorListNpc.setPosition(Alignment.UPRIGTH, 0, -24);
		this.add(editorListNpc);
	}
	
	public void loadNpcList() {
		editorListNpc.clear();
		
		HashMap<Integer, CreatureProto> base = Database.getBaseCreature();
		
		for(Integer key: base.keySet()){
			if(key != 0){
				ArrayList<String> data = new ArrayList<String>();
				data.add(0, ""+key);
				data.add(1, base.get(key).name);
			
				ListItem item = new ListItem(data);
				editorListNpc.addElement(item);
			}
		}
	}
	
	public int getSelectedListNpc() {
		ListItem item = editorListNpc.getSelected();
		
		if(item != null){
			return Integer.parseInt(item.get(0));
		}
		else{
			return Const.invalidId;
		}
	}
}
