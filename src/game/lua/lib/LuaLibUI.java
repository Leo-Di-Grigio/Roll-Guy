package game.lua.lib;

import game.script.game.event.GameEvents;

public class LuaLibUI {

	public void updateDialogTopics(){
		GameEvents.getUI().updateDialogTopics();
	}
	
	public void dialogEnd(){
		GameEvents.getUI().dialog.setVisible(false);
		GameEvents.getUI().dialog.setCreature(null);
		GameEvents.dialogEnd();
	}
}
