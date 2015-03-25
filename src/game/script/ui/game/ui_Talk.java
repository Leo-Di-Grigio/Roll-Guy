package game.script.ui.game;

import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.dialog.DialogBlock;
import game.cycle.scene.ui.widgets.ListItem;
import game.cycle.scene.ui.widgets.windows.WindowDialog;
import game.script.Script;
import game.tools.Const;

public class ui_Talk implements Script {

	private WindowDialog dialog;

	public ui_Talk(WindowDialog dialog) {
		this.dialog = dialog;
	}

	@Override
	public void execute() {
		ListItem item = dialog.list.getSelected();
		
		if(item != null){
			int id = Integer.parseInt(item.get(0));
		
			if(id != Const.invalidId){
				DialogBlock block = new DialogBlock(Database.getDialog(id), false);
				dialog.addBlock(block);
			}
		}
	}
}
