package ui.widgets.used;

import resources.Cursors;
import resources.tex.Tex;
import tools.Const;
import ui.Alignment;
import ui.Tooltip;
import ui.Window;
import game.cycle.scene.ui.list.UIGame;
import game.script.ui.game.ui_PlayerDropItem;
import game.script.ui.game.ui_PlayerPickItem;
import game.state.location.creature.Creature;
import game.state.location.creature.items.Equipment;
import game.state.location.creature.items.Item;

public class EquipmentWidget extends Window {
	
	public static final String SLOT_HEAD = "-slot-head";
	public static final String SLOT_CHEST = "-slot-chest";
	public static final String SLOT_HAND_1 = "-slot-h1";
	public static final String SLOT_HAND_2 = "-slot-h2";

	public static final String ITEM_HEAD = "-item-head";
	public static final String ITEM_CHEST = "-item-chest";
	public static final String ITEM_HAND_1 = "-item-h1";
	public static final String ITEM_HAND_2 = "-item-h2";
	
	private Image head;
	private Image chest;
	private Image hand1;
	private Image hand2;

	private UIGame uigame;
	private Equipment equip;
	
	public EquipmentWidget(String title, UIGame ui, int layer, int posX, int posY) {
		super(title, ui, Alignment.CENTER, 200, 0, posX, posY, layer);
		this.uigame = ui;
		loadWidgets();
	}

	private void loadWidgets() {
		head = new Image(this.title+SLOT_HEAD);
		head.setTexNormal(Tex.UI_INVENTORY_SLOT_HELMET);
		head.setSize(64, 64);
		head.setPosition(Alignment.DOWNCENTER, 0, 0);
		head.setScript(new ui_PlayerDropItem(Equipment.slotHead, this));
		head.setLayer(1);
		this.add(head);
		
		chest = new Image(this.title+SLOT_CHEST);
		chest.setTexNormal(Tex.UI_INVENTORY_SLOT_CHEST);
		chest.setSize(64, 96);
		chest.setPosition(Alignment.DOWNCENTER, 0, -100);
		chest.setScript(new ui_PlayerDropItem(Equipment.slotChest, this));
		chest.setLayer(1);
		this.add(chest);
		
		hand1 = new Image(this.title+SLOT_HAND_1);
		hand1.setTexNormal(Tex.UI_INVENTORY_SLOT_HAND_1);
		hand1.setSize(64, 96);
		hand1.setPosition(Alignment.DOWNCENTER, -70, -100);
		hand1.setScript(new ui_PlayerDropItem(Equipment.slotHand1, this));
		hand1.setLayer(1);
		this.add(hand1);
		
		hand2 = new Image(this.title+SLOT_HAND_2);
		hand2.setTexNormal(Tex.UI_INVENTORY_SLOT_HAND_2);
		hand2.setSize(64, 96);
		hand2.setPosition(Alignment.DOWNCENTER, 70, -100);
		hand2.setScript(new ui_PlayerDropItem(Equipment.slotHand2, this));
		hand2.setLayer(1);
		this.add(hand2);
	}
	
	public void setCreature(Creature creature) {
		if(creature == null){
			this.equip = null;
			setVisible(false);
		}
		else{
			if(visible){
				this.equip = null;
				setVisible(false);
			}
			else{
				this.equip = creature.equipment();
				loadSlots();
				setVisible(true);
			}
		}
	}


	private void loadSlots() {
		if(equip == null){
			return;
		}

		this.remove(this.title+ITEM_HEAD);
		this.remove(this.title+ITEM_CHEST);
		this.remove(this.title+ITEM_HAND_1);
		this.remove(this.title+ITEM_HAND_2);
		
		if(equip.slots[Equipment.slotHead] != null){
			dropHelmet(equip.slots[Equipment.slotHead]);
		}
		if(equip.slots[Equipment.slotChest] != null){
			dropChest(equip.slots[Equipment.slotChest]);
		}
		if(equip.slots[Equipment.slotHand1] != null){
			dropHand1(equip.slots[Equipment.slotHand1]);
		}
		if(equip.slots[Equipment.slotHand2] != null){
			dropHand2(equip.slots[Equipment.slotHand2]);
		}
	}

	public void dropItem(int slot, Item item) {
		if(equip == null){
			return;
		}
		if(slot == Equipment.slotHead){
			if(item.proto.slot() == Const.ITEM_SLOT_HEAD && equip.slots[Equipment.slotHead] == null){
				dropHelmet(item);
			}
			else{
				Item tmp = equip.slots[Equipment.slotHead];
				equip.slots[Equipment.slotHead] = null;
				this.remove(this.title+ITEM_HEAD);
				dropHelmet(item);
				Cursors.selectItem(tmp);
			}
		}
		else if(slot == Equipment.slotChest){
			if(item.proto.slot() == Const.ITEM_SLOT_CHEST){
				if(equip.slots[Equipment.slotChest] == null){
					dropChest(item);
				}
				else{
					Item tmp = equip.slots[Equipment.slotChest];
					equip.slots[Equipment.slotChest] = null;
					this.remove(this.title+ITEM_CHEST);
					dropChest(item);
					Cursors.selectItem(tmp);	
				}
			}
		}
		else if(slot == Equipment.slotHand1 || slot == Equipment.slotHand2){
			if(item.proto.slot() == Const.ITEM_SLOT_WEAPON_1H){
				if(slot == Equipment.slotHand1){
					if(equip.slots[Equipment.slotHand1] == null){
						dropHand1(item);
					}
					else{
						Item tmp = equip.slots[Equipment.slotHand1];
						equip.slots[Equipment.slotHand1] = null;
						this.remove(this.title+ITEM_HAND_1);
						dropHand1(item);
						Cursors.selectItem(tmp);
					}
				}
				else if(slot == Equipment.slotHand2){
					if(equip.slots[Equipment.slotHand2] == null){
						dropHand2(item);
					}
					else{
						Item tmp = equip.slots[Equipment.slotHand2];
						equip.slots[Equipment.slotHand2] = null;
						this.remove(this.title+ITEM_HAND_2);
						dropHand2(item);
						Cursors.selectItem(tmp);
					}
				}
			}
			else if(item.proto.slot() == Const.ITEM_SLOT_WEAPIN_2H){
				if(equip.slots[Equipment.slotHand1] == null){
					if(equip.slots[Equipment.slotHand2] == null){
						dropHand1(item);
						dropHand2(item);
					}
					else{
						Item tmp = equip.slots[Equipment.slotHand1];
						equip.slots[Equipment.slotHand1] = null;
						equip.slots[Equipment.slotHand2] = null;
						this.remove(this.title+ITEM_HAND_1);
						this.remove(this.title+ITEM_HAND_2);
						dropHand1(item);
						dropHand2(item);
						Cursors.selectItem(tmp);
					}
				}
			}
		}
	}
	
