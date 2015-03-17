package game.cycle.scene.game.world.creature.ai;

import java.awt.Point;

import game.cycle.scene.game.world.creature.NPC;
import game.cycle.scene.game.world.map.Location;
import game.cycle.scene.game.world.map.Terrain;

// јд∆опа—атана
public class AI {
	
	public static void execute(Location loc, NPC agent){
		agent.aidata.clear();
		
		reciveSensorData(loc, agent);
	}

	private static void reciveSensorData(Location loc, NPC agent){
		Terrain [][] map = loc.map;
		
		Point pos = agent.getPosition();
		int x = pos.x;
		int y = pos.y;
		int r = agent.proto.stats.perception;
		
		int xmin = Math.max(x - r, 0);
		int ymin = Math.max(y - r, 0);
		int xmax = Math.min(x + r, loc.proto.sizeX - 1);
		int ymax = Math.min(y + r, loc.proto.sizeY - 1);
	
		for(int i = xmin; i < xmax; ++i){
			for(int j = ymin; j < ymax; ++j){
				if(map[i][j].creature != null && map[i][j].creature.id != agent.id){
					agent.aidata.addView(map[i][j].creature);
				}
			}
		}
	}
}
