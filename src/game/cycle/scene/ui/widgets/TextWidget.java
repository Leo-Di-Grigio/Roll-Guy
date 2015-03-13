package game.cycle.scene.ui.widgets;

import game.cycle.scene.ui.KeyInput;
import game.cycle.scene.ui.Widget;
import game.cycle.scene.ui.textfilters.TextFilter;
import game.cycle.scene.ui.textfilters.TextFilterDefault;

public abstract class TextWidget extends Widget implements KeyInput {

	protected TextFilter textFilter;
	public int maxTextLength;
	
	public TextWidget(String title) {
		super(title);
		setTextFilter(new TextFilterDefault());
		this.keyInput = true;
	}
	
	public void setTextFilter(TextFilter filter){
		this.textFilter = filter;
	}
}
