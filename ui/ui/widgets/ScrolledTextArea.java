package ui.widgets;

import game.cycle.scene.ui.interfaces.Scroll;

public class ScrolledTextArea extends TextArea implements Scroll {

	public ScrolledTextArea(String title) {
		super(title);
	}

	@Override
	public void scroll(int amount) {

	}
}
