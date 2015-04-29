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

import common.net.Message;
import common.tools.Log;

public class Connection extends Thread {

	private Network net;
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public Connection(Network net, String ip, int port) throws UnknownHostException, IOException, ConnectException {
		this.net = net;
		this.socket = new Socket(InetAddress.getByName(ip), port);
		this.in = new ObjectInputStream(socket.getInputStream());
		this.out = new ObjectOutputStream(socket.getOutputStream());
	}
	
	@Override
	public void run() {
		if(isConnected()){
			try {
				Log.msg("Connection sucess " + socket.getInetAddress());
				
				while(socket.isBound()){
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
			socket.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isConnected(){
		return (socket != null && socket.isBound());
	}
}
