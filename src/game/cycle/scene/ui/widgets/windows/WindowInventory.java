package game.cycle.scene.ui.widgets.windows;

import game.cycle.scene.game.state.location.creature.items.Inventory;
import game.cycle.scene.ui.list.UIGame;
import game.cycle.scene.ui.widgets.Image;
import game.cycle.scene.ui.widgets.InventoryWidget;
import game.cycle.scene.ui.widgets.Window;
import game.resources.Resources;
import game.resources.tex.Tex;
import game.script.ui.game.ui_InventoryUpdate;

public class WindowInventory extends Window {
	public static final String uiBackground = "-back";
	public static final String uiInventory = "-inventory";
	
	public Image background;
	private InventoryWidget inventory;
	
	public WindowInventory(String title, UIGame ui, int layer, int inventorySizeX, int inventorySizeY) {
		super(title, ui, Alignment.CENTER, 176, 24, 0, 0, layer);
		this.setTexNormal(Resources.getTex(Tex.UI_LIST_LINE));
		this.setText("Container");
		loadWidgets(ui, inventorySizeX, inventorySizeY);
	}
	
	private void loadWidgets(UIGame ui, int sizeX, int sizeY) {
		this.closeButton(true);
		this.closeButton.setScript(new ui_InventoryUpdate(this));
		this.lockButton(true);
		
		background = new Image(this.title + uiBackground);
		background.setSize(176, 218);
		background.setPosition(Alignment.UPCENTER, 0, -24);
		this.add(background);
		
		inventory = new InventoryWidget(this.title+uiInventory, ui, 1, 0, -12);
		this.add(inventory);
	}

	public void showContainer(Inventory inventory) {
		this.inventory.showContainer(inventory);
		
		if(inventory == null){
			this.setVisible(false);
		}
		else{
			this.setVisible(true);
		}
	}

	public void update() {
		inventory.update();
	}
}
