package game.cycle.scene.game.world.creature.items;

import game.tools.Const;

public class Equipment {

	public static final int slotHead = 1;
	public static final int slotChest = 2;
	public static final int slotHand1 = 3;
	public static final int slotHand2 = 4;

	public Item head;
	public Item chest;
	public Item hand1;
	public Item hand2;
	
	public Equipment() {
		
	}
	
	public int [] getIntArray(){
		int [] array = new int[4];
		
		array[0] = (head == null) ? Const.invalidId : head.proto.id;
		array[1] = (chest == null) ? Const.invalidId : chest.proto.id;
		array[2] = (hand1 == null) ? Const.invalidId : hand1.proto.id;
		array[3] = (hand2 == null) ? Const.invalidId : hand2.proto.id;
		
		return array;
	}
}
