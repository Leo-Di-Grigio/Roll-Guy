package server;

import java.net.InetAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import common.net.Message;

public class ClientPool {

	private HashMap<Integer, Client> clients;
	private HashMap<InetAddress, Integer> clientsAddress;
	
	public ClientPool() {
		clients = new HashMap<Integer, Client>();
		clientsAddress = new HashMap<InetAddress, Integer>();
	}
	
	public void add(Client client){
		clients.put(client.id, client);
		clientsAddress.put(client.address(), client.id);
		new Thread(client).start();
	}

	public Client remove(int id) {
		return clients.remove(id);
	}
	
	public void clear(){
		Set<Integer> keys = clients.keySet();
		for(Integer key: keys){
			clients.get(key).disconnect();
		}
		
		clients.clear();
		
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

	public void sendToAlmostAll(int id, Message msg) {
		for(Client client: clients.values()){
			if(client.id != id){
				client.send(msg);
			}
		}
	}

	public Collection<Client> value() {
		return clients.values();
	}
}
