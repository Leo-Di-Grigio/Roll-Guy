package client.net;

import java.io.IOException;
import java.net.BindException;
import java.net.UnknownHostException;

import server.Server;
import common.Version;
import common.net.Message;
import common.tools.Log;
import client.Config;
import client.scenes.SceneMng;
import client.scenes.list.SceneMenuConnect;
import client.scenes.list.SceneMenuServer;

public class Network {
	
	private Server server;
	private Connection connect;
	private ClientLogic logic;
	
	public void runServer() {
		if(server == null){
			try {
				server = new Server(Config.port);
				new Thread(server).start();
				
				showServerMenu();
				runConnection();
			} 
			catch (BindException e) {
				Log.err("Port " + Config.port + " already binded");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			Log.err("Server already runned");
		}
	}
	
	public void runConnection() {
		if(connect == null){
			try {
				connect = new Connection(this, Config.server, Config.port);
				new Thread(connect).start();
				Log.msg("Connection created");
				this.logic = new ClientLogic(this);
				
				send(new Message(Message.VERSION_CHECK, "" + Version.MAJOR + "." + Version.MINOR + "." + Version.REVISION));
			} 
			catch (UnknownHostException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			Log.msg("Already connected");
		}
	}
	
	public void closeServer() {
		if(connect != null){
			connect.disconnect();
			connect = null;
		}
		
		server.disconnect();
		server = null;
		
		showMainMenu();
	}
	
	public void closeConnection(){
		if(connect != null){
			connect.disconnect();
			connect = null;
		}
		
		showMainMenu();
	}
	
	public void read(Message msg){
		Log.msg("->: " + msg.timestamp + ":" + msg.key + ":" + msg.str);
		logic.read(msg);
	}
	
	public void send(Message msg){
		connect.send(msg);
	}
	
	public void showMainMenu(){
		SceneMng.switchScene(SceneMng.SCENE_MENU);
		SceneMng.remove(SceneMng.SCENE_MENU_SERVER);
	}
	
	public void showConnectionMenu(){
		if(!SceneMng.getCurrentTitle().contains(SceneMng.SCENE_MENU_SERVER)){
			SceneMng.addScene(SceneMng.SCENE_MENU_CONNECT, new SceneMenuConnect(SceneMng.SCENE_MENU_CONNECT, this));
			SceneMng.switchScene(SceneMng.SCENE_MENU_CONNECT);
		}
	}
	
	private void showServerMenu(){
		SceneMng.addScene(SceneMng.SCENE_MENU_SERVER, new SceneMenuServer(SceneMng.SCENE_MENU_SERVER, this, server));
		SceneMng.switchScene(SceneMng.SCENE_MENU_SERVER);
	}
}
