package game.cycle.scene.game.world.location;

import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.database.GameConst;
import game.cycle.scene.game.world.location.creature.NPC;
import game.cycle.scene.game.world.location.go.GO;
import game.cycle.scene.game.world.location.go.GOFactory;
import game.cycle.scene.ui.list.UIGame;
import game.tools.Const;

public class Editor {
	
	public static void editorTerrain(Location loc, int x, int y, UIGame ui, int brush) {
		if(loc.inBound(x, y)){
			int terrainid = ui.getSelectedListTerrain();
			
			if(terrainid != Const.INVALID_ID){
				int size = 1;
				switch (brush) {
					case UIGame.modeTerrainBrush1: size = 1; break;
					case UIGame.modeTerrainBrush2: size = 2; break;
					case UIGame.modeTerrainBrush3: size = 3; break;
				}
				
				for(int i = 0; i < size; ++i){
					for(int j = 0; j < size; ++j){
						if(loc.inBound(i + x, j + y)){
							loc.map[i + x][j + y].proto = Database.getTerrainProto(terrainid);
						}
					}
				}
			}
		}
	}
	
	public static void npcAdd(Location loc, int x, int y, UIGame ui) {
		if(loc.inBound(x, y)){
			int id = ui.getSelectedListNpc();
		
			if(id != Const.INVALID_ID){
				if(loc.map[x][y].creature == null){
					NPC npc = new NPC(Const.INVALID_ID, Database.getCreature(id));
					npc.setSpawnPosition(x, y);
					npc.setPosition(x, y);
					npc.setSpritePosition(x*GameConst.TILE_SIZE, y*GameConst.TILE_SIZE);
					loc.addCreature(npc, x, y);
				}
				else{
					loc.deleteObject(loc.map[x][y].creature);
				}
			}
			else{
				loc.deleteObject(loc.map[x][y].creature);
			}
		}
	}
	
	public static void npcEdit(Location loc, int x, int y, UIGame ui){
		if(loc.inBound(x, y)){
			ui.npcEdit.setCreature(loc.map[x][y].creature);
		}
	}
	
	public static void goAdd(Location loc, int x, int y, UIGame ui) {
		if(loc.inBound(x, y)){
			int id = ui.getSelectedListGO();
		
			if(id != Const.INVALID_ID){
				if(loc.map[x][y].go == null){
					GO go = GOFactory.getGo(Const.INVALID_ID, id, x, y, 0, 0, 0, 0);
					loc.map[x][y].go = go;
					
					if(go.proto.waypoint){
						loc.waypoints.put(go.getGUID(), go);
					}
				}
				else{
					if(loc.map[x][y].go.proto.waypoint){
						loc.waypoints.remove(loc.map[x][y].go.getGUID());
					}
					loc.map[x][y].go = null;
				}
			}
			else{
				if(loc.map[x][y].go.proto.waypoint){
					loc.waypoints.remove(loc.map[x][y].go.getGUID());
				}
				loc.map[x][y].go = null;
			}
		}
	}
	
	public static void goAdd(Location loc, GO go, int posx, int posy) {
		loc.map[posx][posy].go = go;
		
		if(go.proto.waypoint){
			loc.waypoints.put(go.getGUID(), go);
		}
	}

	public static void goEdit(Location loc, int x, int y, UIGame ui) {
		if(loc.inBound(x, y)){
			ui.goEdit.setGO(loc.map[x][y].go);
		}
	}
	
	public static int addNpcWayPoint(Location loc, int npcGUID, int wpGUID, int number, int pause) {
		NPC npc = loc.npcs.get(npcGUID);
		if(npc == null){
			return 1;
		}
		else{
			GO go = loc.waypoints.get(wpGUID);
			
			if(go == null){
				return 2;
			}
			else{
				npc.addWayPoint(go, number, pause);
				return 0;
			}
			
		}
	}

	public static int npcWayPointList(Location loc, int npcGUID) {
		NPC npc = loc.npcs.get(npcGUID);
		if(npc == null){
			return 0;	
		}
		else{
			npc.printWayPoints();
			return 1;
		}
	}

	public static int npcWayPointDelete(Location loc, int npcGUID, int wpNumber) {
		NPC npc = loc.npcs.get(npcGUID);
		if(npc == null){
			return 0;
		}
		else{
			npc.aidata.removeWayPoint(wpNumber);
			return 1;
		}
	}
}
