package game.cycle.scene.game.world.creature.ai;

import java.awt.Point;

import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.game.world.creature.NPC;
import game.cycle.scene.game.world.event.LocationEvent;
import game.cycle.scene.game.world.map.Location;
import game.cycle.scene.game.world.map.Terrain;

// јд∆опа—атана
public class AI {
	
	public static void execute(Location loc, NPC agent){
		agent.aidata.executed = true;
		agent.aidata.clear();
		
		if(agent.ap == 0){
			agent.aidata.updated = true;
			return;
		}
		else{
			reciveSensorData(loc, agent);

			if(agent.aidata.viewedEnemy.size() > 0){
				attack(loc, agent);
			}
			else{
				agent.aidata.updated = true;
			}
		}
	}
	
	public static void update(Location loc, NPC agent){
		if(agent.ap == 0){
			agent.aidata.updated = true;
		}
		else{
			if(agent.aidata.viewedEnemy.size() == 0){
				agent.aidata.updated = true;
			}
			else{
				if(agent.getPath() == null){
					execute(loc, agent);
				}
			}
		}
	}

	public static void event(Location loc, LocationEvent event, NPC agent) {
		float r1 = getRange(agent.getPosition().x, agent.getPosition().y, event.source.getPosition().x, event.source.getPosition().y);
		float r2 = getRange(agent.getPosition().x, agent.getPosition().y, event.target.getPosition().x, event.target.getPosition().y);
		
		if(r1 <= agent.proto.stats.perception || r2 <= agent.proto.stats.perception){
			switch (event.eventType) {
				case ATTACK:
					eventAttack(loc, event, agent);
					break;
					
				default:
					break;
			}
		}
	}
	
	private static void eventAttack(Location loc, LocationEvent event, NPC agent) {
		if(event.target.fraction == agent.fraction){
			if(event.source.isCreature()){
				agent.aidata.addEnemy((Creature)event.source);
			}
		}
	}

	private static float getRange(int x1, int y1, int x2, int y2){
		return (float) Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
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
	
		for(int i = xmin; i <= xmax; ++i){
			for(int j = ymin; j <= ymax; ++j){
				if(map[i][j].creature != null && map[i][j].creature.getId() != agent.getId()){
					agent.aidata.addViewedEnemy(map[i][j].creature);
				}
			}
		}
	}

	private static void attack(Location loc, NPC agent) {
		float minRange = Float.MAX_VALUE;
		Creature nearestEnemy = null;
		
		for(Creature enemy: agent.aidata.viewedEnemy.values()){
			float range = loc.getRange(agent, enemy);
			
			if(range < minRange){
				minRange = range;
				nearestEnemy = enemy;
			}
		}
		
		Point pos = nearestEnemy.getPosition();
		
		if(minRange <= agent.skills.attack.range){
			// attack
			while(loc.useSkill(agent.skills.attack, agent, pos.x, pos.y));
			
			agent.aidata.updated = true;
		}
		else{
			// follow
			agent.move(loc, pos.x, pos.y);
			
			if(agent.getPath() == null){
				agent.aidata.updated = true;
			}
		}
	}
}
