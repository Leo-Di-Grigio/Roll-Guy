package game.cycle.scene.game.world.event.trigger;

import game.cycle.scene.game.world.event.LocationEvent;
import game.cycle.scene.game.world.go.GO;
import game.tools.Const;
import game.tools.Log;

public class TriggerItem extends Trigger {

	public TriggerItem(GO go, int param, int scriptId, int param1, int param2, int param3, int param4) {
		super(go, Trigger.ITEM, param, scriptId, param1, param2, param3, param4);
	}

	@Override
	public boolean execute(LocationEvent event, int itemGUID) {
		if(itemGUID != Const.invalidId){
			Log.debug("go: " + go.getId()  + " Trigger ITEM. Item GUID: " + itemGUID);
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
