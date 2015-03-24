package game.cycle.scene.game.world.creature.items;

import game.tools.Const;

import java.util.HashMap;

public class Inventory {

	private int sizeX;
	private int sizeY;
	
	private HashMap<Integer, Item> items;
	private Item [][] slots;
	private int [][] space;
	private int mass;
	
	public Inventory(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		
		items = new HashMap<Integer, Item>();
		space = new int[sizeX][sizeY];
		slots = new Item[sizeX][sizeY];
		
		for(int i = 0; i < sizeX; ++i){
			for(int j = 0; j < sizeY; ++j){
				space[i][j] = Const.invalidId;
			}
		}
	}
	
	public boolean addItem(Item item){
		for(int i = 0; i < sizeX; ++i){
			for(int j = 0; j < sizeY; ++j){
				if(space[i][j] == Const.invalidId){
					int maxX = Math.min(sizeX, i + item.proto.sizeX);
					int maxY = Math.min(sizeY, i + item.proto.sizeY);
					
					int freeVolume = 0;
					for(int x = i; x < maxX; ++x){
						for(int y = j; y < maxY; ++y){
							if(space[x][y] == Const.invalidId){
								freeVolume++;
							}
						}
					}
					
					if(freeVolume == (item.proto.sizeX*item.proto.sizeY)){
						for(int x = i; x < maxX; ++x){
							for(int y = j; y < maxY; ++y){
								space[x][y] = item.guid;
								slots[x][y] = item;
							}
						}
						
						items.put(item.guid, item);
						mass += item.proto.mass;
						return true;
					}
					else{
						return false;
					}
				}
			}
		}
		
		return false;
	}
	
	public Item removeItem(int slotX, int slotY){
		if(slotX >= 0 && slotX < sizeX && slotY >= 0 && slotY < sizeY){
			int guid = space[slotX][slotY];
			
			if(guid != Const.invalidId){
				Item item = items.remove(guid);
				
				for(int i = 0; i < sizeX; ++i){
					for(int j = 0; j < sizeY; ++j){
						if(space[i][j] == guid){
							space[i][j] = Const.invalidId;
						}
					}
				}
				
				mass -= item.proto.mass;
				return item;
			}
			else{
				return null;
			}
		}
		else{
			return null;
		}
	}
	
	public int getTotalMass(){
		return mass;
	}

	public Item getItem(int slotX, int slotY) {
		if(slotX >= 0 && slotX < sizeX && slotY >= 0 && slotY < sizeY){
			return items.get(space[slotX][slotY]);
		}
		else{
			return null;
		}
	}
}
