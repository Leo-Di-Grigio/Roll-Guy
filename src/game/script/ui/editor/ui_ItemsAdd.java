package game.script.ui.editor;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.state.database.Database;
import game.cycle.scene.game.state.location.creature.items.Item;
import game.cycle.scene.ui.list.UIGame;
import game.cycle.scene.ui.widgets.windows.WindowEditorItems;
import game.script.Script;
import game.tools.Const;

public class ui_ItemsAdd implements Script {

	private WindowEditorItems window;
	private SceneGame scene;
	private UIGame ui;

	public ui_ItemsAdd(WindowEditorItems window, UIGame ui, SceneGame scene) {
		this.window = window;
		this.ui = ui;
		this.scene = scene;
	}

	@Override
	public void execute() {
		int id = window.getSelectedItem();
		
		if(id != Const.INVALID_ID){
			if(scene.getState().getPlayer().inventory.addItem(new Item(Database.getItem(id)))){
				ui.invenotry.update();
			}
		}
	}
}
