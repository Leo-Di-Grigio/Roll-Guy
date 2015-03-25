package game.cycle.scene.game.world.creature.items;

import game.cycle.scene.game.world.database.Database;
import game.tools.Const;

import java.awt.Point;
import java.util.HashMap;

public class Inventory {

	private int sizeX;
	private int sizeY;
	
	private HashMap<Integer, Item> items;
	private HashMap<Integer, Point> itemsPos;
	private Item [][] slots;
	private int [][] space;
	private int mass;
	
	public Inventory(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		
		items = new HashMap<Integer, Item>();
		itemsPos = new HashMap<Integer, Point>();
		space = new int[sizeX][sizeY];
		slots = new Item[sizeX][sizeY];
		
		for(int i = 0; i < sizeX; ++i){
			for(int j = 0; j < sizeY; ++j){
				space[i][j] = Const.invalidId;
			}
		}
	}
	
	public int [] getIntArray(){
		int [] array = new int[items.size()*3 + 1]; // x, y, item base id
		
		array[0] = items.size();
		int i = 1;
		for(Item item: items.values()){
			Point pos = itemsPos.get(item.guid);;
			array[i] = item.proto.id;
			array[i+1] = pos.x;
			array[i+2] = pos.y;
			i += 3;
		}
		
		return array;
	}
	
	public void addItem(int id, int x, int y){
		Item item = new Item(Database.getItem(id));
		
		int maxX = Math.min(sizeX, x + item.proto.sizeX);
		int maxY = Math.min(sizeY, y + item.proto.sizeY);
		
		int freeVolume = 0;
		for(int i = x; i < maxX; ++i){
			for(int j = y; j < maxY; ++j){
				if(space[i][j] == Const.invalidId){
					freeVolume++;
				}
			}
		}
		
		if(freeVolume == item.proto.sizeX*item.proto.sizeY){
			for(int i = x; i < maxX; ++i){
				for(int j = y; j < maxY; ++j){
					space[i][j] = item.guid;
					slots[i][j] = item;
				}
			}
			
			items.put(item.guid, item);
			itemsPos.put(item.guid, new Point(x, y));
			mass += item.proto.mass;
		}
	}
	
	public boolean addItem(Item item){
		for(int j = 0; j < sizeY; ++j){
			for(int i = 0; i < sizeX; ++i){
				if(space[i][j] == Const.invalidId){
					int maxX = Math.min(sizeX, i + item.proto.sizeX);
					int maxY = Math.min(sizeY, j + item.proto.sizeY);
					
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
						itemsPos.put(item.guid, new Point(i, j));
						mass += item.proto.mass;
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public boolean dropItem(int slotX, int slotY, Item item) {
		if(slotX >= 0 && slotX < sizeX && slotY >= 0 && slotY < sizeY){
			int maxX = Math.min(sizeX, slotX + item.proto.sizeX);
			int maxY = Math.min(sizeY, slotY + item.proto.sizeY);
			
			int freeVolume = 0;
			for(int i = slotX; i < maxX; ++i){
				for(int j = slotY; j < maxY; ++j){
					if(space[i][j] == Const.invalidId){
						freeVolume++;
					}
				}
			}
			
			if(freeVolume == (item.proto.sizeX*item.proto.sizeY)){
				for(int i = slotX; i < maxX; ++i){
					for(int j = slotY; j < maxY; ++j){
						space[i][j] = item.guid;
						slots[i][j] = item;
					}
				}
			
				items.put(item.guid, item);
				itemsPos.put(item.guid, new Point(slotX, slotY));
				mass += item.proto.mass;
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}
	
	public Item pickItem(int slotX, int slotY){
		if(slotX >= 0 && slotX < sizeX && slotY >= 0 && slotY < sizeY){
			int guid = space[slotX][slotY];
			
			if(guid != Const.invalidId){
				Item item = items.remove(guid);
				itemsPos.remove(guid);
				
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

	public int getItemsCount() {
		return items.size();
	}
	
	public Point getItemPos(int guid){
		return itemsPos.get(guid);
	}

	public HashMap<Integer, Item> getItems() {
		return items;
	}
}
