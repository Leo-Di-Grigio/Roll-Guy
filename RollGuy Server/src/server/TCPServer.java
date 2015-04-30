package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

import common.tools.Log;

public class TCPServer extends Thread {

	private ServerSocket tcpSocket;
	private ClientPool clients;
	private MessagePool messages;

	public TCPServer(ServerSocket tcpSocket, ClientPool clients, MessagePool messages) {
		this.tcpSocket = tcpSocket;
		this.clients = clients;
		this.messages = messages;
	}
	
	@Override
	public void run() {
		if(tcpSocket != null && tcpSocket.isBound()){
			Log.debug("Server started");
			
			try {
				while(tcpSocket.isBound()){
					Client client = new Client(tcpSocket.accept(), messages, clients);
					clients.add(client);	
				}
			}
			catch (NullPointerException e){
				Log.msg("Server closed");
			}
			catch (SocketException e){
				Log.debug("TCP server socket closed");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void disconnect() {
		try {
			tcpSocket.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
