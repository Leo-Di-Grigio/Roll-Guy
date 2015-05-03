package game.script.ui.game;

import resources.Cursors;
import ui.widgets.ImageItem;
import ui.widgets.used.InventoryWidget;
import game.script.Script;

public class ui_InventoryPickItem implements Script {

	private InventoryWidget inventory;
	private ImageItem img;

	public ui_InventoryPickItem(InventoryWidget inventoryWidget, ImageItem img) {
		this.inventory = inventoryWidget;
		this.img = img;
	}

	@Override
	public void execute() {
		if(Cursors.getSelectedItem() == null){
			inventory.pickItem(img.getGuid());
		}
	}
}
