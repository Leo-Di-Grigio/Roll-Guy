package game.cycle.scene.game.world.creature.items;

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
				array[i] = Const.invalidId;
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
}
