package game.script.game.effect;

import game.cycle.scene.game.state.database.GameConst;
import game.cycle.scene.game.state.location.Location;
import game.cycle.scene.game.state.location.LocationObject;
import game.cycle.scene.game.state.location.creature.Creature;
import game.cycle.scene.game.state.location.go.GO;
import game.cycle.scene.game.state.skill.Effect;
import game.script.game.event.Logic;

public class effect_Drag implements Effect {

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
		caster.dragObject(target);
		
		if(target != null){
			int x = caster.getPosition().x;
			int y = caster.getPosition().y;
			
			if(target.isGO()){
				loc.map[target.getPosition().x][target.getPosition().y].go = null;
				target.setPosition(x, y);
				target.setSpritePosition(x*GameConst.TILE_SIZE, y*GameConst.TILE_SIZE);
			}
			else if(target.isCreature()){
				loc.map[target.getPosition().x][target.getPosition().y].creature = null;
				target.setPosition(x, y);
				target.setSpritePosition(x*GameConst.TILE_SIZE, y*GameConst.TILE_SIZE);
			}
		}
	}
	
	public static void characterDropObject(Location loc, LocationObject caster) {
		LocationObject object = caster.getDraggedObject();
		int x = caster.getPosition().x;
		int y = caster.getPosition().y;
		
		if(object != null){
			if(object.isGO()){
				if(loc.map[x][y].go == null){
					GO go = (GO)object;
					loc.map[x][y].go = go;
					go.setPosition(x, y);
					go.setSpritePosition(x*GameConst.TILE_SIZE, y*GameConst.TILE_SIZE);
					caster.dropObject();
				}
			}
			else if(object.isCreature()){				
				if(loc.map[x][y].creature == null){
					loc.map[x][y].creature = (Creature)object;
					caster.dropObject();
				}
				else{
					float angle = caster.getDirectAngle();
					
					if(angle <= 45.0f && (angle >= 0.0f || angle > 315.0f)){
						dropCharacter(loc, caster, object, x+1, y);
					}
					else if(angle > 45.0f && angle <= 135.0f){
						dropCharacter(loc, caster, object, x, y+1);
					}
					else if(angle > 135.0f && angle <= 225.0f){
						dropCharacter(loc, caster, object, x-1, y);
					}
					else if(angle > 225.0f && angle <= 315.0f){
						dropCharacter(loc, caster, object, x, y-1);
					}
				}
			}	
		}
	}
	
	private static void dropCharacter(Location loc, LocationObject caster, LocationObject object, int x, int y){
		if(loc.inBound(x, y) && loc.map[x][y].creature == null && loc.map[x][y].proto.passable()){
			loc.map[x][y].creature = (Creature)object;
			object.setPosition(x, y);
			object.setSpritePosition(x*GameConst.TILE_SIZE, y*GameConst.TILE_SIZE);
			caster.dropObject();
		}
	}
}
