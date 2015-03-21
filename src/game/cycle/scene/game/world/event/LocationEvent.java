package game.cycle.scene.game.world.event;

import game.cycle.scene.game.world.LocationObject;

public class LocationEvent {
	// constants
	public static enum Type {
		VISUAL,
		SOUND,
		SCRIPT,
	};
	
	public static enum Event {
		NULL,
		ATTACK,
		TALKING,
	}
	
	// data
	public Type type;
	public Event eventType;
	public LocationObject source;
	public LocationObject target;
	
	public LocationEvent(Type type, Event eventType, LocationObject source, LocationObject target) {
		this.type = type;
		this.eventType = eventType;
		this.source = source;
		this.target = target;
	}
}
