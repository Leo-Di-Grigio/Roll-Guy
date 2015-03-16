package game.script.ui.game;

import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.dialog.DialogBlock;
import game.cycle.scene.ui.widgets.Dialog;
import game.cycle.scene.ui.widgets.ListItem;
import game.script.Script;
import game.tools.Const;

public class ui_Talk implements Script {

	private Dialog dialog;

	public ui_Talk(Dialog dialog) {
		this.dialog = dialog;
	}

	@Override
	public void execute() {
		ListItem item = dialog.list.getSelected();
		int id = Integer.parseInt(item.get(0));
		
		if(id != Const.invalidId){
			DialogBlock block = new DialogBlock(Database.getDialog(id));
			dialog.addBlock(block);
		}
	}
}
