package game.cycle.scene.ui.widgets;

import game.cycle.scene.ui.Widget;
import game.resources.Resources;
import game.resources.tex.Tex;

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
