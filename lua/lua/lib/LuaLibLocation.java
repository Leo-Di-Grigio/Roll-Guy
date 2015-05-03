package lua.lib;

import game.script.game.effect.effect_Drag;
import game.script.game.event.Logic;
import game.state.database.Database;
import game.state.location.Editor;
import game.state.location.LocationObject;
import game.state.location.creature.Creature;
import game.state.location.creature.NPC;
import game.state.location.go.GO;
import game.state.skill.Skill;

public class LuaLibLocation {

	// 
	public int getMapId(){
		return Logic.getLocation().proto.id();
	}
	
	// envirnoment
	public void updateFOV(){
		Logic.getGlobals().getPlayer().updateLOS();
		Logic.getLocation().requestUpdate();
	}
	
	// Modes
	public void realTime(){
		if(Logic.getLocation().isTurnBased()){
			Logic.requestSwitchMode(true);
		}
	}
	
	public void turnBased(){
		if(!Logic.getLocation().isTurnBased()){
			Logic.requestTurnMode(true);
		}
	}
	
	// Actions
	public void teleport(LocationObject user, GO go){
		Logic.teleport(user, go);
	}
	
	public void teleport(LocationObject user, int mapId, int x, int y){
		Logic.teleport(user, mapId, x, y);
	}
	
	// Location Object
	public void remove(int guid){
		Logic.getLocation().removeObject(guid, false);
	}
	
	public void kill(int guid){
		Logic.getLocation().killObject(guid);
	}
	
	public void kill(int x, int y){
		if(Logic.getLocation().inBound(x, y)){
			Creature creature = Logic.getLocation().map[x][y].creature;
			
			if(creature != null){
				Logic.getLocation().killObject(creature);
			}
		}
	}
	
	public void useSkill(LocationObject user, int skillId, int x, int y){
		if(user != null){
			Skill skill = Database.getSkill(skillId);
			
			if(skill != null){
				user.useSkill(Logic.getLocation(), skill, x, y);
			}
		}
	}
	
	public void drag(LocationObject user, LocationObject target){
		new effect_Drag().execute(user, target);
	}
	
	// Creature
	public Creature getCreature(int guid){
		return Logic.getLocation().getCreature(guid);
	}
	
	public void removeCreature(int x, int y){
		if(Logic.getLocation().inBound(x, y)){
			Creature creature = Logic.getLocation().map[x][y].creature;
		
			if(creature != null){
				Logic.getLocation().removeObject(creature, false);
			}
		}
	}
	
	// NPC
	public NPC getNPC(int guid){
		return Logic.getLocation().getNPC(guid);
	}
	
	public NPC spawnNPC(int id, int x, int y){
		return Editor.npcAdd(Logic.getLocation(), id, x, y, false); 
	}
	
	public void addWP(LocationObject obj, int wpGUID, int number, int pause){
		if(obj != null){
			Editor.npcWayPointAdd(Logic.getLocation(), obj.getGUID(), wpGUID, number, pause);
		}
	}
	
	public void removeWP(int npcGUID, int wpNumber){
		Editor.npcWayPointDelete(Logic.getLocation(), npcGUID, wpNumber);
	}
	
	public void clearWP(int npcGUID){
		Editor.npcWayPointClear(Logic.getLocation(), npcGUID);
	}
	
	// GO
	public GO getGO(int guid){
		return Logic.getLocation().getGO(guid);
	}
	
	public GO spawnGO(int id, int x, int y){
		return Editor.goAdd(Logic.getLocation(), id, x, y, false);
	}
}
