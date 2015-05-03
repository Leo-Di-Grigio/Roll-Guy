package lua.lib;

import tools.Log;
import ui.UI;

public class LuaLibConsole {

	public void print(String text){
		Log.lua(text);
	}
	
	public void splash(String text){
		UI.setInformation(text);
	}
}
