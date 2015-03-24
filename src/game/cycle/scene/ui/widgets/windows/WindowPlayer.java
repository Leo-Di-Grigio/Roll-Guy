package game.cycle.scene.ui.widgets.windows;

import game.cycle.scene.game.world.creature.Player;
import game.cycle.scene.game.world.creature.items.Equipment;
import game.cycle.scene.game.world.creature.items.Item;
import game.cycle.scene.game.world.creature.items.ItemProto;
import game.cycle.scene.ui.list.UIGame;
import game.cycle.scene.ui.widgets.Image;
import game.cycle.scene.ui.widgets.Window;
import game.resources.Cursors;
import game.resources.Resources;
import game.resources.Tex;
import game.script.ui.game.ui_PlayerDropItem;
import game.script.ui.game.ui_PlayerPickItem;

public class WindowPlayer extends Window {
	
	private UIGame uigame;
	private Equipment equip;
	
	public static final String uiBackground = "player-back";
	public static final String uiSlotHead = "player-slot-head";
	public static final String uiSlotChest = "player-slot-chest";
	public static final String uiSlotHand1 = "player-slot-h1";
	public static final String uiSlotHand2 = "player-slot-h2";
	
	public static final String uiSlotItemHead = "player-slot-item-head";
	public static final String uiSlotItemChest = "player-slot-item-chest";
	public static final String uiSlotItemHand1 = "player-slot-item-h1";
	public static final String uiSlotItemHand2 = "player-slot-item-h2";
	
	public Image background;
	public Image head;
	public Image chest;
	public Image hand1;
	public Image hand2;
	
	public WindowPlayer(String title, UIGame ui, int layer) {
		super(title, ui, Alignment.CENTER, 336, 24, -300, 100, layer);
		this.uigame = ui;
		this.setTexNormal(Resources.getTex(Tex.uiListLine));
		this.setText("Player");
		loadWidgets();
	}

	private void loadWidgets() {
		this.closeButton(true);
		this.lockButton(true);
		
		background = new Image(uiBackground);
		background.setSize(336, 200);
		background.setPosition(Alignment.UPCENTER, 0, -24);
		this.add(background);
		
		head = new Image(uiSlotHead);
		head.setTexNormal(Tex.uiInventorySlotHead);
		head.setSize(64, 64);
		head.setPosition(Alignment.UPCENTER, 0, -30);
		head.setLayer(1);
		head.setScript(new ui_PlayerDropItem(Equipment.slotHead, this));
		this.add(head);
		
		chest = new Image(uiSlotChest);
		chest.setTexNormal(Tex.uiInventorySlotChest);
		chest.setSize(64, 96);
		chest.setPosition(Alignment.UPCENTER, 0, -100);
		chest.setLayer(1);
		chest.setScript(new ui_PlayerDropItem(Equipment.slotChest, this));
		this.add(chest);
		
		hand1 = new Image(uiSlotHand1);
		hand1.setTexNormal(Tex.uiInventorySlotHand1);
		hand1.setSize(64, 96);
		hand1.setPosition(Alignment.UPCENTER, -100, -100);
		hand1.setLayer(1);
		hand1.setScript(new ui_PlayerDropItem(Equipment.slotHand1, this));
		this.add(hand1);
		
		hand2 = new Image(uiSlotHand2);
		hand2.setTexNormal(Tex.uiInventorySlotHand2);
		hand2.setSize(64, 96);
		hand2.setPosition(Alignment.UPCENTER, 100, -100);
		hand2.setLayer(1);
		hand2.setScript(new ui_PlayerDropItem(Equipment.slotHand2, this));
		this.add(hand2);
	}

	public void setPlayer(Player player) {
		if(visible){
			setVisible(false);
		}
		else{
			this.equip = player.equipment;
			setVisible(true);
		}
	}

