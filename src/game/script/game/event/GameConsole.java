package game.script.game.event;

import game.cycle.scene.game.world.World;
import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.location.Editor;
import game.tools.Log;

import com.badlogic.gdx.Gdx;

public class GameConsole {
	
	private static World world;

	public GameConsole(World world) {
		GameConsole.world = world;
	}
	
	// Console
	public static void consoleCommand(String text) {
		String [] arr = text.split(" ");
		if(arr.length > 0 && arr[0].startsWith("/")){
			// command
			switch (arr[0]) {
				case "/converter":
					converter(arr);
					break;
					
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
	
	private static void converter(String[] arr) {
		if(arr.length == 4){
			String file = arr[1];
			
			try {
				int texFirst = Integer.parseInt(arr[2]);
				int texSecond = Integer.parseInt(arr[3]);
				
				if(Database.getTerrainProto(texFirst) != null && Database.getTerrainProto(texSecond) != null){
					Editor.imageScreenLoader(world.getLocation(), file, texFirst, texSecond);
				}
			}
			catch(NumberFormatException e){
				Log.err("Invalid texture parameters");
			}
		}
		else{
			Log.msg("/converter [image.png] [terrainFirst] [terrainSecond]");
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
		int result = Editor.addNpcWayPoint(world.getLocation(), npcGUID, wpGUID, number, pause);
		
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
		int result = Editor.npcWayPointList(world.getLocation(), npcGUID);
		if(result == 0){
			Log.err("NPC GUID " + npcGUID + " is not exist");
		}
	}
	
	private static void npcWayPointDelete(int npcGUID, int wpNumber) {
		int result = Editor.npcWayPointDelete(world.getLocation(), npcGUID, wpNumber);
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
