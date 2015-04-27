package game.cycle.scene.game.state.location.go;

import com.badlogic.gdx.graphics.g2d.Sprite;

import game.cycle.scene.game.state.database.Database;
import game.cycle.scene.game.state.database.GameConst;
import game.resources.Resources;
import game.resources.tex.Tex;

public class GOFactory {

	public static GO getGo(int guid, int id, int x, int y, int param1, int param2, int param3, int param4){
		GO go = new GO(guid, Database.getGO(id));
		
		go.setSprite(new Sprite(Resources.getTex(Tex.GO_NULL + go.proto.tex1())));
		go.setSpritePosition(x*GameConst.TILE_SIZE, y*GameConst.TILE_SIZE);
		go.setLos(go.proto.los());
		go.setParam1(param1);
		go.setParam2(param2);
		go.setParam3(param3);
		go.setParam4(param4);
		go.script = go.proto.script();
		
		return go;
	}
}
