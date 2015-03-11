package game.tools;

public class Log {

	public synchronized static void msg(String text){
		System.out.println(text);
	}
	
	public synchronized static void debug(String text){
		System.out.println("DEBUG: " + text);
	}
	
	public synchronized static void err(String text){
		System.err.println("ERR: " + text);
	}
}
