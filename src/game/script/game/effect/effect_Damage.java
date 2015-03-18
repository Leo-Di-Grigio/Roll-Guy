package game.script.game.effect;

import game.cycle.scene.game.world.LocationObject;
import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.game.world.creature.NPC;
import game.cycle.scene.game.world.skill.Effect;
import game.script.game.event.GameEvents;

public class effect_Damage implements Effect {

	private int damage;
	
	public effect_Damage(int damage) {
		this.damage = damage;
	}

	@Override
	public void execute(LocationObject caster, LocationObject target) {
		if(caster.isPlayer()){
			GameEvents.gameModeTurnBased(true);
		}
		
		boolean isAlive = target.damage(damage);
		
		if(target.isNPC() && caster.isCreature()){
			NPC npc = (NPC)target;
			npc.aidata.addEnemy((Creature)caster);
		}
		
		if(!isAlive){
			GameEvents.gameModeRealTime();
			GameEvents.destroyed(target);
		}
	}
}
