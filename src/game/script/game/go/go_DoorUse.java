package game.script.game.go;

import com.badlogic.gdx.graphics.g2d.Sprite;

import game.cycle.scene.game.world.location.LocationObject;
import game.cycle.scene.game.world.location.creature.Player;
import game.cycle.scene.game.world.location.go.GO;
import game.resources.Resources;
import game.resources.tex.Tex;
import game.script.ScriptGame;

public class go_DoorUse implements ScriptGame {

	private GO go;
	private boolean doorOpen; // false = close, true = open
	
	private Sprite spriteDoorOpen;
	private Sprite spriteDoorClosed;
	
	public go_DoorUse(GO go) {
		this.go = go; 
		
		this.spriteDoorOpen = new Sprite(Resources.getTex(Tex.GO_NULL + go.proto.tex1()));
		this.spriteDoorOpen.setPosition(go.getSpriteX(), go.getSpriteY());
		
		this.spriteDoorClosed = new Sprite(Resources.getTex(Tex.GO_NULL + go.proto.tex2()));
		this.spriteDoorClosed.setPosition(go.getSpriteX(), go.getSpriteY());
		
		// closed
		this.doorOpen = false;
		this.go.passable = false;
		this.go.setSprite(spriteDoorClosed);
	}
	
	@Override
	public boolean execute(LocationObject user) {
		doorOpen = !doorOpen;
		this.go.passable = doorOpen;
		this.go.losBlock = !doorOpen;
		
		if(doorOpen){
			go.setSprite(spriteDoorOpen);
		}
		else{
			go.setSprite(spriteDoorClosed);
		}
		
		if(user.isPlayer()){
			Player player = (Player)user;
			player.updateLOS();
		}
		
		return doorOpen;
	}
}