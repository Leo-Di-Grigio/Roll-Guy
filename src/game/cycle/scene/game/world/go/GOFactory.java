package game.cycle.scene.game.world.go;

import com.badlogic.gdx.graphics.g2d.Sprite;

import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.map.Location;
import game.resources.Resources;
import game.resources.Tex;
import game.script.Script;
import game.script.game.go.go_DoorUse;

public class GOFactory {

	public static GO getGo(int id, int x, int y){
		GO go = new GO();
		go.proto = Database.getGO(id);
		
		go.sprite = new Sprite(Resources.getTex(Tex.go + go.proto.texure_1));
		go.sprite.setPosition(x*Location.tileSize, y*Location.tileSize);
		
		go.script1 = getScript(go);
		
		return go;
	}

	
	// GO Script base
	public static final int go_DoorUse = 1;
	
	private static Script getScript(GO go) {
		int scriptId = go.proto.scriptId_1;
		
		switch(scriptId){
			case go_DoorUse:
				return new go_DoorUse(go);
		}
		
		return null;
	}
}
