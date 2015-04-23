package game.resources.tex;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TexAtlas extends Tex {

	public static final int COLLUMN_SINGLE = 0;
	public static final int COLLUMN_LEFT = 1;
	public static final int COLLUMN_RIGHT = 2;
	public static final int COLLUMN_DOUBLE = 3;
	public static final int WALL_HORIZONTAL = 4;
	public static final int WALL_VERTICAL = 5;
	
	public TextureRegion [] arr;
	
	public TexAtlas(int id, Texture tex) {
		super(id, tex);
		
		arr = new TextureRegion[6];
		
		for(int i = 0; i < arr.length; ++i){
			arr[i] = new TextureRegion(tex, i*40, 0, 40, 120);
		}
	}
}
