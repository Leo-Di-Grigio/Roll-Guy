package game.cycle.scene.game.state.location.creature.ai;

import game.cycle.scene.game.state.event.Event;
import game.cycle.scene.game.state.location.Location;
import game.cycle.scene.game.state.location.LocationObject;
import game.cycle.scene.game.state.location.creature.Creature;
import game.cycle.scene.game.state.location.sound.LocationSound;

public class AITools {

	public static boolean isVisible(Location loc, Creature agent, LocationObject target){
		return (loc.checkVisiblity(agent, target) == null && Perception.isVisible(agent, loc, target));
	}
	
	protected static int getVolume(Location loc, Creature agent, Event event){
		return LocationSound.volume(loc, event.source.getPosition().x, event.source.getPosition().y, agent.getPosition().x, agent.getPosition().y, event.volume);
	}
}