	private void dropHelmet(Item item){
		Image img = new Image(this.title+ITEM_HEAD);
		img.setSize(item.tex.getWidth(), item.tex.getHeight());
		img.setPosition(Alignment.DOWNCENTER, 0, 0);
		img.setTexNormal(item.tex);
		img.setScript(new ui_PlayerPickItem(Equipment.slotHead, this));
		img.setTooltip(new Tooltip(item.proto.title(), "mass: "+item.proto.mass()+"\nguid: "+item.guid));
		img.setLayer(2);
		this.add(img);
		equip.slots[Equipment.slotHead] = item;
		uigame.selectItem(null);
		setVisible(true);
	}
	
	private void dropChest(Item item) {
		Image img = new Image(this.title+ITEM_CHEST);
		img.setSize(item.tex.getWidth(), item.tex.getHeight());
		img.setPosition(Alignment.DOWNCENTER, 0, -100);
		img.setTexNormal(item.tex);
		img.setScript(new ui_PlayerPickItem(Equipment.slotChest, this));
		img.setTooltip(new Tooltip(item.proto.title(), "mass: "+item.proto.mass()+"\nguid: "+item.guid));
		img.setLayer(2);
		this.add(img);
		equip.slots[Equipment.slotChest] = item;
		uigame.selectItem(null);
		setVisible(true);
	}
	
	private void dropHand1(Item item) {
		Image img = new Image(this.title+ITEM_HAND_1);
		img.setSize(item.tex.getWidth(), item.tex.getHeight());
		img.setPosition(Alignment.DOWNCENTER, -70, -100);
		img.setTexNormal(item.tex);
		img.setScript(new ui_PlayerPickItem(Equipment.slotHand1, this));
		img.setTooltip(new Tooltip(item.proto.title(), "mass: "+item.proto.mass()+"\nguid: "+item.guid));
		img.setLayer(2);
		this.add(img);
		equip.slots[Equipment.slotHand1] = item;
		uigame.selectItem(null);
		setVisible(true);
	}
	
	private void dropHand2(Item item) {
		Image img = new Image(this.title+ITEM_HAND_2);
		img.setSize(item.tex.getWidth(), item.tex.getHeight());
		img.setPosition(Alignment.DOWNCENTER, 70, -100);
		img.setTexNormal(item.tex);
		img.setScript(new ui_PlayerPickItem(Equipment.slotHand2, this));
		img.setTooltip(new Tooltip(item.proto.title(), "mass: "+item.proto.mass()+"\nguid: "+item.guid));
		img.setLayer(2);
		this.add(img);
		equip.slots[Equipment.slotHand2] = item;
		uigame.selectItem(null);
		setVisible(true);
	}

	// Pick item
	public void pickItem(int slot) {
		switch(slot){
			case Equipment.slotHead:
				Cursors.selectItem(equip.slots[Equipment.slotHead]);
				equip.slots[Equipment.slotHead] = null;
				this.remove(this.title+ITEM_HEAD);
				break;
				
			case Equipment.slotChest:
				Cursors.selectItem(equip.slots[Equipment.slotChest]);
				equip.slots[Equipment.slotChest] = null;
				this.remove(this.title+ITEM_CHEST);
				break;
			
			case Equipment.slotHand1:
			case Equipment.slotHand2:
				pickHandItem(slot);
				break;
		}
	}

	private void pickHandItem(int slot) {
		boolean H2 = false;
		
		if(slot == Equipment.slotHand1){
			if(equip.slots[Equipment.slotHand1].proto.slot() == Const.ITEM_SLOT_WEAPIN_2H){
				H2 = true;
			}
			else{
				Cursors.selectItem(equip.slots[Equipment.slotHand1]);
				equip.slots[Equipment.slotHand1] = null;
				this.remove(this.title+ITEM_HAND_1);
			}
		}
		else if(slot == Equipment.slotHand2){
			if(equip.slots[Equipment.slotHand2].proto.slot() == Const.ITEM_SLOT_WEAPIN_2H){
				H2 = true;
			}
			else{
				Cursors.selectItem(equip.slots[Equipment.slotHand2]);
				equip.slots[Equipment.slotHand2] = null;
				this.remove(this.title+ITEM_HAND_2);
			}
		}
		
		if(H2){
			Cursors.selectItem(equip.slots[Equipment.slotHand1]);
			equip.slots[Equipment.slotHand1] = null;
			equip.slots[Equipment.slotHand2] = null;
			this.remove(this.title+ITEM_HAND_1);
			this.remove(this.title+ITEM_HAND_2);
		}
	}
}
