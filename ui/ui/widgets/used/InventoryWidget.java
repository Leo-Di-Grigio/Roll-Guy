package ui.widgets.used;

import java.awt.Point;
import java.util.HashMap;

import resources.tex.Tex;
import ui.Alignment;
import ui.Tooltip;
import ui.Window;
import ui.widgets.ImageItem;
import ui.widgets.InventorySlot;

import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;

import game.cycle.scene.game.state.location.creature.items.Inventory;
import game.cycle.scene.game.state.location.creature.items.Item;
import game.cycle.scene.ui.list.UIGame;
import game.script.ui.game.ui_InventoryPickItem;
import game.tools.Const;

public class InventoryWidget extends Window {
	
	public static final String SLOT = "-slot-";
	public static final String ITEM = "-item-";
	public static final String MASS = "-mass";
	
	private InventorySlot [][] slots;
	private HashMap<Integer, ImageItem> items;
	private Label mass;

	// loaded
	private UIGame uigame;
	private Inventory inventory;
	
	public InventoryWidget(String title, UIGame ui, int layer, int posX, int posY) {
		super(title, ui, Alignment.CENTER, 176, 0, posX, posY, layer);
		this.uigame = ui;
		this.items = new HashMap<Integer, ImageItem>();
		loadWidgets();
	}

	private void loadWidgets() {
		slots = new InventorySlot[Const.INVENTORY_SIZE_X][Const.INVENTORY_SIZE_Y];
		for(int i = 0; i < Const.INVENTORY_SIZE_X; ++i){
			for(int j = 0; j < Const.INVENTORY_SIZE_Y; ++j){
				slots[i][j] = new InventorySlot(this.title + SLOT, i, j, this);
				slots[i][j].setSize(32, 32);
				slots[i][j].setPosition(Alignment.DOWNLEFT, i*32 + 8, -j*32 - 34);
				slots[i][j].setLayer(1);
				slots[i][j].setTexNormal(Tex.UI_INVENTORY_SLOT);
				this.add(slots[i][j]);
			}
		}
		
		mass = new Label(this.title + MASS, "");
		mass.setSize(386, 32);
		mass.setPosition(Alignment.DOWNLEFT, 10, -220);
		mass.setTextAlignment(HAlignment.LEFT);
		mass.setLayer(1);
		this.add(mass);
	}
	
	public void showContainer(Inventory inventory){
		resetSlots();
		
		if(inventory == null){
			setVisible(false);
		}
		else{
			if(visible){
				setVisible(false);
			}
			else{
				this.inventory = inventory;
				loadSlots();
				setVisible(true);
			}
		}
	}
	
	private void resetSlots() {
		for(Image img: items.values()){
			this.remove(img.getTitle());
		}
		
		items.clear();
	}
	
	private void loadSlots() {
		HashMap<Integer, Item> list = inventory.getItems();
		
		for(Item item: list.values()){
			Point pos = inventory.getItemPos(item.guid);
			addItem(item, pos.x, pos.y);
		}
		
		updateMass();
	}
	
	public void dropItem(Item item, int slotX, int slotY){
		if(inventory.dropItem(slotX, slotY, item)){
			addItem(item, slotX, slotY);
			uigame.selectItem(null);
			this.setVisible(true);
			updateMass();
		}
	}
	
	private void addItem(Item item, int slotX, int slotY){
		ImageItem img = new ImageItem(this.title + ITEM + item.guid, item.guid);
		img.setSize(32*item.proto.sizeX(), 32*item.proto.sizeY());
		img.setPosition(Alignment.DOWNLEFT, slotX*32 + 8, -slotY*32 - (item.proto.sizeY()*32) - 2);
		img.setTexNormal(item.tex);
		img.setScript(new ui_InventoryPickItem(this, img));
		img.setTooltip(new Tooltip(item.proto.title(), "mass: "+item.proto.mass()+"\nguid: "+item.guid));
		img.setLayer(2);
		this.add(img);
		items.put(item.guid, img);
	}
	
	private void updateMass() {
		mass.setText("Mass: " + inventory.getTotalMass());
	}

	public void pickItem(int guid){
		Point pos = inventory.getItemPos(guid);
		if(pos != null){
			ImageItem img = items.get(guid);
			
			if(img != null){
				Item item = inventory.pickItem(pos.x, pos.y);
				
				if(item != null){
					this.remove(img.getTitle());
					this.items.remove(guid);
					uigame.selectItem(item);
					updateMass();
				}
			}
		}
	}
	
	public void update(){
		showContainer(inventory);
		showContainer(inventory);
	}
}
