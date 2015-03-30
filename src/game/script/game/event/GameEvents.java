package game.script.game.event;

import com.badlogic.gdx.Gdx;

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
		String [] arr = text.split(" ");
		if(arr.length > 0 && arr[0].startsWith("/")){
			// command
			switch (arr[0]) {
				case "/npc":
					npcCommand(arr);
					break;
					
				case "/exit":
					Gdx.app.exit();
					break;
					
				default:
					Log.msg("Invalid command");
					break;
			}
		}
		else{
			Log.msg("Invalid command");
		}
	}

	private static void npcCommand(String [] arr) {
		if(arr.length > 1){
			switch(arr[1]){
				case "wp":
					if(arr.length == 6){
						try {
							int npcGUID = Integer.parseInt(arr[2]);
							int wpGUID = Integer.parseInt(arr[3]);
							int number = Integer.parseInt(arr[4]);
							int pause = Integer.parseInt(arr[5]);
							addNpcWayPoint(npcGUID, wpGUID, number, pause);
						}
						catch(NumberFormatException e){
							Log.err("Invalid parameters");
						}
					}
					else if(arr.length == 5 && arr[2].equals("delete")){
						try {
							int npcGUID = Integer.parseInt(arr[3]);
							int wpNumber = Integer.parseInt(arr[4]);
							npcWayPointDelete(npcGUID, wpNumber);
						}
						catch(NumberFormatException e){
							Log.err("Invalid parameters");
						}
					}
					else if(arr.length == 4 && arr[2].equals("list")){
						try {
							int npcGUID = Integer.parseInt(arr[3]);
							npcWayPointList(npcGUID);
						}
						catch(NumberFormatException e){
							Log.err("Invalid parameters");
						}
					}
					else{
						Log.msg("Invalid parameters in command: /npc wp [npc-guid] [wp-guid] [number] [pause time]");
					}
					break;
				
				default:
					printNpcConsoleCommands();
					break;
			}
		}
		else{
			printNpcConsoleCommands();
		}
	}

	private static void addNpcWayPoint(int npcGUID, int wpGUID, int number, int pause) {
		int result = world.getLocation().addNpcWayPoint(npcGUID, wpGUID, number, pause);
		
		switch (result) {
			case 0:
				Log.msg("Success");
				break;
				
			case 1:
				Log.err("NPC GUID " + npcGUID + " is not exist");
				break;
			
			case 2:
				Log.err("WP GUID " + wpGUID + " is not exist");
				break;
				
			default:
				break;
		}
	}

	private static void npcWayPointList(int npcGUID) {
		int result = world.getLocation().npcWayPointList(npcGUID);
		if(result == 0){
			Log.err("NPC GUID " + npcGUID + " is not exist");
		}
	}
	
	private static void npcWayPointDelete(int npcGUID, int wpNumber) {
		int result = world.getLocation().npcWayPointDelete(npcGUID, wpNumber);
		if(result == 0){
			Log.err("NPC GUID " + npcGUID + " is not exist");
		}
		else{
			Log.msg("WP deleted");
		}
	}
	
	private static void printNpcConsoleCommands() {
		Log.msg("NPC commands:");
		Log.msg("/npc wp [npc-guid] [wp-guid] [wp-number] [pause time]");
		Log.msg("/npc wp list [npc-guid]");
		Log.msg("/npc wp delete [npc-guid] [wp-number]");
	}
}