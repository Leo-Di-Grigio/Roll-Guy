package game.cycle.scene.game.world.event;

import game.cycle.scene.game.world.location.LocationObject;

public class LocationEvent {
	public static final int EVENT_VISUAL = 0;
	public static final int EVENT_SOUND  = 1;
	public static final int EVENT_SCRIPT = 2;
	public static final int EVENT_TRIGGER = 3;
	
	public static final int CONTEXT_NULL = 0;
	public static final int CONTEXT_ATTACK = 1;
	public static final int CONTEXT_TALKING = 2;
	public static final int CONTEXT_DAMAGE = 3;
	public static final int CONTEXT_GO_USE = 4;
	public static final int CONTEXT_LAND = 5;
	public static final int CONTEXT_LINK = 6;
	public static final int CONTEXT_DIALOG_BEGIN = 7;
	public static final int CONTEXT_DIALOG_END = 8;
	
	// data
	public int type;
	public int context;
	public LocationObject source;
	public LocationObject target;
	
	public LocationEvent(int type, int context, LocationObject source, LocationObject target) {
		this.type = type;
		this.context = context;
		this.source = source;
		this.target = target;
	}
}
