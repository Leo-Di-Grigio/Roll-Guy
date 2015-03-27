package game.cycle.scene.game.world.event.trigger;

import game.cycle.scene.game.world.event.LocationEvent;

public class TriggerSound extends Trigger {

	public TriggerSound(int param, int scriptId, int param1, int param2, int param3, int param4) {
		super(Trigger.SOUND, scriptId, param1, param2, param3, param4);
	}

	@Override
	public void execute(LocationEvent event) {
		
	}
}
