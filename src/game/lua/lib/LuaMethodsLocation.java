package game.lua.lib;

import org.luaj.vm2.LuaValue;

import game.cycle.scene.game.world.location.LocationObject;
import game.cycle.scene.game.world.location.go.GO;
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
	
	public void teleport(LocationObject user, GO go){
		GameEvents.teleport(user, go);
	}
}
