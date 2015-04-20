package game.script.game.effect;

import game.cycle.scene.game.state.event.LocationEvent;
import game.cycle.scene.game.state.location.LocationObject;
import game.cycle.scene.game.state.location.creature.Creature;
import game.cycle.scene.game.state.location.creature.NPC;
import game.cycle.scene.game.state.location.go.GO;
import game.cycle.scene.game.state.skill.Effect;
import game.script.game.event.Logic;

public class effect_Damage implements Effect {

	private int damage;
	
	public effect_Damage(int damage) {
		this.damage = damage;
	}

	@Override
	public void execute(LocationObject caster, LocationObject target) {
		if(caster.getGUID() != target.getGUID()){
			if(target.isCreature()){
				Logic.requestTurnMode(caster.isPlayer());
			}
			
			Logic.addLocationEvent(new LocationEvent(LocationEvent.EVENT_VISUAL, LocationEvent.CONTEXT_ATTACK, caster, target));
			Logic.addLocationEvent(new LocationEvent(LocationEvent.EVENT_SOUND, LocationEvent.CONTEXT_ATTACK, caster, target));
			
			boolean isAlive = target.damage(damage);
		
			if(target.isNPC() && caster.isCreature()){
				NPC npc = (NPC)target;
				npc.aidata.addEnemy((Creature)caster);
			}
			
			if(target.isGO()){
				GO go = (GO)target;
				go.event(new LocationEvent(LocationEvent.EVENT_TRIGGER, LocationEvent.CONTEXT_DAMAGE, caster, target));
			}
		
			if(!isAlive){
				Logic.destroyed(target);
				Logic.requestUpdate();
			}
		}
	}
}
