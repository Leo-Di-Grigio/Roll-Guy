package game.cycle.scene.game.world.event.trigger;

import game.cycle.scene.game.world.event.LocationEvent;
import game.cycle.scene.game.world.go.GO;

public class TriggerGoUse extends Trigger {

	public TriggerGoUse(GO go, int param, int scriptId, int param1, int param2, int param3, int param4) {
		super(go, Trigger.GO_USE, param, scriptId, param1, param2, param3, param4);
	}

	@Override
	public void execute(LocationEvent event, int param) {
		
	}
}
