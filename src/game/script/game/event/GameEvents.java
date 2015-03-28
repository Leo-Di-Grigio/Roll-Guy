package game.script.game.event;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.world.LocationObject;
import game.cycle.scene.game.world.World;
import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.event.LocationEvent;
import game.cycle.scene.game.world.go.GO;
import game.cycle.scene.game.world.map.LocationProto;
import game.cycle.scene.game.world.skill.Skill;
import game.cycle.scene.ui.list.UIGame;
import game.tools.Const;
import game.tools.Log;

public class GameEvents {

	private static SceneGame game;
	private static World world;
	private static UIGame ui;
	
	public GameEvents(SceneGame scene, UIGame ui){
		GameEvents.game = scene;
		GameEvents.world = scene.getWorld();
		GameEvents.ui = ui;
	}
	
	public static void teleport(GO go, LocationObject user) {
		int mapId = go.param1;
		int x = go.param2;
		int y = go.param3;
		
		LocationProto location = Database.getLocation(mapId);
		user.resetPath();
		if(location != null){
			game.loadLocation(mapId, x, y);
		}
	}

	// TURN SYSTEM
	public static void gameModeTurnBased(boolean playerTurn){
		ui.turnBased(playerTurn);
		world.gameModeTurnBased(playerTurn);
	}
	
	public static void gameModeRealTime(){
		ui.turnBased(false);
		world.gameModeRealTime();
	}
	
	public static void endTurn() {
		if(world.endTurn()){
			ui.turnBased(true);
		}
	}
	
	public static void nextTurn() {
		ui.turnBased(true);
		ui.setMode(Const.invalidId);
		world.resetPlayer();
		world.resetPlayerSkill();
	}
	
	// INTERACTIVE
	public static void useSkillSelfTarget(Creature target, Skill skill) {
		world.selfcastSkill(target, skill);
	}
	
	// AI
	public static void addLocationEvent(LocationEvent event) {
		world.addLocationEvent(event);
	}
	
	// MISC
	public static void destroyed(LocationObject object){
		world.destroy(object);
	}

	// Console
	public static void consoleCommand(String text) {
		Log.msg(text);
	}
}