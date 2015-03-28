package game.cycle.scene.game.world.event.trigger;

import game.cycle.scene.game.world.event.LocationEvent;
import game.cycle.scene.game.world.go.GO;
import game.tools.Log;

public class TriggerLink extends Trigger {

	public TriggerLink(GO go, int param, int scriptId, int param1, int param2, int param3, int param4) {
		super(go, Trigger.LINK, param, scriptId, param1, param2, param3, param4);
	}

	@Override
	public boolean execute(LocationEvent event, int triggerSlot) {
		if(event == null){
			Log.debug("go: " + go.getId() + " Trigger LINK. Slot: " + (triggerSlot+1));
			
			if(this.script != null){
				if(triggerSlot + 1 == param || triggerSlot == 0){ // use 1,2,3,4 trigger slots
					this.script.execute(go);
				}
			}
			
			return true;
		}
		else{
			return false;
		}
	}
}
