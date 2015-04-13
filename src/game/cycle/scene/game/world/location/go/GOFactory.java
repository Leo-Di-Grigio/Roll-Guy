package game.cycle.scene.game.world.location.go;

import com.badlogic.gdx.graphics.g2d.Sprite;

import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.database.GameConst;
import game.cycle.scene.game.world.event.GameScriptLoader;
import game.resources.Resources;
import game.resources.tex.Tex;

public class GOFactory {

	public static GO getGo(int guid, int id, int x, int y, int param1, int param2, int param3, int param4){
		GO go = new GO(guid, Database.getGO(id));
		
		go.setSprite(new Sprite(Resources.getTex(Tex.GO_NULL + go.proto.tex1())));
		go.setPosition(x, y);
		go.setSpritePosition(x*GameConst.TILE_SIZE, y*GameConst.TILE_SIZE);
		
		go.param1 = param1;
		go.param2 = param2;
		go.param3 = param3;
		go.param4 = param4;
		go.losBlock = go.proto.los();
		go.script = GameScriptLoader.getScript(go);
		
		return go;
	}
}
