package game.lua.lib;

import game.script.game.event.Logic;

public class LuaLibUI {

	public void updateDialogTopics(){
		Logic.getUI().updateDialogTopics();
	}
	
	public void dialogEnd(){
		Logic.getUI().dialog.setVisible(false);
		Logic.getUI().dialog.setCreature(null);
		Logic.dialogEnd();
	}
}
