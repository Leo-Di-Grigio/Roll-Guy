package game.cycle.scene.game.state.location;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.cycle.scene.game.state.database.Database;
import game.cycle.scene.game.state.location.creature.NPC;
import game.cycle.scene.game.state.location.go.GO;
import game.cycle.scene.game.state.location.go.GOFactory;
import game.cycle.scene.ui.list.UIGame;
import game.tools.Const;
import game.tools.Log;

public class Editor {
	
	private static final String folderTextures = "assets/textures/";
	
	public static void imageScreenLoader(Location loc, String file, int terrainFirst, int terrainSecond){
		try {
			File img = new File(folderTextures + file);
	    	BufferedImage image;

			image = ImageIO.read(img);
			int sizeX = image.getWidth();
    		int sizeY = image.getHeight();
	    		
			int [][] tmp = new int[sizeX][sizeY];
		
			for(int i = 0; i < sizeX; ++i){
				for(int j = 0, y = 0; j < sizeY; ++j){
					y = sizeY - j - 1;
					tmp[i][y] = image.getRGB(i, j);
				}
			}
	    
	    	for(int i = 0; i < sizeX; ++i){
	    		for(int j = 0; j < sizeY; ++j){
	    			if(tmp[i][j] == 0xffffffff){
	    				loc.map[i][j].proto = Database.getTerrainProto(terrainFirst);
	    			}
	    			else{
	    				loc.map[i][j].proto = Database.getTerrainProto(terrainSecond);
	    			}
		    	}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void editorTerrain(Location loc, int x, int y, UIGame ui, int brush) {
		if(loc.inBound(x, y)){
			int terrainid = ui.getSelectedListTerrain();
			
			if(terrainid != Const.INVALID_ID){
				if(brush == UIGame.MODE_TERRAIN_FILL){
					int old = loc.map[x][y].proto.id();
					fillTerrain(loc, x, y, old, terrainid);
				}
				else{
					int size = 1;
					switch (brush) {
						case UIGame.MODE_TERRAIN_BRUSH_1: size = 1; break;
						case UIGame.MODE_TERRAIN_BRUSH_2: size = 2; break;
						case UIGame.MODE_TERRAIN_BRUSH_3: size = 3; break;
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
			
			loc.requestUpdate();
		}
	}
	
	private static void fillTerrain(Location loc, int beginX, int beginY, int old, int newTerrain){
		if(loc.map[beginX][beginY].proto.id() == newTerrain){
			return;
		}
		else{
			ArrayList<Integer> Qx = new ArrayList<Integer>();
			ArrayList<Integer> Qy = new ArrayList<Integer>();
			
			Qx.add(beginX);
			Qy.add(beginY);
			
			while(!Qx.isEmpty() && !Qy.isEmpty()){
				int x = Qx.remove(0);
				int y = Qy.remove(0);
				
				if(loc.inBound(x + 1, y)){
					if(loc.map[x+1][y].proto.id() == old){
						loc.map[x+1][y].proto = Database.getTerrainProto(newTerrain);
						Qx.add(x+1);
						Qy.add(y);
					}
				}
				
				if(loc.inBound(x - 1, y)){
					if(loc.map[x-1][y].proto.id() == old){
						loc.map[x-1][y].proto = Database.getTerrainProto(newTerrain);
						Qx.add(x-1);
						Qy.add(y);
					}
				}
				
				if(loc.inBound(x, y + 1)){
					if(loc.map[x][y+1].proto.id() == old){
						loc.map[x][y+1].proto = Database.getTerrainProto(newTerrain);
						Qx.add(x);
						Qy.add(y+1);
					}
				}
				
				if(loc.inBound(x, y-1)){
					if(loc.map[x][y-1].proto.id() == old){
						loc.map[x][y-1].proto = Database.getTerrainProto(newTerrain);
						Qx.add(x);
						Qy.add(y-1);
					}
				}
			}
		}
	}
	
	public static GO goAdd(Location loc, UIGame ui, int x, int y, boolean permanent) {
		if(loc.inBound(x, y)){
			int id = ui.getSelectedListGO();
		
			if(id != Const.INVALID_ID){
				if(loc.map[x][y].go == null){
					GO go = GOFactory.getGo(Const.INVALID_ID, id, x, y, 0, 0, 0, 0);
					
					if(loc.addObject(go, x, y, permanent)){
						if(permanent){
							go.setSpawnPosition(x, y);
						}
						else{
							go.setSpawnPosition(null);
						}
						
						return go;	
					}
					else{
						return null;
					}
				}
				else{
					loc.removeObject(loc.map[x][y].go.getGUID(), permanent);
					return null;
				}
			}
			else{
				if(loc.map[x][y].go != null){
					loc.removeObject(loc.map[x][y].go.getGUID(), permanent);
				}
				return null;
			}
		}
		else{
			return null;
		}
	}

	public static GO goAdd(Location loc, int id, int x, int y, boolean permanent) {
		if(loc.inBound(x, y)){
			if(loc.map[x][y].go == null){
				GO go = GOFactory.getGo(Const.INVALID_ID, id, x, y, 0, 0, 0, 0);
				loc.addObject(go, x, y, permanent);
				
				if(permanent){
					go.setSpawnPosition(x, y);
				}
				else{
					go.setSpawnPosition(null);
				}
				
				return go;
			}
			else{
				Log.err("Editor.goAdd(): node x: " + x + " y: " + y + " already blocked");
				return null;
			}
		}
		else{
			return null;
		}
	}
	
	public static void goAdd(Location loc, GO go, int posx, int posy) {
		loc.addObject(go, posx, posy, true);
	}

	public static void goEdit(Location loc, int x, int y, UIGame ui) {
		if(loc.inBound(x, y)){
			ui.goEdit.setGO(loc.map[x][y].go);
		}
	}

	public static NPC npcAdd(Location loc, UIGame ui, int x, int y, boolean permanent) {
		if(loc.inBound(x, y)){
			int id = ui.getSelectedListNpc();
		
			if(id != Const.INVALID_ID){
				if(loc.map[x][y].creature == null){
					NPC npc = new NPC(Const.INVALID_ID, Database.getCreature(id));
					loc.addObject(npc, x, y, permanent);
					
					if(permanent){
						npc.setSpawnPosition(x, y);
					}
					else{
						npc.setSpawnPosition(null);
					}
					
					return npc;
				}
				else{
					loc.removeObject(loc.map[x][y].creature, permanent);
					return null;
				}
			}
			else{
				loc.removeObject(loc.map[x][y].creature, permanent);
				return null;
			}
		}
		else{
			return null;
		}
	}

	public static NPC npcAdd(Location loc, int id, int x, int y, boolean permanent) {
		if(loc.inBound(x, y)){
			if(loc.map[x][y].creature == null){
				NPC npc = new NPC(Const.INVALID_ID, Database.getCreature(id));
				loc.addObject(npc, x, y, permanent);
				
				if(permanent){
					npc.setSpawnPosition(x, y);
				}
				else{
					npc.setSpawnPosition(null);
				}
					
				return npc;
			}
			else{
				Log.err("Editor.npcAdd(): node x: " + x + " y: " + y + " already blocked");
				return null;
			}
		}
		else{
			return null;
		}
	}
		
	public static void npcEdit(Location loc, int x, int y, UIGame ui){
		if(loc.inBound(x, y)){
			ui.npcEdit.setCreature(loc.map[x][y].creature);
		}
	}
	
	public static int npcWayPointAdd(Location loc, int npcGUID, int wpGUID, int number, int pause) {
		NPC npc = loc.getNPC(npcGUID);
		if(npc == null){
			return 1;
		}
		else{
			GO go = loc.getGO(wpGUID);
			
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
		NPC npc = loc.getNPC(npcGUID);
		if(npc == null){
			return 0;	
		}
		else{
			npc.printWayPoints();
			return 1;
		}
	}

	public static int npcWayPointDelete(Location loc, int npcGUID, int wpNumber) {
		NPC npc = loc.getNPC(npcGUID);
		if(npc == null){
			return 0;
		}
		else{
			npc.aidata.removeWayPoint(wpNumber);
			return 1;
		}
	}
	
	public static int npcWayPointClear(Location loc, int npcGUID){
		NPC npc = loc.getNPC(npcGUID);
		if(npc == null){
			return 0;
		}
		else{
			npc.aidata.clearWayPoints();
			return 1;
		}
	}
}
