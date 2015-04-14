package game.lua.lib;

import game.cycle.scene.game.world.location.Editor;
import game.cycle.scene.game.world.location.LocationObject;
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
	
	// NPC	
	public void spawnNPC(int id, int x, int y){
		
	}
	
	public void killNPC(int guid){
		GameEvents.getLocation().killObject(guid);
	}
	
	public void killNPC(int x, int y){
		
	}
	
	public void removeNPC(int id){
		
	}
	
	public void removeNPC(int x, int y){
		
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
	public void spawnGO(int id, int x, int y){
		
	}
	
	public void killGO(int guid){
		
	}
	
	public void killGO(int x, int y){
		
	}
	
	public void removeGO(int id){
		
	}
	
	public void removeGO(int x, int y){
		
	}
}
