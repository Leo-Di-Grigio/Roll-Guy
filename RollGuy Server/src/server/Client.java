package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import common.net.Message;
import common.tools.Log;

public class Client extends Thread {

	private static int ID = 0;
	public final int id = ID++;
	
	private MessagePool messages;
	private ClientPool clients;
	
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	public Client(Socket socket, MessagePool messages, ClientPool clients) throws IOException {
		this.messages = messages;
		this.clients = clients;
		
		this.socket = socket;
		this.out = new ObjectOutputStream(socket.getOutputStream());
		this.in = new ObjectInputStream(socket.getInputStream());
	}
	
	protected static void resetId(){
		Client.ID = 0;
	}
	
	@Override
	public void run() {
		if(isConnected()){
			try {		
				Log.debug("Clinet ID:" + id + ":" + socket.getInetAddress() + ":" + socket.getPort());
				while(socket.isBound()){
					Object obj = in.readObject();
					
					if(obj == null){
						Log.err("Message reciving error");
					}
					else{
						messages.read(id, (Message)obj);
					}
				}
			}
			catch (SocketException e){
				Log.msg("Client ID:" + id + " disconnected");
			}
			catch (EOFException e){
				Log.msg("Disconnecting..");
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			finally{
				clients.remove(id);
			}
		}
		else{
			Log.err("No connection avaible");
		}
	}
	
	public void disconnect(){
		try {
			socket.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean isConnected() {
		return (socket != null && socket.isBound());
	}

	public void send(Message msg){
		if(msg != null){
			try {
				Log.msg("->: " + msg.timestamp + ":" + msg.key + ":" + msg.str);
				out.writeObject(msg);
				out.flush();
			}
			catch (SocketException e){
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