	// Drop item
	public void dropItem(int slot, Item item) {
		if(slot == Equipment.slotHead){
			if(item.proto.type == ItemProto.typeHelemt && equip.head == null){
				dropHelmet(item);
			}
		}
		else if(slot == Equipment.slotChest){
			if(item.proto.type == ItemProto.typeChest && equip.chest == null){
				dropChest(item);
			}
		}
		else if(slot == Equipment.slotHand1 || slot == Equipment.slotHand2){
			if(item.proto.type == ItemProto.typeWeapon1H){
				if(slot == Equipment.slotHand1 && equip.hand1 == null){
					dropHand1(item);
				}
				else if(slot == Equipment.slotHand2 && equip.hand2 == null){
					dropHand2(item);
				}
			}
			else if(item.proto.type == ItemProto.typeWeapon2H){
				if(equip.hand1 == null && equip.hand2 == null){
					dropHand1(item);
					dropHand2(item);
				}
			}
		}
	}

	private void dropHelmet(Item item){
		Image img = new Image(uiSlotItemHead);
		img.setSize(item.tex.getWidth(), item.tex.getHeight());
		img.setPosition(Alignment.UPCENTER, 0, -30);
		img.setLayer(2);
		img.setTexNormal(item.tex);
		img.setScript(new ui_PlayerPickItem(Equipment.slotHead, this));
		this.add(img);
		equip.head = item;
		uigame.selectItem(null);
		setVisible(true);
	}
	
	private void dropChest(Item item) {
		Image img = new Image(uiSlotItemChest);
		img.setSize(item.tex.getWidth(), item.tex.getHeight());
		img.setPosition(Alignment.UPCENTER, 0, -100);
		img.setLayer(2);
		img.setTexNormal(item.tex);
		img.setScript(new ui_PlayerPickItem(Equipment.slotChest, this));
		this.add(img);
		equip.chest = item;
		uigame.selectItem(null);
		setVisible(true);
	}
	
	private void dropHand1(Item item) {
		Image img = new Image(uiSlotItemHand1);
		img.setSize(item.tex.getWidth(), item.tex.getHeight());
		img.setPosition(Alignment.UPCENTER, -100, -100);
		img.setLayer(2);
		img.setTexNormal(item.tex);
		img.setScript(new ui_PlayerPickItem(Equipment.slotHand1, this));
		this.add(img);
		equip.hand1 = item;
		uigame.selectItem(null);
		setVisible(true);
	}
	
	private void dropHand2(Item item) {
		Image img = new Image(uiSlotItemHand2);
		img.setSize(item.tex.getWidth(), item.tex.getHeight());
		img.setPosition(Alignment.UPCENTER, 100, -100);
		img.setLayer(2);
		img.setTexNormal(item.tex);
		img.setScript(new ui_PlayerPickItem(Equipment.slotHand2, this));
		this.add(img);
		equip.hand2 = item;
		uigame.selectItem(null);
		setVisible(true);	
	}

	// Pick item
	public void pickItem(int slot) {
		switch(slot){
			case Equipment.slotHead:
				Cursors.selectItem(equip.head);
				equip.head = null;
				this.remove(uiSlotItemHead);
				break;
				
			case Equipment.slotChest:
				Cursors.selectItem(equip.chest);
				equip.chest = null;
				this.remove(uiSlotItemChest);
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
			if(equip.hand1.proto.type == ItemProto.typeWeapon2H){
				H2 = true;
			}
			else{
				Cursors.selectItem(equip.hand1);
				equip.hand1 = null;
				this.remove(uiSlotItemHand1);
			}
		}
		else if(slot == Equipment.slotHand2){
			if(equip.hand2.proto.type == ItemProto.typeWeapon2H){
				H2 = true;
			}
			else{
				Cursors.selectItem(equip.hand2);
				equip.hand2 = null;
				this.remove(uiSlotItemHand2);
			}
		}
		
		if(H2){
			Cursors.selectItem(equip.hand1);
			equip.hand1 = null;
			equip.hand2 = null;
			this.remove(uiSlotItemHand1);
			this.remove(uiSlotItemHand2);
		}
	}
}
