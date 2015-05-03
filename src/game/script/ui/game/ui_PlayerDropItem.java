package game.script.ui.game;

import ui.widgets.EquipmentWidget;
import game.cycle.scene.game.state.location.creature.items.Item;
import game.resources.Cursors;
import game.script.Script;

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
