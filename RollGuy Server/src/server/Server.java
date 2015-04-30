package server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;

import server.logic.ServerLogic;

public class Server extends Thread {
	
	private ServerSocket tcpSocket;
	private ClientPool clients;
	private MessagePool messages;
	private ServerLogic logic;
	
	private TCPServer tcpServer;
	private Thread tcpThread;
	
	public Server(int tcpPort) throws IOException, BindException {
		tcpSocket = new ServerSocket(tcpPort);
		
		clients = new ClientPool();
		messages = new MessagePool();
		tcpServer = new TCPServer(tcpSocket, clients, messages);
		logic = new ServerLogic(clients, messages);
		
		new Thread(logic).start();
	}
	
	@Override
	public void run() {
		tcpThread = new Thread(tcpServer);
		tcpThread.start();
	}
	
	public synchronized void disconnect() {
		tcpServer.disconnect();
		clients.clear();
	}

	public synchronized boolean isConnected() {
		return tcpSocket != null && tcpSocket.isBound();
	}
	
	public synchronized ServerLogic getLogic(){
		return logic;
	}
}