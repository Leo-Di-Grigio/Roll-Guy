package game.cycle.scene.game.world.event.trigger;

import game.cycle.scene.game.world.event.LocationEvent;
import game.cycle.scene.game.world.location.go.GO;
import game.tools.Log;

public class TriggerGoUse extends Trigger {

	public TriggerGoUse(GO go, int param, int scriptId, int param1, int param2, int param3, int param4) {
		super(go, Trigger.GO_USE, param, scriptId, param1, param2, param3, param4);
	}

	@Override
	public boolean execute(LocationEvent event, int param) {
		if(event.type == LocationEvent.Type.TRIGGER && event.event == LocationEvent.Event.GO_USE){
			Log.debug("go: " + go.getGUID() + " Trigger GO_USE.");
			
			if(this.script != null){
				this.script.execute(event.source);
			}
			
			return true;
		}
		else{
			return false;
		}
	}
}