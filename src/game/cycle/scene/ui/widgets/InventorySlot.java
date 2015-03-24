package game.cycle.scene.ui.widgets;

import game.cycle.scene.ui.widgets.windows.WindowInventory;
import game.script.ui.game.ui_InventoryDropItem;

public class InventorySlot extends Image {

	public InventorySlot(String title, int x, int y, WindowInventory inventory) {
		super(title + x + "-" + y);		
		this.setScript(new ui_InventoryDropItem(x, y, inventory));
	}
}
