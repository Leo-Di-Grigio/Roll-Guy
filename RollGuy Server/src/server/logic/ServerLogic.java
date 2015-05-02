package server.logic;

import common.Const;
import common.Version;
import common.net.Message;
import server.ClientPool;
import server.MessagePool;

public class ServerLogic extends Thread {

	private ClientPool clients;
	private MessagePool messages;
	private ServerState state;

	public ServerLogic(ClientPool clients, MessagePool messages) {
		this.messages = messages;
		this.clients = clients;
	}
		
	// update cycle
	protected long cycleTime;
	protected long LastTime = System.currentTimeMillis();
	protected long elapsedTime = 0;
	protected long LastUpdate = 0;
	
	@Override
	public void run() {
		while(true){
			synchUpdateRate();
			update();
		}
	}
	
	private void update() {		
		while(messages.hasNext()){
			execute(messages.poll());
		}
	}

	private void synchUpdateRate() {
		cycleTime = cycleTime + Const.SERVER_UPDATE_RATE;
		long difference = cycleTime - System.currentTimeMillis();
		try {
			Thread.sleep(Math.max(0, difference));
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		elapsedTime = System.currentTimeMillis() - LastTime;
	}

	
	private void execute(Message msg) {
		switch (msg.key) {
			case Message.CLIENT_PLAYER_MOVE:
				playerMove(msg);
				break;
				
			case Message.CLIENT_VERSION_CHECK:
				versionValidation(msg);
				break;
				
			default:
				break;
		}
	}
	
	private void versionValidation(Message msg) {
		String [] arr = msg.str.split("\\.");
		
		if(arr.length == 3){
			try{
				int major = Integer.parseInt(arr[0]);
				int minor = Integer.parseInt(arr[1]);
				int revision = Integer.parseInt(arr[2]);
				
				if(major == Version.MAJOR && minor == Version.MINOR && revision == Version.REVISION){
					versionCheckSuñcess(msg.clientId);
				}
				else{
					versionCheckError(msg.clientId);
				}
			}
			catch (NumberFormatException e){
				versionCheckError(msg.clientId);
			}
		}
		else{
			versionCheckError(msg.clientId);
		}
	}
	
	private void versionCheckSuñcess(int id) {
		clients.send(id, new Message(Message.SERVER_VERSION_CHECK_SUCCESS));
		
		if(state != null){
			state.addPlayer(id);
			clients.send(id, new Message(Message.SERVER_LOAD_GAME));
		}
	}

	private void versionCheckError(int id) {
		clients.send(id, new Message(Message.SERVER_VERSION_CHECK_ERROR));
	}

	private void playerMove(Message msg) {
		state.playerMove(msg);
	}
	
	public ServerState getGameState() {
		return state;
	}

	public void loadState() {
		state = new ServerState(clients);
	}
}
