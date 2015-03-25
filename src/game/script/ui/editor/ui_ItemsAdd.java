package game.script.ui.editor;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.world.creature.items.Item;
import game.cycle.scene.game.world.database.Database;
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
		
		if(id != Const.invalidId){
			if(scene.getWorld().getPlayer().inventory.addItem(new Item(Database.getItem(id)))){
				ui.invenotry.update();
			}
		}
	}
}
