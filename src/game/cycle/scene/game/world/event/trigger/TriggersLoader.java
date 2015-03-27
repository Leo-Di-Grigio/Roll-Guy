package game.cycle.scene.game.world.event.trigger;

public class TriggersLoader {
	
	public static Trigger getTrigger(int type, int p, int script, int p1, int p2, int p3, int p4) {
		switch (type) {
			case Trigger.NULL:
			case Trigger.NULL_1:
				return null;

			case Trigger.GO_USE:
				return new TriggerGoUse(p, script, p1, p2, p3, p4);
				
			case Trigger.LAND:
				return null;
				
			case Trigger.SOUND:
				return null;
				
			case Trigger.EFFECT:
				return null;
				
			case Trigger.LINK:
				return null;
				
			case Trigger.ITEM:
				return null;
				
			case Trigger.DAMAGE:
				return null;
						
			default:
				return null;
		}
	}
}
