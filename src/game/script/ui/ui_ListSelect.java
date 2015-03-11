package game.script.ui;

import game.cycle.input.UserInput;
import game.cycle.scene.ui.widgets.List;
import game.script.Script;

public class ui_ListSelect implements Script {

	private List list;
	
	public ui_ListSelect(List list) {
		this.list = list;
	}
	
	@Override
	public void execute() {
		list.selectLine(UserInput.mouseX, UserInput.mouseY);
	}
}
