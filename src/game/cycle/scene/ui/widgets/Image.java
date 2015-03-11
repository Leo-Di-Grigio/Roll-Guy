package game.cycle.scene.ui.widgets;

import game.cycle.scene.ui.Widget;
import game.resources.Resources;
import game.resources.Tex;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Image extends Widget {

	private Sprite sprite;
	
	public Image(String title) {
		super(title);
		sprite = new Sprite(Resources.getTex(Tex.texNull));
	}
	
	public void setTex(Texture tex){
		sprite = new Sprite(tex);
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
	}
	
	public void setRotate(float angle){
		sprite.setRotation(angle);
	}
	
	@Override
	public void setPosition(Alignment alignment, int x, int y) {
		super.setPosition(alignment, x, y);
		sprite.setPosition(this.x, this.y);
	}
	
	@Override
	public void setSize(int sizeX, int sizeY) {
		super.setSize(sizeX, sizeY);
		
		float sX = sizeX/sprite.getWidth();
		float sY = sizeY/sprite.getHeight();
		sprite.setScale(sX, sY);
	}

	@Override
	public void draw(SpriteBatch sprites) {
		sprite.draw(sprites);
	}

	public float getRotate() {
		return sprite.getRotation();
	}

	@Override
	public void dispose() {
		super.dispose();
		sprite = null;
	}
}
