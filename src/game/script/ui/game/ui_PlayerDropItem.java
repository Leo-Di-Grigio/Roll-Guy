package game.script.ui.game;

import game.cycle.scene.game.world.creature.items.Item;
import game.cycle.scene.ui.widgets.windows.WindowPlayer;
import game.resources.Cursors;
import game.script.Script;

public class ui_PlayerDropItem implements Script {

	private int slot;
	private WindowPlayer window;

	public ui_PlayerDropItem(int slot, WindowPlayer window) {
		this.slot = slot;
		this.window = window;
	}

	@Override
	public void execute() {
		Item item = Cursors.getSelectedItem();
		
		if(item != null){
			window.dropItem(slot, item);
		}
	}
}
