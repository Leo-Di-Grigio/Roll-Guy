package game.script.ui.game;

import game.cycle.scene.game.world.creature.items.Item;
import game.cycle.scene.ui.widgets.windows.WindowInventory;
import game.resources.Cursors;
import game.script.Script;

public class ui_InventoryDropItem implements Script {

	private WindowInventory inventory;
	private int x;
	private int y;

	public ui_InventoryDropItem(int x, int y, WindowInventory inventory) {
		this.inventory = inventory;
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
