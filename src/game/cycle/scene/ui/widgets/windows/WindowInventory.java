package game.cycle.scene.ui.widgets.windows;

import java.awt.Point;
import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;

import game.cycle.scene.game.world.creature.items.Inventory;
import game.cycle.scene.game.world.creature.items.Item;
import game.cycle.scene.game.world.database.GameConst;
import game.cycle.scene.ui.list.UIGame;
import game.cycle.scene.ui.widgets.Image;
import game.cycle.scene.ui.widgets.ImageItem;
import game.cycle.scene.ui.widgets.InventorySlot;
import game.cycle.scene.ui.widgets.Label;
import game.cycle.scene.ui.widgets.Window;
import game.resources.Resources;
import game.resources.Tex;
import game.script.ui.game.ui_InventoryPickItem;

public class WindowInventory extends Window {

	private UIGame uigame;
	private Inventory inventory;
	
	public static final String uiBackground = "inventory-back";
	public static final String uiSlot = "inventory-slot-";
	public static final String uiItem = "inventory-item-";
	public static final String uiMass = "inventory-mass";
	
	public Image background;
	public InventorySlot [][] slots;
	public HashMap<Integer, ImageItem> items;
	public Label mass;
	
	public WindowInventory(String title, UIGame ui, int layer) {
		super(title, ui, Alignment.CENTER, 336, 24, 0, 100, layer);
		this.uigame = ui;
		this.setTexNormal(Resources.getTex(Tex.uiListLine));
		this.setText("Inventory");
		this.items = new HashMap<Integer, ImageItem>();
		loadWidgets();
	}
	
	private void loadWidgets() {
		this.closeButton(true);
		this.lockButton(true);
		
		background = new Image(uiBackground);
		background.setSize(336, 410);
		background.setPosition(Alignment.UPCENTER, 0, -24);
		this.add(background);
		
		slots = new InventorySlot[GameConst.inventorySizeX][GameConst.inventorySizeY];
		for(int i = 0; i < GameConst.inventorySizeX; ++i){
			for(int j = 0; j < GameConst.inventorySizeY; ++j){
				slots[i][j] = new InventorySlot(uiSlot, i, j, this);
				slots[i][j].setSize(32, 32);
				slots[i][j].setPosition(Alignment.DOWNLEFT, i*32 + 8, -j*32 - 34);
				slots[i][j].setLayer(1);
				slots[i][j].setTexNormal(Tex.uiInventorySlot);
				this.add(slots[i][j]);
			}
		}
		
		mass = new Label(uiMass, "");
		mass.setSize(386, 32);
		mass.setPosition(Alignment.DOWNLEFT, 10, -410);
		mass.setTextAlignment(HAlignment.LEFT);
		mass.setLayer(1);
		this.add(mass);
	}

	public void showInventory(Inventory inventory){
		if(visible){
			resetSlots();
			setVisible(false);
		}
		else{
			this.inventory = inventory;
			loadSlots();
			setVisible(true);
		}
	}
	
	private void resetSlots() {
		for(int i = 0; i < GameConst.inventorySizeX; ++i){
			for(int j = 0; j < GameConst.inventorySizeY; ++j){
				slots[i][j].setTexNormal(Tex.uiInventorySlot);
			}
		}
		
		for(Image img: items.values()){
			this.remove(img.title);
		}
		
		items.clear();
	}

	private void loadSlots() {
		HashMap<Integer, Item> list = inventory.getItems();
		
		for(Item item: list.values()){
			Point pos = inventory.getItemPos(item.guid);
			addItem(item, pos.x, pos.y);
		}
		
		mass.setText("Mass: " + inventory.getTotalMass());
	}
	
	private void addItem(Item item, int slotX, int slotY){
		ImageItem img = new ImageItem(uiItem+item.guid, item.guid);
		img.setSize(32*item.proto.sizeX, 32*item.proto.sizeY);
		img.setPosition(Alignment.DOWNLEFT, slotX*32 + 8, -slotY*32 - (item.proto.sizeY*32) - 2);
		img.setLayer(2);
		img.setTexNormal(item.tex);
		img.setScript(new ui_InventoryPickItem(this, img));
		this.add(img);
		items.put(item.guid, img);
	}

	public void pickItem(int guid){
		Point pos = inventory.getItemPos(guid);
		if(pos != null){
			ImageItem img = items.get(guid);
			
			if(img != null){
				Item item = inventory.pickItem(pos.x, pos.y);
				
				if(item != null){
					this.remove(img.title);
					this.items.remove(guid);
					uigame.selectItem(item);
				}
			}
		}
	}
	
	public void dropItem(Item item, int slotX, int slotY){
		if(inventory.dropItem(slotX, slotY, item)){
			addItem(item, slotX, slotY);
			uigame.selectItem(null);
			this.setVisible(true);
		}
	}
}
