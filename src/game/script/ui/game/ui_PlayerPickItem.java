package game.script.ui.game;

import resources.Cursors;
import ui.widgets.used.EquipmentWidget;
import game.script.Script;

public class ui_PlayerPickItem implements Script {

	private int slot;
	private EquipmentWidget equipment;

	public ui_PlayerPickItem(int slot, EquipmentWidget equipment) {
		this.slot = slot;
		this.equipment = equipment;
	}

	@Override
	public void execute() {
		if(Cursors.getSelectedItem() == null){
			equipment.pickItem(slot);
		}
		else{
			equipment.dropItem(slot, Cursors.getSelectedItem());
		}
	}
}
