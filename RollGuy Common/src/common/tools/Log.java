package common.tools;

public class Log {

	public synchronized static void msg(String text){
		System.out.println(text);
	}
	
	public synchronized static void debug(String text){
		System.out.println(text);
	}
	
	public synchronized static void err(String text){
		System.err.println(text);
	}

	public static void serverWarining(String text) {
		System.out.println(text);
	}
}
