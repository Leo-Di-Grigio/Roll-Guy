package server;

import java.util.HashMap;
import java.util.Set;

import common.net.Message;

public class ClientPool {

	private HashMap<Integer, Client> clients;
	
	public ClientPool() {
		clients = new HashMap<Integer, Client>();
	}
	
	public void add(Client client){
		clients.put(client.id, client);
	}

	public Client remove(int id) {
		return clients.remove(id);
	}
	
	public void clear(){
		Set<Integer> keys = clients.keySet();
		for(Integer key: keys){
			Client client = clients.remove(key);
			client.disconnect();
		}
		
		Client.resetId();
	}
	
	public void send(int id, Message msg){
		Client client = clients.get(id);
		
		if(client != null){
			client.send(msg);
		}
	}
	
	public void sendToAll(Message msg){
		for(Client client: clients.values()){
			client.send(msg);
		}
	}
}
