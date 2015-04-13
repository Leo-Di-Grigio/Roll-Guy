package game.script.game.go;


import game.cycle.scene.game.world.event.LocationEvent;
import game.cycle.scene.game.world.location.LocationObject;
import game.cycle.scene.game.world.location.go.GO;
import game.lua.LuaEngine;
import game.resources.tex.Tex;
import game.script.ScriptGame;

public class go_DoorUse implements ScriptGame {

	private GO go;
	
	public go_DoorUse(GO go) {
		this.go = go; 
		
		// closed
		this.go.setPassable(false);
		this.go.setTexture(Tex.GO_DOOR_CLOSE);
		
		// test
		LuaEngine.load("door");
	}
	
	@Override
	public boolean execute(LocationObject user) {
		LuaEngine.execute("door", new LocationEvent(LocationEvent.EVENT_SCRIPT, LocationEvent.CONTEXT_GO_USE, user, go));
		return false;
	}
}