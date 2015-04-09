package game.cycle.scene.game.world.location.lighting;

import game.cycle.scene.game.world.location.Location;
import game.cycle.scene.game.world.location.Terrain;
import game.cycle.scene.game.world.location.go.GO;
import game.tools.Log;
import game.tools.Tools;

import java.awt.Point;
import java.util.HashMap;
import java.util.Set;

public class LocationLighting {
	
	private HashMap<Integer, LightingArea> lightingAreas;
	
	public LocationLighting() {
		lightingAreas = new HashMap<Integer, LightingArea>();
	}
	
	public int [] getIntArray(){
		int [] arr = new int[lightingAreas.size() * 6]; // index, x0, y0, x1, y1, power
		
		Set<Integer> keys = lightingAreas.keySet();
		int i = 0;
		for(Integer key: keys){
			LightingArea area = lightingAreas.get(key);
			
			arr[i] = key;
			arr[i+1] = area.x0;
			arr[i+2] = area.y0;
			arr[i+3] = area.x1;
			arr[i+4] = area.y1;
			arr[i+5] = area.power;
			
			i += 6;
		}
		
		return arr;
	}
	
	public LightingArea getArea(int key){
		return lightingAreas.get(key);
	}

	public int size() {
		return lightingAreas.size();
	}
	
	public static void removeArea(Location loc, int key){
		if(loc != null){
			loc.light.lightingAreas.remove(key);
			loc.requestUpdate();
		}
	}
	
	public static void addArea(Location loc, int key, int x0, int y0, int x1, int y1, int power){
		if(loc != null){
			int tmp = 0;
			if(x0 > x1){ // swap
				tmp = x0;
				x0 = x1;
				x1 = tmp;
			}
			if(y0 > y1){ // swap
				tmp = y0;
				y0 = y1;
				y1 = tmp;
			}
			
			// range check
			x0 = Math.max(0, x0);
			y0 = Math.max(0, y0);
			x1 = Math.max(0, x1);
			y1 = Math.max(0, y1);
			x0 = Math.min(loc.proto.sizeX - 1, x0);
			y0 = Math.min(loc.proto.sizeY - 1, y0);
			x1 = Math.min(loc.proto.sizeX - 1, x1);
			y1 = Math.min(loc.proto.sizeY - 1, y1);
			
			// add
			loc.light.lightingAreas.put(key, new LightingArea(x0, y0, x1, y1, power));
		}
	}
	
	public static void printAreas(Location loc){
		if(loc != null){
			// global
			Log.msg("Global light power: " + loc.proto.environmentLight);
			
			// all areas
			Set<Integer> keys = loc.light.lightingAreas.keySet();
			for(Integer key: keys){
				LightingArea area = loc.light.lightingAreas.get(key);
				Log.msg("ID: " + key 
						+ " x0: " + area.x0 + " y0: " + area.y0 
						+ " x1: " + area.x1 + " y1: " + area.y1 
						+ " power: " + area.power);
			}
		}
	}

	public static void setEnvironmentLight(Location loc, int power) {
		if(loc != null){
			loc.proto.environmentLight = power;
			loc.requestUpdate();
		}
	}
	
	public static void updateLighting(Location loc){
		if(loc != null){
			// clear location lighting
			for(int i = 0; i < loc.proto.sizeX; ++i){
				for(int j = 0; j < loc.proto.sizeY; ++j){
					loc.map[i][j].lighting = loc.proto.environmentLight;
				}
			}
			
			// lighting areas
			for(LightingArea area: loc.light.lightingAreas.values()){
				lightingArea(loc, area);
			}
		
			// go's lighting
			for(GO go: loc.gos.values()){
				if(go.proto.lighting){
					lightingGO(loc, go, go.getPosition().x, go.getPosition().y);
				}
			}
		}
	}
	
	private static void lightingArea(Location loc, LightingArea area) {
		for(int i = area.x0; i < area.x1; ++i){
			for(int j = area.y0; j < area.y1; ++j){
				loc.map[i][j].lighting += area.power;
			}
		}
	}

	private static void lightingGO(Location loc, GO go, int x, int y) {
		int power = (int)(Math.sqrt(go.proto.lightingPower));
		
		if(power > 0){
			int minx = Math.max(0, x - power);
			int miny = Math.max(0, y - power);
			int maxx = Math.min(loc.proto.sizeX - 1, x + power);
			int maxy = Math.min(loc.proto.sizeY - 1, y + power);
			float range = 0.0f;
			
			for(int i = minx; i < maxx; ++i){
				for(int j = miny; j < maxy; ++j){
					Point endFOV = loc.checkVisiblity(x, y, i, j);
					
					if(endFOV == null){
						Terrain node = loc.map[i][j];
						
						range = (float)Tools.getRange(i, j, x, y);
						node.lighting += power*100/(range*range);
					}
					else{
						int fovX = endFOV.x;
						int fovY = endFOV.y;
						
						if(loc.inBound(fovX, fovY)){
							Terrain node = loc.map[fovX][fovY];
							range = (float)Tools.getRange(fovX, fovY, x, y);
							node.lighting += power*100/(range*range);
						}
					}
				}
			}
		}
	}	
}
