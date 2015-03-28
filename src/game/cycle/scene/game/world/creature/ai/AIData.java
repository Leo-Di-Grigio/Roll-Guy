package game.cycle.scene.game.world.creature.ai;

import java.util.HashMap;

import game.cycle.scene.game.world.creature.Creature;

public class AIData {

	// update
	public boolean executed;
	public boolean updated;
	
	// status
	public boolean combat;
	
	// sensor data
	public HashMap<Integer, Creature> viewedCreatures;
	public HashMap<Integer, Creature> viewedEnemy;

	// personal data
	public HashMap<Integer, Creature> enemy;
	
	public AIData() {
		viewedCreatures = new HashMap<Integer, Creature>();
		viewedEnemy = new HashMap<Integer, Creature>();
		enemy = new HashMap<Integer, Creature>();
	}

	public void reset() {
		updated = false;
		executed = false;
	}
	
	public void clear() {
		viewedCreatures.clear();
		viewedEnemy.clear();
	}

	public void addEnemy(Creature creature){
		enemy.put(creature.getGUID(), creature);
	}
	
	public void addViewedEnemy(Creature creature) {
		viewedCreatures.put(creature.getGUID(), creature);
		
		if(enemy.containsKey(creature.getGUID())){
			viewedEnemy.put(creature.getGUID(), creature);
		}
	}
}
