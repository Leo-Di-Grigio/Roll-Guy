package client;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import client.input.KeyBinds;

import com.badlogic.gdx.Input.Keys;

public class Config {
	
	public static int frameWidth = 1366;
	public static int frameHight = 768;
	
	public static boolean frameResizeble = false;
	public static boolean fullscreen = false;
	
	public static boolean javaDebug = true;
	public static boolean logDebug = true;
	public static boolean logErrors = true;
	public static boolean logLuaErrors = true;
	
	public static int tcpPort = 6666;
	public static int udpPort = 6666;
	public static String server = "leodigrigio.ddns.net";
	
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
				out.println("#network settings");
				out.println("server: leodigrigio.ddns.net");
				out.println("tcp-port: 6666");
				out.println("");
				out.println("# log settings");
				out.println("java-debug: true");
				out.println("debug: true");
				out.println("errors: true");
				out.println("lua-errors: true");
				out.println("");
				out.println("# key binds");
				out.println("key-action-bar-1: 1");
				out.println("key-action-bar-2: 2");
				out.println("key-action-bar-3: 3");
				out.println("key-action-bar-4: 4");
				out.println("key-action-bar-5: 5");
				out.println("key-action-bar-6: 6");
				out.println("key-action-bar-7: 7");
				out.println("key-action-bar-8: 8");
				out.println("key-action-bar-9: 9");
				out.println("key-action-bar-10: 0");
				out.println("key-action-bar-11: -");
				out.println("key-action-bar-12: =");
				out.println("key-inventory: I");
				
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
								
								case "server":
									server = arr[1];
									break;
									
								case "tcp-port":
									tcpPort = Integer.parseInt(arr[1]);
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
									
								case "key-action-bar-1":
									KeyBinds.keyActionBar0 = Keys.valueOf(arr[1]);
									break;
									
								case "key-action-bar-2":
									KeyBinds.keyActionBar1 = Keys.valueOf(arr[1]);
									break;
									
								case "key-action-bar-3":
									KeyBinds.keyActionBar2 = Keys.valueOf(arr[1]);
									break;
									
								case "key-action-bar-4":
									KeyBinds.keyActionBar3 = Keys.valueOf(arr[1]);
									break;
									
								case "key-action-bar-5":
									KeyBinds.keyActionBar4 = Keys.valueOf(arr[1]);
									break;
									
								case "key-action-bar-6":
									KeyBinds.keyActionBar5 = Keys.valueOf(arr[1]);
									break;
									
								case "key-action-bar-7":
									KeyBinds.keyActionBar6 = Keys.valueOf(arr[1]);
									break;
									
								case "key-action-bar-8":
									KeyBinds.keyActionBar7 = Keys.valueOf(arr[1]);
									break;
									
								case "key-action-bar-9":
									KeyBinds.keyActionBar8 = Keys.valueOf(arr[1]);
									break;
									
								case "key-action-bar-10":
									KeyBinds.keyActionBar9 = Keys.valueOf(arr[1]);
									break;
									
								case "key-action-bar-11":
									KeyBinds.keyActionBar10 = Keys.valueOf(arr[1]);
									break;
									
								case "key-action-bar-12":
									KeyBinds.keyActionBar11 = Keys.valueOf(arr[1]);
									break;
									
								case "key-inventory":
									KeyBinds.inventory = Keys.valueOf(arr[1]);
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
