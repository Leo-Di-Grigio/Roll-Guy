package server;

import java.util.LinkedList;

import common.net.Message;

public class MessagePool {

	private LinkedList<Message> array;
	
	public MessagePool() {
		array = new LinkedList<Message>();
	}

	public synchronized void add(int id, Message msg) {
		msg.clientId = id;
		array.add(msg);
	}

	public synchronized boolean hasNext(){
		return !array.isEmpty();
	}
	public synchronized Message poll(){
		return array.poll();
	}
}
