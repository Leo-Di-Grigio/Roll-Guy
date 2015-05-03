package game.script.game.event;

import tools.Log;
import game.state.State;
import game.state.database.Database;
import game.state.location.Editor;
import game.state.location.lighting.LocationLighting;

import com.badlogic.gdx.Gdx;

public class GameConsole {
	
	private static State world;

	public GameConsole(State world) {
		GameConsole.world = world;
	}
	
	// Console
	public static void consoleCommand(String text) {
		String [] arr = text.split(" ");
		if(arr.length > 0){
			// command
			switch (arr[0]) {
				case "/location":
					location(arr);
					break;
					
				case "/converter":
					converter(arr);
					break;
					
				case "/npc":
					npcCommand(arr);
					break;
				
				case "/exit":
					Gdx.app.exit();
					break;
				
				case "help":
				case "/help":
					printNpcConsoleCommands();
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
	
	private static void location(String [] arr) {
		if(arr.length == 3){
			if(arr[1].equals("light")){
				if(arr[2].equals("print")){
					LocationLighting.printAreas(world.getLocation());
				}
				else{
					// add global lighting
					try {
						int power = Integer.parseInt(arr[2]);
						power = Math.max(0, power);
						power = Math.min(100, power);
					
						LocationLighting.setEnvironmentLight(world.getLocation(), power);
					}
					catch(NumberFormatException e){
						Log.err("Invalid intensivity param");
					}
				}
			}
		}
		else if(arr.length == 4){
			if(arr[1].equals("light") && arr[2].equals("remove")){
				// remove
				try {
					int index = Integer.parseInt(arr[3]);
					LocationLighting.removeArea(world.getLocation(), index);
				}
				catch(NumberFormatException e){
					Log.err("Invalid param");
				}
			}
		}
		else if(arr.length == 9){
			if(arr[1].equals("light") && arr[2].equals("add")){
				// add area
				try {
					int index = Integer.parseInt(arr[3]);
					int x0 = Integer.parseInt(arr[4]);
					int y0 = Integer.parseInt(arr[5]);
					int x1 = Integer.parseInt(arr[6]);
					int y1 = Integer.parseInt(arr[7]);
					int power = Integer.parseInt(arr[8]);
					LocationLighting.addArea(world.getLocation(), index, x0, y0, x1, y1, power);
					world.getLocation().requestUpdate();
				}
				catch(NumberFormatException e){
					Log.err("Invalid param(s)");
				}
			}
		}
		else{
			Log.msg("/location light print");
			Log.msg("/location light remove [index]");
			Log.msg("/location light add [index] [x0] [y0] [x1] [y1] [power]");
			Log.msg("/location light [intensivity 0..100]");
		}
	}

	private static void converter(String [] arr) {
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
		int result = Editor.npcWayPointAdd(world.getLocation(), npcGUID, wpGUID, number, pause);
		
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
		Log.msg("Image converter:");
		Log.msg("/converter [image.png] [terrainFirst] [terrainSecond]");
		Log.msg("Location commands:");
		Log.msg("/location light print");
		Log.msg("/location light remove [index]");
		Log.msg("/location light add [x0] [y0] [x1] [y1] [power]");
		Log.msg("/location light [intensivity 0..100]");
		Log.msg("App commands:");
		Log.msg("/exit");
	}
}
