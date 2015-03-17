package game.script.game.go;

import com.badlogic.gdx.graphics.g2d.Sprite;

import game.cycle.scene.game.world.LocationObject;
import game.cycle.scene.game.world.go.GO;
import game.resources.Resources;
import game.resources.Tex;
import game.script.ScriptGame;

public class go_DoorUse implements ScriptGame {

	private GO go;
	private boolean doorOpen; // false = close, true = open
	
	private Sprite spriteDoorOpen;
	private Sprite spriteDoorClosed;
	
	public go_DoorUse(GO go) {
		this.go = go; 
		
		this.spriteDoorOpen = new Sprite(Resources.getTex(Tex.go + go.proto.texure_1));
		this.spriteDoorOpen.setPosition(go.getSpriteX(), go.getSpriteY());
		
		this.spriteDoorClosed = new Sprite(Resources.getTex(Tex.go + go.proto.texure_2));
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
		
		if(doorOpen){
			go.setSprite(spriteDoorOpen);
		}
		else{
			go.setSprite(spriteDoorClosed);
		}
		
		return doorOpen;
	}
}