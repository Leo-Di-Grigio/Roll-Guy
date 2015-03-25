package game.cycle.scene.ui.widgets.windows;

import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.ui.list.UIGame;

public class WindowCorpse extends WindowInventory {
	
	public WindowCorpse(String title, UIGame ui, int layer, int sizeX, int sizeY, int posX, int posY) {
		super(title, ui, layer, sizeX, sizeY, posX, posY);
		setText("Corpse");
		addWidgets();
	}

	private void addWidgets() {
		
	}

	public void showCreature(Creature creature) {
		if(creature == null){
			super.showContainer(null);
		}
		else{
			if(creature.isAlive()){
				super.showContainer(null);
			}
			else{
				super.showContainer(creature.inventory);
			}
		}
	}
}
