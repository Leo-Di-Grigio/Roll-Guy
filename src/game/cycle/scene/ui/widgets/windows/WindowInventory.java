package game.cycle.scene.ui.widgets.windows;

import game.cycle.scene.game.world.creature.items.Inventory;
import game.cycle.scene.game.world.creature.items.Item;
import game.cycle.scene.game.world.database.GameConst;
import game.cycle.scene.ui.list.UIGame;
import game.cycle.scene.ui.widgets.Image;
import game.cycle.scene.ui.widgets.Window;
import game.resources.Resources;
import game.resources.Tex;

public class WindowInventory extends Window {

	private Inventory inventory;
	
	public static final String uiBackground = "inventory-back";
	public static final String uiSlot = "inventory-slot-";
	
	public Image background;
	public Image [][] slots;
	
	public WindowInventory(String title, UIGame ui, int layer) {
		super(title, ui, Alignment.CENTER, 256, 24, 0, 0, layer);
		this.setTexNormal(Resources.getTex(Tex.uiListLine));
		this.setText("Inventory");
		loadWidgets();
	}
	
	private void loadWidgets() {
		this.closeButton(true);
		this.lockButton(true);
		
		background = new Image(uiBackground);
		background.setSize(256, 300);
		background.setPosition(Alignment.UPCENTER, 0, -24);
		this.add(background);
		
		slots = new Image[GameConst.inventorySizeX][GameConst.inventorySizeY];
		for(int i = 0; i < GameConst.inventorySizeX; ++i){
			for(int j = 0; j < GameConst.inventorySizeY; ++j){
				slots[i][j] = new Image(uiSlot+i+"-"+j);
				slots[i][j].setSize(24, 24);
				slots[i][j].setPosition(Alignment.DOWNLEFT, i*24 + 8, -j*24 - 30);
				slots[i][j].setLayer(1);
				slots[i][j].setTexNormal(Tex.uiInventorySlot);
				this.add(slots[i][j]);
			}
		}
	}

	public void showInventory(Inventory inventory){
		if(visible){
			setVisible(false);
		}
		else{
			this.inventory = inventory;
			setVisible(true);
			loadSlots();
		}
	}

	private void loadSlots() {
		for(int i = 0; i < GameConst.inventorySizeX; ++i){
			for(int j = 0; j < GameConst.inventorySizeY; ++j){
				Item item = inventory.getItem(i, j);
				
				if(item != null){
					slots[i][j].setTexNormal(item.tex);
				}
			}
		}
	}
}
