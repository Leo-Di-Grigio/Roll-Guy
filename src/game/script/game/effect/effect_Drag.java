package game.script.game.effect;

import game.cycle.scene.game.world.LocationObject;
import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.game.world.go.GO;
import game.cycle.scene.game.world.skill.Effect;
import game.script.game.event.GameEvents;

public class effect_Drag implements Effect {

	@Override
	public void execute(LocationObject caster, LocationObject target) {
		if(caster.getDraggedObject() == null){
			GameEvents.characterDropObject(caster);
		}
		
		if(caster.getDraggedObject() == null && caster.getGUID() != target.getGUID()){
			if(target.isGO()){
				GO go = (GO)target;
			
				if(go.proto.dragble){
					GameEvents.characterDragObject(caster, target);
				}
			}
			else if(target.isCreature()){
				Creature creature = (Creature)target;
			
				if(!creature.isAlive()){
					GameEvents.characterDragObject(caster, target);
				}
			}
		}
	}
}
