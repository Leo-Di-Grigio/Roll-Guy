package game.cycle.scene.ui.widgets.windows;

import java.util.ArrayList;
import java.util.HashMap;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.map.TerrainProto;
import game.cycle.scene.ui.UI;
import game.cycle.scene.ui.widgets.List;
import game.cycle.scene.ui.widgets.ListItem;
import game.cycle.scene.ui.widgets.Window;
import game.resources.Resources;
import game.resources.Tex;
import game.tools.Const;

public class WindowEditorTerrain extends Window {
	
	public static final String uiEditorTerrainList = "editor-terrain-list";
	public List editorListTerrain;
	
	public WindowEditorTerrain(String title, UI ui, int layer, SceneGame scene) {
		super(title, ui, Alignment.CENTER, 260, 24, 0, 0, layer);
		this.setTexNormal(Resources.getTex(Tex.uiListLine));
		this.closeButton(true);
		
		loadWidgets(scene);
		loadTerrainList();
	}

	private void loadWidgets(SceneGame scene) {
		editorListTerrain = new List(uiEditorTerrainList);
		editorListTerrain.setSize(260, 300);
		editorListTerrain.setVisible(16);
		editorListTerrain.setPosition(Alignment.UPRIGTH, 0, -24);
		this.add(editorListTerrain);
	}
	
	private void loadTerrainList() {
		editorListTerrain.clear();
		HashMap<Integer, TerrainProto> base = Database.getBaseTerrain();
		
		ArrayList<Boolean> mask = new ArrayList<Boolean>();
		mask.add(0, true);
		mask.add(1, false);
		
		for(Integer key: base.keySet()){
			ArrayList<String> data = new ArrayList<String>();
			
			data.add(0, ""+key);
			data.add(1, base.get(key).title);
			
			ListItem item = new ListItem(data, mask);
			item.setFormatter("");
			editorListTerrain.addElement(item);
		}
	}
	
	public int getSelectedListTerrain() {
		ListItem item = editorListTerrain.getSelected();
		if(item != null){
			return Integer.parseInt(item.get(0));
		}
		else{
			return Const.invalidId;
		}
	}
}
