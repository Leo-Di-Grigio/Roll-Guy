package game.script.ui.game;

import resources.Cursors;
import ui.widgets.used.InventoryWidget;
import game.cycle.scene.game.state.location.creature.items.Item;
import game.script.Script;

public class ui_InventoryDropItem implements Script {

	private InventoryWidget inventory;
	private int x;
	private int y;

	public ui_InventoryDropItem(int x, int y, InventoryWidget inventoryWidget) {
		this.inventory = inventoryWidget;
		this.x = x;
		this.y = y;
	}

	@Override
	public void execute() {
		Item item = Cursors.getSelectedItem();
		
		if(item != null){
			inventory.dropItem(item, x, y);
		}
	}
}
