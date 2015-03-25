package game.script.ui.game;

import game.cycle.scene.ui.widgets.windows.WindowInventory;
import game.script.Script;

public class ui_InventoryUpdate implements Script {

	private WindowInventory window;

	public ui_InventoryUpdate(WindowInventory window) {
		this.window = window;
	}

	@Override
	public void execute() {
		this.window.showContainer(null);
	}
}
