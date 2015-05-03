package ui.widgets.used;

import resources.Resources;
import resources.tex.Tex;
import ui.Widget;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Image extends Widget {
	
	public Image(String title) {
		super(title);
		setTexNormal(Resources.getTex(Tex.UI_BACKGROUND_NORMAL));
	}

	@Override
	public void draw(SpriteBatch sprites) {
		sprites.draw(this.texNormal, x, y, sizeX, sizeY);
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
