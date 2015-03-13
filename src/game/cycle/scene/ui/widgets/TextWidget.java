package game.cycle.scene.ui.widgets;

import game.cycle.scene.ui.KeyInput;
import game.cycle.scene.ui.Widget;

public abstract class TextWidget extends Widget implements KeyInput {

	public int maxTextLength;
	
	public TextWidget(String title) {
		super(title);
		this.keyInput = true;
	}
}
