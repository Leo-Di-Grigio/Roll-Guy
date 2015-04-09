package game.script.game.effect;

import game.cycle.scene.game.world.event.LocationEvent;
import game.cycle.scene.game.world.event.LocationEvent.Event;
import game.cycle.scene.game.world.event.LocationEvent.Type;
import game.cycle.scene.game.world.location.LocationObject;
import game.cycle.scene.game.world.location.creature.Creature;
import game.cycle.scene.game.world.location.creature.NPC;
import game.cycle.scene.game.world.location.go.GO;
import game.cycle.scene.game.world.skill.Effect;
import game.script.game.event.GameEvents;

public class effect_Damage implements Effect {

	private int damage;
	
	public effect_Damage(int damage) {
		this.damage = damage;
	}

	@Override
	public void execute(LocationObject caster, LocationObject target) {
		if(caster.getGUID() != target.getGUID()){
			GameEvents.requestSwitchMode(caster.isPlayer());
			GameEvents.addLocationEvent(new LocationEvent(Type.VISUAL, Event.ATTACK, caster, target));
			GameEvents.addLocationEvent(new LocationEvent(Type.SOUND, Event.ATTACK, caster, target));
			
			boolean isAlive = target.damage(damage);
		
			if(target.isNPC() && caster.isCreature()){
				NPC npc = (NPC)target;
				npc.aidata.addEnemy((Creature)caster);
			}
			
			if(target.isGO()){
				GO go = (GO)target;
				go.event(new LocationEvent(Type.TRIGGER, Event.DAMAGE, caster, target), damage);
			}
		
			if(!isAlive){
				GameEvents.destroyed(target);
			}
		}
	}
}
