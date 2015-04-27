package game.script.game.effect;

import game.cycle.scene.game.state.location.Location;
import game.cycle.scene.game.state.location.LocationObject;
import game.cycle.scene.game.state.location.creature.Creature;
import game.cycle.scene.game.state.location.go.GO;
import game.cycle.scene.game.state.skill.SkillEffect;
import game.script.game.event.Logic;

public class effect_Drag implements SkillEffect {

	@Override
	public void execute(LocationObject caster, LocationObject target) {
		if(caster.getDraggedObject() == null){
			Logic.characterDropObject(caster);
		}
		
		if(caster.getDraggedObject() == null && caster.getGUID() != target.getGUID()){
			if(target.isGO()){
				GO go = (GO)target;
			
				if(go.proto.dragble()){
					Logic.characterDragObject(caster, target);
				}
			}
			else if(target.isCreature()){
				Creature creature = (Creature)target;
			
				if(!creature.isAlive()){
					Logic.characterDragObject(caster, target);
				}
			}
		}
	}
	
	// Location changes
	public static void characterDragObject(Location loc, LocationObject caster, LocationObject target) {

	}
	
	public static void characterDropObject(Location loc, LocationObject caster) {
		
	}
}
