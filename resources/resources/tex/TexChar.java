package resources.tex;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TexChar extends Tex {
	
	public static final int DIRECT_DOWN = 0;
	public static final int DIRECT_LEFT = 1;
	public static final int DIRECT_RIGHT = 2;
	public static final int DIRECT_UP = 3;
	
	public TextureRegion [] move1;
	public TextureRegion [] move2;
	public TextureRegion [] idle;
	public TextureRegion [] dead;
	
	public TexChar(int id, Texture tex) {
		super(id, tex);
		
		move1 = new TextureRegion[4];
		move2 = new TextureRegion[4];
		idle = new TextureRegion[4];
		dead = new TextureRegion[4];
		
		for(int i = 0; i < 4; ++i){
			move1[i] = new TextureRegion(tex, 0, i*40, 40, 40);
			move2[i] = new TextureRegion(tex, 80,i*40, 40, 40);
			idle[i]  = new TextureRegion(tex, 40,i*40, 40, 40);
			dead[i]  = new TextureRegion(tex, 120,i*40, 40, 40);
		}
	}
}
