package game.lua.lib;

import game.script.game.event.Logic;

public class LuaLibGlobal {
	
	public void setInt(String param, int value){
		Logic.getGlobals().setInt(param, value);
	}
	
	public void setFlag(String param, boolean value){
		Logic.getGlobals().setFlag(param, value);
	}
	
	public void setText(String param, String value){
		Logic.getGlobals().setText(param, value);
	}
	
	public int getInt(String param){
		return Logic.getGlobals().getInt(param);
	}
	
	public boolean getFlag(String param){
		return Logic.getGlobals().getFlag(param);
	}
	
	public String getText(String param){
		return Logic.getGlobals().getText(param);
	}
	
	public int removeInt(String param){
		return Logic.getGlobals().removeInt(param);
	}
	
	public boolean removeFlag(String param){
		return Logic.getGlobals().removeFlag(param);
	}

	public String removeText(String param){
		return Logic.getGlobals().removeText(param);
	}
}