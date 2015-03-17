package game.script.game.event;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.world.LocationObject;
import game.cycle.scene.game.world.World;
import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.go.GO;
import game.cycle.scene.game.world.map.LocationProto;

public class GameEvents {

	private static SceneGame game;
	private static World world;
	
	public GameEvents(SceneGame scene){
		GameEvents.game = scene;
		GameEvents.world = scene.getWorld();
	}
	
	public static void teleport(GO go, LocationObject user) {
		int mapId = go.param1;
		int x = go.param2;
		int y = go.param3;
		
		LocationProto location = Database.getLocation(mapId);
		user.resetMovement();
		if(location != null){
			game.loadLocation(mapId, x, y);
		}
	}
	
	public static void gameModeTurnBased(boolean playerTurn){
		game.gameModeTurnBased(playerTurn);
	}
	
	public static void gameModeRealTime(){
		game.gameModeRealTime();
	}

	public static void nextTurn() {
		game.nextTurn();
	}
	
	public static void destroyed(LocationObject object){
		world.destroy(object);
	}
}