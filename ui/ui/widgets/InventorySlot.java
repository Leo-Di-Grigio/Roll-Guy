package ui.widgets;

import game.script.ui.game.ui_InventoryDropItem;

public class InventorySlot extends Image {

	public InventorySlot(String title, int x, int y, InventoryWidget inventoryWidget) {
		super(title + x + "-" + y);		
		this.setScript(new ui_InventoryDropItem(x, y, inventoryWidget));
	}
}
