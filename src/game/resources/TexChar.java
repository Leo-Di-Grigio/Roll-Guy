package game.resources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TexChar extends Tex {
	
	public static final int directDown = 0;
	public static final int directLeft = 1;
	public static final int directRight = 2;
	public static final int directUp = 3;
	
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
			move1[i] = new TextureRegion(tex, 0, i*32, 32, 32);
			move2[i] = new TextureRegion(tex, 64,i*32, 32, 32);
			idle[i]  = new TextureRegion(tex, 32,i*32, 32, 32);
			dead[i]  = new TextureRegion(tex, 96,i*32, 32, 32);
		}
	}
}
