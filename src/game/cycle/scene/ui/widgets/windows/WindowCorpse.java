package game.cycle.scene.ui.widgets.windows;

import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.ui.list.UIGame;

public class WindowCorpse extends WindowInventory {
	
	public WindowCorpse(String title, UIGame ui, int layer, int sizeX, int sizeY) {
		super(title, ui, layer, sizeX, sizeY);
		setText("Corpse");
		addWidgets();
	}

	private void addWidgets() {
		
	}

	public void showCreature(Creature creature) {
		if(creature == null){
			showContainer(null);
		}
		else{
			if(creature.isAlive()){
				showContainer(null);
			}
			else{
				showContainer(creature.inventory);
			}
		}
	}
}
