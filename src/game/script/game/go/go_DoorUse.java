package game.script.game.go;

import com.badlogic.gdx.graphics.g2d.Sprite;

import game.cycle.scene.game.world.go.GO;
import game.resources.Resources;
import game.resources.Tex;
import game.script.Script;

public class go_DoorUse implements Script {

	private GO go;
	private boolean doorStatus; // false = close, true = open
	
	private Sprite spriteDoorOpen;
	private Sprite spriteDoorClosed;
	
	public go_DoorUse(GO go) {
		this.go = go; 
		
		this.spriteDoorOpen = new Sprite(Resources.getTex(Tex.go + go.proto.texure_1));
		this.spriteDoorOpen.setPosition(go.sprite.getX(), go.sprite.getY());
		
		this.spriteDoorClosed = new Sprite(Resources.getTex(Tex.go + go.proto.texure_2));
		this.spriteDoorClosed.setPosition(go.sprite.getX(), go.sprite.getY());
	}

	@Override
	public void execute() {
		doorStatus = !doorStatus;
		
		this.go.passable = !doorStatus;
		
		if(doorStatus){
			go.sprite = spriteDoorOpen;
		}
		else{
			go.sprite = spriteDoorClosed;
		}
	}
}