package game.tools;

import game.Config;

public class Log {

	public synchronized static void msg(String text){
		System.out.println(text);
	}
	
	public synchronized static void debug(String text){
		if(Config.logDebug){
			System.out.println("DEBUG: " + text);
		}
	}
	
	public synchronized static void err(String text){
		if(Config.logErrors){
			System.err.println("ERR: " + text);
		}
	}
}
