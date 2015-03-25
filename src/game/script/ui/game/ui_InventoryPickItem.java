package game.script.ui.game;

import game.cycle.scene.ui.widgets.ImageItem;
import game.cycle.scene.ui.widgets.InventoryWidget;
import game.resources.Cursors;
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
