package game.cycle.scene.game.world.event.trigger;

import game.cycle.scene.game.world.event.LocationEvent;
import game.cycle.scene.game.world.location.go.GO;
import game.tools.Log;

public class TriggerDamage extends Trigger {

	public TriggerDamage(GO go, int param, int scriptId, int param1, int param2, int param3, int param4) {
		super(go, Trigger.DAMAGE, param, scriptId, param1, param2, param3, param4);
	}

	@Override
	public boolean execute(LocationEvent event, int damage) {
		if(event.type == LocationEvent.Type.TRIGGER && event.event == LocationEvent.Event.DAMAGE){
			Log.debug("go: " + go.getGUID()  + " Trigger DAMAGE. Dealed " + damage);
			
			if(this.script != null){
				if(param == 0 || (damage >= param)){
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
