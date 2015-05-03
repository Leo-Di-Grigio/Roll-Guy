package game.state.location.creature.ai;

import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import tools.Const;
import tools.Log;
import game.state.location.Location;
import game.state.location.LocationObject;
import game.state.location.creature.Creature;
import game.state.location.go.GO;

public class AIData {

	// update
	public boolean fullUpdate;
	public boolean softUpdated;
	
	// status
	public boolean combat;
	public boolean foundCorpse; 
	
	// sensor data
	public HashMap<Integer, Creature> viewedCreatures; // <guid, creature>
	public HashMap<Integer, Creature> viewedEnemy;     // <guid, creature>

	// personal data
	public HashMap<Integer, Creature> enemy;
	public Point pointOfInteres; 
	
	// waypoints
	public int waypointPause = Const.INVALID_ID;
	public int waypointPauseMax = Const.INVALID_ID;
	public float waypointPauseSec = Const.INVALID_ID;
	public float waypointPauseSecMax = Const.INVALID_ID;
	
	private TreeMap<Integer, GO> waypoints;
	private TreeMap<Integer, Integer> waypointsPause;
	private Iterator<Integer> waypointsIter;
	
	public AIData() {
		pointOfInteres = new Point(Const.INVALID_ID, Const.INVALID_ID);
		
		viewedCreatures = new HashMap<Integer, Creature>();
		viewedEnemy = new HashMap<Integer, Creature>();
		enemy = new HashMap<Integer, Creature>();

		// wp
		waypoints = new TreeMap<Integer, GO>();
		waypointsIter = waypoints.keySet().iterator();
		waypointsPause = new TreeMap<Integer, Integer>();
	}

	public void reset() {
		softUpdated = false;
		fullUpdate = false;
		foundCorpse = false;
	}
	
	public void clear() {
		viewedCreatures.clear();
		viewedEnemy.clear();
		combat = false;
	}

	public void addEnemy(Creature creature){
		if(creature.isAlive()){
			enemy.put(creature.getGUID(), creature);
		
			if(viewedCreatures.containsKey(creature.getGUID())){
				viewedEnemy.put(creature.getGUID(), creature);
			}
		}
	}

	public boolean checkEnemyList(LocationObject source) {
		return enemy.containsKey(source.getGUID());
	}
	
	public void addViewedCreature(Creature creature) {
		if(creature.isAlive()){
			viewedCreatures.put(creature.getGUID(), creature);
		
			if(enemy.containsKey(creature.getGUID())){
				viewedEnemy.put(creature.getGUID(), creature);
			}	
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

	public void clearWayPoints(){
		waypoints.clear();
		waypointsPause.clear();
		waypointsIter = null;
	}
	
	public void addPointOfInteres(int x, int y){
		pointOfInteres.setLocation(x, y);
	}
	
	public void clearPointOfInteres(){
		pointOfInteres.setLocation(Const.INVALID_ID, Const.INVALID_ID);
	}
	
	public boolean hasPointOfInteres(){
		return pointOfInteres.x != Const.INVALID_ID && pointOfInteres.y != Const.INVALID_ID;
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
	
	public int getWayPointsIntArraySize(){
		return waypoints.keySet().size() * 3;
	}

	public int [] getWayPointsIntArray(){
		int [] arr = new int[getWayPointsIntArraySize()]; 
		
		int i = 0;
		Set<Integer> numbers = waypoints.keySet();
		for(Integer number: numbers){
			GO wp = waypoints.get(number);
			arr[i] = wp.getGUID();
			arr[i+1] = number;
			arr[i+2] = getWayPointPause(wp.getGUID());
			i += 3;
		}
		
		return arr;
	}
	
	public void setWayPointsIntArray(Location loc, int [] arr) {
		for(int i = 0; i < arr.length; i += 3){
			int guid = arr[i];
			int number = arr[i+1];
			int pause = arr[i+2];
			
			addWayPoint(loc.getGO(guid), number, pause);
		}
	}
}
