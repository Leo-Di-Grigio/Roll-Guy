package game.script.ui.game;

import game.cycle.scene.ui.widgets.ImageItem;
import game.cycle.scene.ui.widgets.windows.WindowInventory;
import game.resources.Cursors;
import game.script.Script;

public class ui_InventoryPickItem implements Script {

	private WindowInventory inventory;
	private ImageItem img;

	public ui_InventoryPickItem(WindowInventory inventory, ImageItem img) {
		this.inventory = inventory;
		this.img = img;
	}

	@Override
	public void execute() {
		if(Cursors.getSelectedItem() == null){
			inventory.pickItem(img.getGuid());
		}
	}
}
