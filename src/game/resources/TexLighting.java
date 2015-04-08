package game.resources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TexLighting extends Tex {
	
	public TextureRegion [] power;
	
	public TexLighting(int id, Texture tex) {
		super(id, tex);
		power = new TextureRegion[11];
		
		for(int i = 0; i < power.length; ++i){
			power[i] = new TextureRegion(tex, i*32, 0, 32, 32);
		}
	}
}
