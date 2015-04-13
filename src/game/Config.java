package game;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Config {

	public static int frameWidth = 1366;
	public static int frameHight = 768;
	
	public static boolean frameResizeble = false;
	public static boolean fullscreen = false;
	
	public static boolean javaDebug = true;
	public static boolean logDebug = true;
	public static boolean logErrors = true;
	public static boolean logLuaErrors = true;
	
	public Config() {
		try {
			File file = new File("config.cfg");
		
			if(!file.exists()){
				file.createNewFile();
				PrintWriter out = new PrintWriter(file);
				
				out.println("# display settings");
				out.println("frame-witdh: " + 1366);
				out.println("frame-hight: " + 768);
				out.println("fullscreen: false");
				out.println("");
				out.println("# log settings");
				out.println("java-debug: true");
				out.println("debug: true");
				out.println("errors: true");
				out.println("lua-errors: true");
				
				out.flush();
				out.close();
			}
			else{
				Scanner in = new Scanner(file);
				
				while(in.hasNextLine()){
					String line = in.nextLine();
					
					if(!line.startsWith("#")){
						String [] arr = line.split(":");
						
						if(arr.length == 2){
							arr[1] = arr[1].replaceAll("\\s+","");
						
							switch(arr[0]){
								case "frame-witdh":
									frameWidth = Integer.parseInt(arr[1]);
									break;
								
								case "frame-hight":
									frameHight = Integer.parseInt(arr[1]);
									break;
								
								case "fullscreen":
									fullscreen = Boolean.parseBoolean(arr[1]);
									break;
								
								case "java-debug":
									javaDebug = Boolean.parseBoolean(arr[1]);
									break;
									
								case "debug":
									logDebug = Boolean.parseBoolean(arr[1]);
									break;
								
								case "errors":
									logErrors = Boolean.parseBoolean(arr[1]);
									break;
									
								case "lua-errors:":
									logLuaErrors = Boolean.parseBoolean(arr[1]);
									break;
							}
						}
					}
				}
				
				in.close();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
