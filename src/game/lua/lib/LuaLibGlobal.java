package game.lua.lib;

import org.luaj.vm2.LuaValue;

import game.cycle.scene.game.world.GlobalParams;

public class LuaLibGlobal {
	
	public void setInt(String param, int value){
		GlobalParams.setInt(param, value);
	}
	
	public void setFlag(String param, boolean value){
		GlobalParams.setFlag(param, value);
	}
	
	public void setText(String param, String value){
		GlobalParams.setText(param, value);
	}
	
	public LuaValue getInt(String param){
		return GlobalParams.getInt(param);
	}
	
	public LuaValue getFlag(String param){
		return GlobalParams.getFlag(param);
	}
	
	public LuaValue getText(String param){
		return GlobalParams.getText(param);
	}
	
	public LuaValue removeInt(String param){
		return GlobalParams.removeInt(param);
	}
	
	public LuaValue removeFlag(String param){
		return GlobalParams.removeFlag(param);
	}

	public LuaValue removeText(String param){
		return GlobalParams.removeText(param);
	}
}