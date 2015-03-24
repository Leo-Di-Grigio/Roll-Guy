package game.script.ui.game;

import game.cycle.scene.ui.widgets.windows.WindowPlayer;
import game.resources.Cursors;
import game.script.Script;

public class ui_PlayerPickItem implements Script {

	private int slot;
	private WindowPlayer window;

	public ui_PlayerPickItem(int slot, WindowPlayer window) {
		this.slot = slot;
		this.window = window;
	}

	@Override
	public void execute() {
		if(Cursors.getSelectedItem() == null){
			window.pickItem(slot);
		}
	}
}
