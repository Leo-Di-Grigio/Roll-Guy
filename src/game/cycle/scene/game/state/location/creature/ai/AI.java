package game.cycle.scene.game.state.location.creature.ai;

import game.cycle.scene.game.state.database.GameConst;
import game.cycle.scene.game.state.event.Event;
import game.cycle.scene.game.state.location.Location;
import game.cycle.scene.game.state.location.creature.Creature;
import game.cycle.scene.game.state.location.creature.NPC;
import game.cycle.scene.game.state.location.go.GO;
import game.tools.Tools;

public class AI {
	
	public static void fullUpdate(Location loc, NPC agent){
		agent.aidata.fullUpdate = true;
		agent.aidata.clear();
		
		reciveSensorData(loc, agent);

		if(agent.aidata.foundCorpse){
			searchPotentialEnemy(loc, agent);
		}
		
		if(agent.aidata.viewedEnemy.size() == 0){
			moveToWayPoint(loc, agent);
		}
		else{
			attack(loc, agent);
		}
	}

	public static void softUpdate(Location loc, NPC agent){
		if(agent.aidata.viewedEnemy.size() == 0){
			agent.aidata.softUpdated = true;
		}
		else{
			if(agent.getPath() == null){
				fullUpdate(loc, agent);
			}
		}
	}

	public static void event(Location loc, Event event, NPC agent) {
		float r1, r2;
		
		if(event.target == null || event.source == null){
			r1 = GameConst.AI_CALCULATE_RANGE;
			r2 = GameConst.AI_CALCULATE_RANGE;
		}
		else{
			r1 = Tools.getRange(agent, event.source);
			r2 = Tools.getRange(agent, event.target);
		}
	
		if(r1 <= GameConst.AI_CALCULATE_RANGE || r2 <= GameConst.AI_CALCULATE_RANGE){
			switch (event.type) {
	
				case Event.EVENT_VISUAL_ATTACK:
					eventVisualAttack(loc, event, agent);
					break;
				
				case Event.EVENT_SOUND_ATTACK:
					eventSoundAttack(loc, event, agent);
					break;
				
				case Event.EVENT_DIALOG_BEGIN:
					eventDialogBegin(loc, event, agent);
					break;
				
				case Event.EVENT_DIALOG_END:
					eventDialogEnd(loc, event, agent);
					break;
				
				default:
					break;
			}
		}
	}

	private static void eventVisualAttack(Location loc, Event event, NPC agent) {
		if(event.target.fraction == agent.fraction){
			if(event.source.isCreature()){
				if(AITools.isVisible(loc, agent, event.source)){
					agent.aidata.addEnemy((Creature)event.source);
					agent.aidata.combat = true;
					agent.aidata.fullUpdate = false;
				}
			}
		}
	}

	private static void eventSoundAttack(Location loc, Event event, NPC agent) {
		if(Perception.isHear(agent, AITools.getVolume(loc, agent, event))){
			
		}
	}
	
	private static void eventDialogBegin(Location loc, Event event, NPC agent) {

	}
	
	private static void eventDialogEnd(Location loc, Event event, NPC agent) {

	}
	
	private static void reciveSensorData(Location loc, NPC agent){

	}

	private static void searchPotentialEnemy(Location loc, NPC agent) {
		for(Creature creature: agent.aidata.viewedCreatures.values()){
			if(creature.proto().fraction() != agent.proto().fraction()){
				agent.aidata.addEnemy(creature);
			}
		}
	}

	private static void attack(Location loc, NPC agent) {
		agent.aidata.combat = true;
		
		float minRange = Float.MAX_VALUE;
		Creature nearestEnemy = null;
		
		for(Creature enemy: agent.aidata.viewedEnemy.values()){
			float range = Tools.getRange(agent, enemy);
			
			if(range < minRange){
				minRange = range;
				nearestEnemy = enemy;
			}
		}
		
		if(minRange <= agent.skills().get(0).range){
			// attack
			while(agent.useSkill(loc, agent.skills().get(0), nearestEnemy));
			agent.aidata.softUpdated = true;
		}
		else{
			// follow
			//agent.move(loc, pos.x, pos.y);
			
			if(agent.getPath() == null){
				agent.aidata.softUpdated = true;
			}
		}
	}
	
	private static void moveToWayPoint(Location loc, NPC agent) {
		if(agent.aidata.waypointPause < agent.aidata.waypointPauseMax){
			agent.aidata.waypointPause++;
		}
		else{
			GO wp = agent.aidata.getNextWayPoint();
		
			if(wp == null){
				agent.aidata.softUpdated = true;
			}
			else{
				int waypointPause = agent.aidata.getWayPointPause(wp.getGUID());
				agent.aidata.waypointPause = 0;
				agent.aidata.waypointPauseMax = waypointPause;
			
				if(agent.getPath() == null){
					agent.aidata.softUpdated = true;
				}
			}
		}
	}
}
