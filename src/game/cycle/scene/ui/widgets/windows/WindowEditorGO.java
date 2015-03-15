package game.cycle.scene.ui.widgets.windows;

import java.util.ArrayList;
import java.util.HashMap;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.go.GOProto;
import game.cycle.scene.ui.UI;
import game.cycle.scene.ui.widgets.Button;
import game.cycle.scene.ui.widgets.List;
import game.cycle.scene.ui.widgets.ListItem;
import game.cycle.scene.ui.widgets.Window;
import game.resources.Resources;
import game.resources.Tex;
import game.script.ui.app.ui_GameClickMode;
import game.tools.Const;

public class WindowEditorGO extends Window {
	
	public static final String uiEditorGOAdd = "editor-go-add";
	public static final String uiEditorGOEdit = "editor-go-edit";
	public static final String uiEditorGOList = "editor-go-list";
	
	public Button editorGOAdd;
	public Button editorGOEdit;
	public List   editorListGO;
	
	public WindowEditorGO(String title, UI ui, int layer, SceneGame scene) {
		super(title, ui, Alignment.CENTER, 326, 24, 0, 0, layer);
		this.setTexNormal(Resources.getTex(Tex.uiListLine));
		
		this.closeButton(true);
		loadWidgets(scene);
		loadGOList();
	}

	private void loadWidgets(SceneGame scene) {
		editorGOAdd = new Button(uiEditorGOAdd, "Add");
		editorGOAdd.setSize(64, 32);
		editorGOAdd.setPosition(Alignment.UPRIGTH, -262, -24);
		editorGOAdd.setScript(new ui_GameClickMode(scene, SceneGame.clickEditorGOAdd));
		this.add(editorGOAdd);
		
		editorGOEdit = new Button(uiEditorGOEdit, "Edit");
		editorGOEdit.setSize(64, 32);
		editorGOEdit.setPosition(Alignment.UPRIGTH, -262, -58);
		editorGOEdit.setScript(new ui_GameClickMode(scene, SceneGame.clickEditorGOEdit));
		this.add(editorGOEdit);
		
		editorListGO = new List(uiEditorGOList);
		editorListGO.setSize(260, 300);
		editorListGO.setVisible(16);
		editorListGO.setPosition(Alignment.UPRIGTH, 0, -24);
		this.add(editorListGO);
	}
	
	private void loadGOList() {
		editorListGO.clear();
		HashMap<Integer, GOProto> base = Database.getBaseGO();
		
		for(Integer key: base.keySet()){
			ArrayList<String> data = new ArrayList<String>();
			data.add(0, ""+key);
			data.add(1, base.get(key).title);
			
			ListItem item = new ListItem(data);
			editorListGO.addElement(item);
		}
	}
	
	public int getSelectedListGO(){
		ListItem item = editorListGO.getSelected();
		
		if(item != null){
			return Integer.parseInt(item.get(0));
		}
		else{
			return Const.invalidId;
		}
	}
}
