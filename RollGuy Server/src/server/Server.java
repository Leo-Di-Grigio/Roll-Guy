package server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.SocketException;

import server.logic.ServerLogic;
import common.tools.Log;

public class Server extends Thread {
	
	private ServerSocket serverSocket;
	private ClientPool clients;
	private MessagePool messages;
	private ServerLogic logic;
	
	public Server(int port) throws IOException, BindException {
		serverSocket = new ServerSocket(port);
		clients = new ClientPool();
		logic = new ServerLogic(clients);
		messages = new MessagePool(logic);
	}
	
	@Override
	public void run() {
		if(serverSocket != null && serverSocket.isBound()){
			Log.debug("Server started");
			
			try {
				while(serverSocket.isBound()){
					Client client = new Client(serverSocket.accept(), messages, clients);
					clients.add(client);
					new Thread(client).start();	
				}
			}
			catch (NullPointerException e){
				Log.msg("Server closed");
			}
			catch (SocketException e){
				Log.msg("Server closed");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void disconnect() {
		try {
			serverSocket.close();
			clients.clear();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isConnected() {
		return serverSocket != null && serverSocket.isBound();
	}
	
	public ServerLogic getLogic(){
		return logic;
	}
}