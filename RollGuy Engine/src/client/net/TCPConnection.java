package client.net;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import client.Config;
import common.net.Message;
import common.tools.Log;

class TCPConnection extends Thread {

	private Network net;	
	private Socket tcpSocket;
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public TCPConnection(Network net) throws UnknownHostException, IOException, ConnectException {
		this.net = net;
		this.tcpSocket = new Socket(InetAddress.getByName(Config.server), Config.tcpPort);
		this.in = new ObjectInputStream(tcpSocket.getInputStream());
		this.out = new ObjectOutputStream(tcpSocket.getOutputStream());
	}
	
	@Override
	public void run() {
		if(isConnected()){
			try {
				Log.msg("Connection sucess " + tcpSocket.getInetAddress());
				
				while(tcpSocket.isBound()){
					Object obj = in.readObject();
					
					if(obj == null){
						Log.err("Message is broken");
					}
					else{
						net.read((Message)obj);
					}
				}
			}
			catch (SocketException e){
				Log.msg("Disconnected by server");
			}
			catch (EOFException e){
				
			}
			catch (ClassNotFoundException e){
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			Log.err("Connection error");
		}
	}

	public void send(Message msg) {
		if(msg != null){
			try {
				Log.msg("<- " + msg.timestamp + ":" + msg.key + ":" + msg.str);
				out.writeObject(msg);
			} 
			catch (SocketException e){
				e.printStackTrace();
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
	
	public boolean isConnected(){
		return (tcpSocket != null && tcpSocket.isBound());
	}
}
