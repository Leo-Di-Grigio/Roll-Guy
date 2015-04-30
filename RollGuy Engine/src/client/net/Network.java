package client.net;

import java.io.IOException;
import java.net.BindException;
import java.net.ConnectException;
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
	private TCPConnection tcpConnect;
	private ClientLogic logic;
	
	public void runServer() {
		if(server == null){
			try {
				server = new Server(Config.tcpPort);
				new Thread(server).start();
				
				showServerMenu();
				runConnection();
			} 
			catch (BindException e) {
				Log.err("Port " + Config.tcpPort + " already binded");
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
		if(tcpConnect == null){
			try {
				tcpConnect = new TCPConnection(this);
				new Thread(tcpConnect).start();
			
				logic = new ClientLogic(this);
				
				send(new Message(Message.CLIENT_VERSION_CHECK, "" + Version.MAJOR + "." + Version.MINOR + "." + Version.REVISION));
				Log.msg("Connection created");
			}
			catch (ConnectException e){
				Log.err("Server " + Config.server + " does not found");
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
		if(tcpConnect != null){
			tcpConnect.disconnect();
			tcpConnect = null;
		}
		
		if(server != null){
			server.disconnect();
			server = null;
		}
		
		showMainMenu();
	}
	
	public void closeConnection(){
		if(tcpConnect != null){
			tcpConnect.disconnect();
			tcpConnect = null;
		}
		
		showMainMenu();
	}
	
	public void read(Message msg){
		Log.msg("->: " + msg.timestamp + ":" + msg.key + ":" + msg.str);
		logic.readTCP(msg);
	}
	
	public void send(Message msg){
		tcpConnect.send(msg);
	}
	
	public void showMainMenu(){
		if(server != null){
			closeServer();
		}
		if(tcpConnect != null){
			closeConnection();
		}
		
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
