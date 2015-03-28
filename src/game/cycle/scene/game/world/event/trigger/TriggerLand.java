package game.cycle.scene.game.world.event.trigger;

import game.cycle.scene.game.world.event.LocationEvent;
import game.cycle.scene.game.world.go.GO;
import game.tools.Log;

public class TriggerLand extends Trigger {

	public TriggerLand(GO go, int param, int scriptId, int param1, int param2, int param3, int param4) {
		super(go, Trigger.LAND, param, scriptId, param1, param2, param3, param4);
	}

	@Override
	public boolean execute(LocationEvent event, int mass) {
		if(event.type == LocationEvent.Type.TRIGGER && event.eventType == LocationEvent.Event.TRIGGER_LAND){
			Log.debug("go: " + go.getId() + " Trigger LAND.");
			
			if(this.script != null){
				if(param == 0 || (mass >= param)){
					this.script.execute(event.source);
				}
			}
			
			return true;
		}
		else{
			return false;
		}
	}
}
