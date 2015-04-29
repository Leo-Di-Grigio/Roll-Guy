package client;

public class Config {
	
	public static int frameWidth = 1366;
	public static int frameHight = 768;
	
	public static boolean frameResizeble = false;
	public static boolean fullscreen = false;
	
	public static int port;
	public static String server;
	
	public Config() {
		port = 6666;
		server = "leodigrigio.ddns.net";
	}
}
