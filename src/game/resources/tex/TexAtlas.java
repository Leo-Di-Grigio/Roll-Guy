package game.resources.tex;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TexAtlas extends Tex {

	public static final int TOP_CORNER_LEFT = 0;
	public static final int TOP_CENTER_1 = 1;
	public static final int TOP_CENTER_2 = 2;
	public static final int TOP_CORNER_RIGHT = 3;
	
	public static final int BOTTOM_CORNER_LEFT = 4;
	public static final int BOTTOM_CENTER_1 = 5;
	public static final int BOTTOM_CENTER_2 = 6;
	public static final int BOTTOM_CORNER_RIGHT = 7;
	
	public static final int CENTER_1_LEFT = 8;
	public static final int CENTER_1_RIGHT = 9;
	public static final int CENTER_2_LEFT = 10;
	public static final int CENTER_2_RIGHT = 11;
	
	public static final int CORNER_TOP_LEFT = 12;
	public static final int CORNER_TOP_RIGHT = 13;
	public static final int CORNER_BOTTOM_LEFT = 14;
	public static final int CORNER_BOTTOM_RIGHT = 15;
	
	public TextureRegion [] arr;
	
	public TexAtlas(int id, Texture tex) {
		super(id, tex);
		
		arr = new TextureRegion[16];
		
		arr[TOP_CORNER_LEFT] = new TextureRegion(tex, 0, 0, 80, 120);
		arr[TOP_CENTER_1] = new TextureRegion(tex, 80, 0, 40, 120);
		arr[TOP_CENTER_2] = new TextureRegion(tex, 120, 0, 40, 120);
		arr[TOP_CORNER_RIGHT] = new TextureRegion(tex, 160, 0, 80, 120);
		
		arr[BOTTOM_CORNER_LEFT] = new TextureRegion(tex, 0, 200, 80, 80);
		arr[BOTTOM_CENTER_1] = new TextureRegion(tex, 80, 240, 40, 40);
		arr[BOTTOM_CENTER_2] = new TextureRegion(tex, 120, 240, 40, 40);
		arr[BOTTOM_CORNER_RIGHT] = new TextureRegion(tex, 160, 200, 80, 80);
		
		arr[CENTER_1_LEFT] = new TextureRegion(tex, 0, 120, 40, 40);
		arr[CENTER_1_RIGHT] = new TextureRegion(tex, 200, 120, 40, 40);
		
		arr[CENTER_2_LEFT] = new TextureRegion(tex, 0, 160, 40, 40);
		arr[CENTER_2_RIGHT] = new TextureRegion(tex, 200, 160, 40, 40);
		
		arr[CORNER_TOP_LEFT] = new TextureRegion(tex, 240, 0, 44, 44);
		arr[CORNER_TOP_RIGHT] = new TextureRegion(tex, 320, 0, 44, 44);
		
		arr[CORNER_BOTTOM_LEFT] = new TextureRegion(tex, 240, 80, 44, 132);
		arr[CORNER_BOTTOM_RIGHT] = new TextureRegion(tex, 320, 80, 44, 132);
	}
}
