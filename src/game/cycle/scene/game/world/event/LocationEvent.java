package game.cycle.scene.game.world.event;

import game.cycle.scene.game.world.location.LocationObject;

public class LocationEvent {
	// constants
	public static enum Type {
		VISUAL,
		SOUND,
		SCRIPT, 
		TRIGGER,
	};
	
	public static enum Event {
		NULL,
		ATTACK,
		TALKING, 
		
		// triggers
		DAMAGE, 
		GO_USE, 
		TRIGGER_LAND, 
		TRIGGER_LINK, 
		
		// dialog
		DIALOG_BEGIN,
		DIALOG_END,
	};
	
	// data
	public Type type;
	public Event event;
	public LocationObject source;
	public LocationObject target;
	
	public LocationEvent(Type type, Event event, LocationObject source, LocationObject target) {
		this.type = type;
		this.event = event;
		this.source = source;
		this.target = target;
	}
}
