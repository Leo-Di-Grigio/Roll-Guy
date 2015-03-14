package game.script.game.event;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.go.GO;
import game.cycle.scene.game.world.map.LocationProto;

public class GameEvents {

	private static SceneGame game;
	
	public GameEvents(SceneGame scene){
		game = scene;
	}
	
	public static void teleport(GO go) {
		int mapId = go.param1;
		int x = go.param2;
		int y = go.param3;
		
		LocationProto location = Database.getLocation(mapId);
		if(location != null){
			if(!game.getWorld().getPlayer().isMoved){
				game.loadLocation(mapId, x, y);
			}
		}
	}
}