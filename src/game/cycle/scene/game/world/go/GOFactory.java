package game.cycle.scene.game.world.go;

import com.badlogic.gdx.graphics.g2d.Sprite;

import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.map.Location;
import game.resources.Resources;
import game.resources.Tex;

public class GOFactory {

	public static GO getGo(int baseid, int x, int y){
		GO go = new GO();
		go.proto = Database.getGO(baseid);
		
		go.sprite = new Sprite(Resources.getTex(Tex.go + go.proto.texure));
		go.sprite.setPosition(x*Location.tileSize, y*Location.tileSize);
		
		return go;
	}
}
