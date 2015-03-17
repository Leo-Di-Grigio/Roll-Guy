package game.cycle.scene.game.world.creature.ai;

import java.util.HashMap;

import game.cycle.scene.game.world.creature.Creature;

public class AIData {

	public HashMap<Integer, Creature> viewedCreatures;
	public HashMap<Integer, Creature> viewedEnemy;
	public HashMap<Integer, Creature> enemy;
	
	public AIData() {
		viewedCreatures = new HashMap<Integer, Creature>();
		viewedEnemy = new HashMap<Integer, Creature>();
		enemy = new HashMap<Integer, Creature>();
	}

	public void clear() {
		viewedCreatures.clear();
		viewedEnemy.clear();
	}

	public void addEnemy(Creature creature){
		enemy.put(creature.id, creature);
	}
	
	public void addView(Creature creature) {
		viewedCreatures.put(creature.id, creature);
		
		if(enemy.containsKey(creature.id)){
			viewedEnemy.put(creature.id, creature);
		}
	}
}
