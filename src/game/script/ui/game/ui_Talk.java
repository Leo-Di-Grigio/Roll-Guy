package game.script.ui.game;

import game.cycle.scene.game.state.database.Database;
import game.cycle.scene.game.state.dialog.DialogBlock;
import game.cycle.scene.game.state.dialog.DialogWrapper;
import game.cycle.scene.ui.widgets.ListItem;
import game.cycle.scene.ui.widgets.windows.WindowDialog;
import game.lua.LuaEngine;
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
		
			if(id != Const.INVALID_ID){
				DialogWrapper proto = new DialogWrapper(Database.getDialog(id));
				
				if(proto.script() != null){
					if(!LuaEngine.isLoaded(proto.script())){
						LuaEngine.load(proto.script());
					}
					
					LuaEngine.execute(proto, dialog.getNPC());
					LuaEngine.remove(proto.script());
				}
				
				DialogBlock block = new DialogBlock(proto, false);
				dialog.addBlock(block);
			}
		}
	}
}
