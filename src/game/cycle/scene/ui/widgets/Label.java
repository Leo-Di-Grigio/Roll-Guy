package game.cycle.scene.ui.widgets;

import game.cycle.scene.ui.Widget;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;

public class Label extends Widget {

	protected String text;
	protected TextBounds bounds;
	protected BitmapFont.HAlignment textAlignment;
	
	public Label(String title, String text) {
		super(title);
		setText(text);
	}
	
	public void setText(String text){
		this.text = text;
		bounds = font.getBounds(text);
		setTextAlignment(textAlignment);
	}

	public void setTextAlignment(BitmapFont.HAlignment textAlignment) {
		this.textAlignment = textAlignment;
	}
	
	@Override
	public void draw(SpriteBatch sprites) {
		font.drawWrapped(sprites, text, x, y + bounds.height * 2, sizeX, textAlignment);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		bounds = null;
		text = null;
	}
}
