package server.logic;

import common.Version;
import common.net.Message;
import server.ClientPool;

public class ServerLogic {

	private ClientPool clients;
	private State state;

	public ServerLogic(ClientPool clients) {
		this.clients = clients;
	}

	public void message(int id, Message msg) {
		switch (msg.key) {		
			case Message.VERSION_CHECK:
				versionValidation(id, msg);
				break;
				
			default:
				break;
		}
	}
	
	private void versionValidation(int id, Message msg) {
		String [] arr = msg.str.split("\\.");
		
		if(arr.length == 3){
			try{
				int major = Integer.parseInt(arr[0]);
				int minor = Integer.parseInt(arr[1]);
				int revision = Integer.parseInt(arr[2]);
				
				if(major == Version.MAJOR && minor == Version.MINOR && revision == Version.REVISION){
					versionCheckSuñcess(id);
				}
				else{
					versionCheckError(id);
				}
			}
			catch (NumberFormatException e){
				versionCheckError(id);
			}
		}
		else{
			versionCheckError(id);
		}
	}
	
	private void versionCheckSuñcess(int id) {
		clients.send(id, new Message(Message.VERSION_CHECK_SUCCESS, ""));
	}

	private void versionCheckError(int id) {
		clients.send(id, new Message(Message.VERSION_CHECK_ERROR, ""));
	}

	public State getState() {
		return state;
	}

	public void loadState() {
		state = new State(clients);
	}
}
