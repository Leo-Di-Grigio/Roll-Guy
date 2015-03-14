package game.script.game.go;

import game.cycle.scene.game.world.go.GO;
import game.script.Script;
import game.script.game.event.GameEvents;

public class go_DoorTeleport implements Script {

	public GO go;
	
	public go_DoorTeleport(GO go) {
		this.go = go;
	}

	@Override
	public void execute() {
		GameEvents.teleport(go);
	}
}
