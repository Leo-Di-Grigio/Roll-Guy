package game.script.ui.game;

import resources.Cursors;
import ui.widgets.used.EquipmentWidget;
import game.script.Script;
import game.state.location.creature.items.Item;

public class ui_PlayerDropItem implements Script {

	private int slot;
	private EquipmentWidget equipment;

	public ui_PlayerDropItem(int slot, EquipmentWidget equipment) {
		this.slot = slot;
		this.equipment = equipment;
	}

	@Override
	public void execute() {
		Item item = Cursors.getSelectedItem();
		
		if(item != null){
			equipment.dropItem(slot, item);
		}
	}
}
