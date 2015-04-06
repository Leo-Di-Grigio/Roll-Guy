package game.script.game.go;

import game.cycle.scene.game.world.location.LocationObject;
import game.cycle.scene.game.world.location.go.GO;
import game.script.ScriptGame;
import game.script.game.event.GameEvents;

public class go_DoorTeleport implements ScriptGame {

	public GO go;
	
	public go_DoorTeleport(GO go) {
		this.go = go;
	}

	@Override
	public boolean execute(LocationObject user) {
		GameEvents.teleport(go, user);
		return false;
	}
}
