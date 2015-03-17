package game.cycle.scene.game.world.creature.ai;

import java.awt.Point;

import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.game.world.creature.NPC;
import game.cycle.scene.game.world.database.GameConst;
import game.cycle.scene.game.world.map.Location;
import game.cycle.scene.game.world.map.Terrain;

// јд∆опа—атана
public class AI {
	
	public static void execute(Location loc, NPC agent){
		agent.aidata.clear();
		
		reciveSensorData(loc, agent);
		
		if(agent.aidata.viewedEnemy.size() > 0){
			attack(loc, agent);
		}
	}

	private static void reciveSensorData(Location loc, NPC agent){
		Terrain [][] map = loc.map;
		
		Point pos = agent.getPosition();
		int x = pos.x;
		int y = pos.y;
		int r = agent.proto.stats.perception;
		
		int xmin = Math.max(x - r, 0);
		int ymin = Math.max(y - r, 0);
		int xmax = Math.min(x + r, loc.sizeX - 1);
		int ymax = Math.min(y + r, loc.sizeY - 1);
	
		for(int i = xmin; i < xmax; ++i){
			for(int j = ymin; j < ymax; ++j){
				if(map[i][j].creature != null && map[i][j].creature.id != agent.id){
					agent.aidata.addView(map[i][j].creature);
				}
			}
		}
	}
	
	private static void attack(Location loc, NPC agent) {
		float nearestRange = 100.0f; 
		Creature nearestEnemy = null;
		
		for(Creature enemy: agent.aidata.viewedEnemy.values()){
			float range = loc.getRange(agent.sprite, enemy.sprite);
			if(range < nearestRange){
				nearestRange = range;
				nearestEnemy = enemy;
			}
		}

		if(nearestRange < Location.interactRange){
			System.out.println("Near " + nearestRange);
			if(nearestEnemy != null){
				Point targetPos = nearestEnemy.getPosition();
				int targetX = targetPos.x;
				int targetY = targetPos.y;
				
				int counter = 0;
				while(nearestEnemy != null && nearestEnemy.isAlive() && (agent.ap - GameConst.getAttackAp(agent)) >= 0){
					System.out.println("attack");
					loc.attack(targetX, targetY, agent);
					counter++;
					if(counter > 10){
						break;
					}
				}
			}
		}
		else{
			System.out.println("Too far "  + nearestRange);
		}
	}
}
