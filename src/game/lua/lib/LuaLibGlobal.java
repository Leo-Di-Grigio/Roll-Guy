package game.lua.lib;

import org.luaj.vm2.LuaValue;
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
	
	public LuaValue getInt(String param){
		return Logic.getGlobals().getInt(param);
	}
	
	public LuaValue getFlag(String param){
		return Logic.getGlobals().getFlag(param);
	}
	
	public LuaValue getText(String param){
		return Logic.getGlobals().getText(param);
	}
	
	public LuaValue removeInt(String param){
		return Logic.getGlobals().removeInt(param);
	}
	
	public LuaValue removeFlag(String param){
		return Logic.getGlobals().removeFlag(param);
	}

	public LuaValue removeText(String param){
		return Logic.getGlobals().removeText(param);
	}
}