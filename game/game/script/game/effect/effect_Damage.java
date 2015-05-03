package game.script.game.effect;

import game.script.game.event.Logic;
import game.state.event.Event;
import game.state.location.LocationObject;
import game.state.location.creature.Creature;
import game.state.location.creature.NPC;
import game.state.location.go.GO;
import game.state.skill.SkillEffect;

public class effect_Damage implements SkillEffect {

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
			
			Logic.addEvent(new Event(Event.EVENT_VISUAL_ATTACK, caster, target));
			Logic.addEvent(new Event(Event.EVENT_SOUND_ATTACK, caster, target, 100)); // volume == 20
			
			boolean isAlive = target.damage(damage);
		
			if(target.isNPC() && caster.isCreature()){
				NPC npc = (NPC)target;
				npc.aidata.addEnemy((Creature)caster);
			}
			
			if(target.isGO()){
				GO go = (GO)target;
				go.event(new Event(Event.EVENT_DAMAGE, caster, target));
			}
		
			if(!isAlive){
				Logic.destroyed(target);
				Logic.requestUpdate();
			}
		}
	}
}
