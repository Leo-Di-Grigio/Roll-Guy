package game.cycle.scene.game.world.creature.ai;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.game.world.go.GO;
import game.tools.Const;
import game.tools.Log;

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
	
	// waypoints
	public int waypointPause = Const.invalidId;
	public int waypointPauseMax = Const.invalidId;
	private TreeMap<Integer, GO> waypoints;
	private TreeMap<Integer, Integer> waypointsPause;
	private Iterator<Integer> waypointsIter; 
	
	public AIData() {
		viewedCreatures = new HashMap<Integer, Creature>();
		viewedEnemy = new HashMap<Integer, Creature>();
		enemy = new HashMap<Integer, Creature>();

		// wp
		waypoints = new TreeMap<Integer, GO>();
		waypointsIter = waypoints.keySet().iterator();
		waypointsPause = new TreeMap<Integer, Integer>();
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

	// WayPoints
	public GO getWayPoint(int number){
		return waypoints.get(number);
	}
	
	public GO getNextWayPoint(){
		if(waypointsIter.hasNext()){
			return waypoints.get(waypointsIter.next());
		}
		else{
			waypointsIter = waypoints.keySet().iterator();
			
			if(waypoints.size() > 0){
				return getNextWayPoint();
			}
			else{
				return null;
			}
		}
	}
	
	public int getWayPointPause(int goGUID){
		return waypointsPause.get(goGUID);
	}
	
	public void addWayPoint(GO go, int number, int pause) {
		waypoints.put(number, go);
		waypointsPause.put(go.getGUID(), pause);
		waypointsIter = waypoints.keySet().iterator();
	}
	
	public void removeWayPoint(int number){
		waypoints.remove(number);
		waypointsPause.remove(number);
		waypointsIter = waypoints.keySet().iterator();
	}

	public void printWayPoints() {
		if(waypoints.size() == 0){
			Log.msg("Empty");
		}
		else{
			Set<Integer> keys = waypoints.keySet();
			for(Integer key: keys){
				GO wp = waypoints.get(key);
				Log.msg("" + key + ": " + wp.getGUID() + " - pause " + getWayPointPause(wp.getGUID()));
			}
		}
	}
}
