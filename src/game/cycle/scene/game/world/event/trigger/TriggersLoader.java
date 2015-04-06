package game.cycle.scene.game.world.event.trigger;

import game.cycle.scene.game.world.location.go.GO;

public class TriggersLoader {
	
	public static Trigger getTrigger(GO go, int type, int p, int script, int p1, int p2, int p3, int p4) {
		switch (type) {
			case Trigger.NULL:
			case Trigger.NULL_1:
				return null;

			case Trigger.GO_USE:
				return new TriggerGoUse(go, p, script, p1, p2, p3, p4);
				
			case Trigger.LAND:
				return new TriggerLand(go, p, script, p1, p2, p3, p4);
				
			case Trigger.SOUND: //-
				return new TriggerSound(go, p, script, p1, p2, p3, p4);
				
			case Trigger.EFFECT: //-
				return new TriggerEffect(go, p, script, p1, p2, p3, p4);
				
			case Trigger.LINK:
				return new TriggerLink(go, p, script, p1, p2, p3, p4);
				
			case Trigger.ITEM:
				return new TriggerItem(go, p, script, p1, p2, p3, p4);
				
			case Trigger.DAMAGE:
				return new TriggerDamage(go, p, script, p1, p2, p3, p4);
						
			default:
				return null;
		}
	}
}
