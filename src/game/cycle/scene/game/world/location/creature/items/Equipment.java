package game.cycle.scene.game.world.location.creature.items;

import game.cycle.scene.game.world.database.Database;
import game.tools.Const;

public class Equipment {

	public static final int slotHead = 0;
	public static final int slotChest = 1;
	public static final int slotHand1 = 2;
	public static final int slotHand2 = 3;
	
	public Item [] slots;
	
	public Equipment() {
		this.slots = new Item[4];
	}
	
	public int [] getIntArray(){
		int [] array = new int[4];
		
		for(int i = 0; i < slots.length; ++i){
			if(slots[i] == null){
				array[i] = Const.INVALID_ID;
			}
			else{
				array[i] = slots[i].proto.id;
			}
		}
		
		return array;
	}

	public int getTotalMass() {
		int mass = 0;
		for(Item item: slots){
			if(item != null){
				mass += item.proto.mass;
			}
		}
		
		return mass;
	}

	public void loadSlots(int head, int chest, int hand1, int hand2) {
		if(head != Const.INVALID_ID){
			slots[Equipment.slotHead] = new Item(Database.getItem(head));
		}

		if(chest != Const.INVALID_ID){
			slots[Equipment.slotChest] = new Item(Database.getItem(chest));
		}

		if(hand1 != Const.INVALID_ID){
			slots[Equipment.slotHand1] = new Item(Database.getItem(hand1));
		}

		if(hand2 != Const.INVALID_ID){
			slots[Equipment.slotHand2] = new Item(Database.getItem(hand2));
		}
	}
}
