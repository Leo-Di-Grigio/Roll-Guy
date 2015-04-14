package game.lua.lib;

import game.cycle.scene.game.world.location.Editor;
import game.cycle.scene.game.world.location.LocationObject;
import game.cycle.scene.game.world.location.creature.Creature;
import game.cycle.scene.game.world.location.creature.NPC;
import game.cycle.scene.game.world.location.go.GO;
import game.script.game.event.GameEvents;

public class LuaMethodsLocation {

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
	public NPC spawnNPC(int id, int x, int y){
		return Editor.npcAdd(GameEvents.getLocation(), id, x, y, false); 
	}
	
	public void addWP(int npcGUID, int wpGUID, int number, int pause){
		Editor.npcWayPointAdd(GameEvents.getLocation(), npcGUID, wpGUID, number, pause);
	}
	
	public void removeWP(int npcGUID, int wpNumber){
		Editor.npcWayPointDelete(GameEvents.getLocation(), npcGUID, wpNumber);
	}
	
	public void clearWP(int npcGUID){
		Editor.npcWayPointClear(GameEvents.getLocation(), npcGUID);
	}
	
	// GO
	public GO spawnGO(int id, int x, int y){
		return Editor.goAdd(GameEvents.getLocation(), id, x, y, false);
	}
}
