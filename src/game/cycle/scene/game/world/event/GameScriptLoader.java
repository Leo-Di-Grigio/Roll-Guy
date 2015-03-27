package game.cycle.scene.game.world.event;

import game.cycle.scene.game.world.go.GO;
import game.script.ScriptGame;
import game.script.game.go.go_DoorTeleport;
import game.script.game.go.go_DoorUse;

public class GameScriptLoader {
	
	// GO Script base
	public static final int go_DoorUse = 1;
	public static final int go_DoorTeleport = 2;
	
	public static ScriptGame getScript(GO go) {
		return getScript(go, go.proto.scriptId);
	}

	public static ScriptGame getScript(GO go, int scriptId) {
		switch(scriptId){
			case go_DoorUse:
				return new go_DoorUse(go);
			
			case go_DoorTeleport:
				return new go_DoorTeleport(go);
		}
	
		return null;
	}
}
