package game.lua.lib;

import org.luaj.vm2.LuaValue;

import game.script.game.event.GameEvents;

public class LuaMethodsLocation {

	public LuaValue getTitle(){
		if(GameEvents.getLocation() != null){
			System.out.println(GameEvents.getLocation().proto.title());
			return LuaValue.valueOf(GameEvents.getLocation().proto.title());
		}
		else{
			return LuaValue.NIL;
		}
	}
}
