package game.cycle.scene.game.world.go;

import com.badlogic.gdx.graphics.g2d.Sprite;

import game.cycle.scene.game.world.creature.items.Inventory;
import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.database.GameConst;
import game.resources.Resources;
import game.resources.Tex;
import game.script.ScriptGame;
import game.script.game.go.go_DoorTeleport;
import game.script.game.go.go_DoorUse;

public class GOFactory {

	public static GO getGo(int id, int x, int y, int param1, int param2, int param3, int param4){
		GO go = new GO(Database.getGO(id));
		
		go.setSprite(new Sprite(Resources.getTex(Tex.go + go.proto.texure_1)));
		go.setPosition(x, y);
		go.setSpritePosition(x*GameConst.tileSize, y*GameConst.tileSize);
		
		go.param1 = param1;
		go.param2 = param2;
		go.param3 = param3;
		go.param4 = param4;
		go.losBlock = go.proto.losBlock;
		go.script = getScript(go);
		
		if(go.proto.container){
			go.inventory = new Inventory(go.proto.containerSizeX, go.proto.containerSizeY);
		}
		
		return go;
	}
	
	// GO Script base
	public static final int go_DoorUse = 1;
	public static final int go_DoorTeleport = 2;
	
	private static ScriptGame getScript(GO go) {
		int scriptId = go.proto.scriptId_1;
		
		switch(scriptId){
			case go_DoorUse:
				return new go_DoorUse(go);
				
			case go_DoorTeleport:
				return new go_DoorTeleport(go);
		}
		
		return null;
	}
}
