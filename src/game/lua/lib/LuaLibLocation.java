package game.lua.lib;

import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.location.Editor;
import game.cycle.scene.game.world.location.LocationObject;
import game.cycle.scene.game.world.location.creature.Creature;
import game.cycle.scene.game.world.location.creature.NPC;
import game.cycle.scene.game.world.location.go.GO;
import game.cycle.scene.game.world.skill.Skill;
import game.script.game.effect.effect_Drag;
import game.script.game.event.GameEvents;

public class LuaLibLocation {

	// Modes
	public void realTime(){
		if(GameEvents.getLocation().isTurnBased()){
			GameEvents.requestSwitchMode(true);
		}
	}
	
	public void turnBased(){
		if(!GameEvents.getLocation().isTurnBased()){
			GameEvents.requestTurnMode(true);
		}
	}
	
	// Actions
	public void teleport(LocationObject user, GO go){
		GameEvents.teleport(user, go);
	}
	
	public void teleport(LocationObject user, int mapId, int x, int y){
		GameEvents.teleport(user, mapId, x, y);
	}
	
	// Location Object
	public void remove(int guid){
		GameEvents.getLocation().removeObject(guid, false);
	}
	
	public void kill(int guid){
		GameEvents.getLocation().killObject(guid);
	}
	
	public void kill(int x, int y){
		if(GameEvents.getLocation().inBound(x, y)){
			Creature creature = GameEvents.getLocation().map[x][y].creature;
			
			if(creature != null){
				GameEvents.getLocation().killObject(creature);
			}
		}
	}
	
	public void useSkill(LocationObject user, int skillId, int x, int y){
		if(user != null){
			Skill skill = Database.getSkill(skillId);
			
			if(skill != null){
				user.useSkill(GameEvents.getLocation(), skill, x, y);
			}
		}
	}
	
	public void drag(LocationObject user, LocationObject target){
		new effect_Drag().execute(user, target);
	}
	
	// Creature
	public void removeCreature(int x, int y){
		if(GameEvents.getLocation().inBound(x, y)){
			Creature creature = GameEvents.getLocation().map[x][y].creature;
		
			if(creature != null){
				GameEvents.getLocation().removeObject(creature, false);
			}
		}
	}
	
	// NPC
	public NPC getNPC(int guid){
		return GameEvents.getLocation().getNPC(guid);
	}
	
	public NPC spawnNPC(int id, int x, int y){
		return Editor.npcAdd(GameEvents.getLocation(), id, x, y, false); 
	}
	
	public void addWP(LocationObject obj, int wpGUID, int number, int pause){
		if(obj != null){
			Editor.npcWayPointAdd(GameEvents.getLocation(), obj.getGUID(), wpGUID, number, pause);
		}
	}
	
	public void removeWP(int npcGUID, int wpNumber){
		Editor.npcWayPointDelete(GameEvents.getLocation(), npcGUID, wpNumber);
	}
	
	public void clearWP(int npcGUID){
		Editor.npcWayPointClear(GameEvents.getLocation(), npcGUID);
	}
	
	// GO
	public GO getGO(int guid){
		return GameEvents.getLocation().getGO(guid);
	}
	
	public GO spawnGO(int id, int x, int y){
		return Editor.goAdd(GameEvents.getLocation(), id, x, y, false);
	}
}
