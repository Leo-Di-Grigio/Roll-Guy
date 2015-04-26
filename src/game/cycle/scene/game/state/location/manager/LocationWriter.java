package game.cycle.scene.game.state.location.manager;

import game.cycle.scene.game.state.location.Location;
import game.cycle.scene.game.state.location.LocationObject;
import game.cycle.scene.game.state.location.Node;
import game.cycle.scene.game.state.location.creature.Creature;
import game.cycle.scene.game.state.location.creature.NPC;
import game.cycle.scene.game.state.location.go.GO;
import game.tools.Const;
import game.tools.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class LocationWriter {

	protected static void saveLocation(Location loc){
		try {
			File file = new File(LocationManager.locationPath + loc.proto.file() + LocationManager.locationFileExtension);
			if(!file.exists()){
				file.createNewFile();
			}
			
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
			writeLocation(loc, out);
			
			out.flush();
			out.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void writeLocation(Location loc, BufferedOutputStream out){
		try {
			// write process
			writeMetaData(loc, out);
			writeTerrain(loc, out);
			writeEnvironment(loc, out);
			writeGO(loc, out);
			writeCreatures(loc, out);
			
			// end writing
			Log.debug("Saving complete");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void writeMetaData(Location loc, BufferedOutputStream out) throws IOException{
		int capacity = Const.INTEGER_TYPE_SIZE * 3;
		ByteBuffer buffer = ByteBuffer.allocate(capacity);
		
		buffer.putInt(loc.proto.sizeX());
		buffer.putInt(loc.proto.sizeY());
		buffer.putInt(LocationObject.getStartGUID());

		// write
		byte [] data = buffer.array();
		out.write(data);
	}
	
	private static void writeTerrain(Location loc, BufferedOutputStream out) throws IOException{
		int nodeTypeSize = 1; // terrainId only
		int capacity = Const.INTEGER_TYPE_SIZE * loc.proto.sizeX() * loc.proto.sizeY() * nodeTypeSize;
		ByteBuffer buffer = ByteBuffer.allocate(capacity);
		
		// buffering
		for(int i = 0; i < loc.proto.sizeX(); ++i){
			for(int j = 0; j < loc.proto.sizeY(); ++j){
				Node node = loc.map[i][j];
				
				// terrain id
				buffer.putInt(node.proto.id());
			}
		}
		
		// write
		byte [] data = buffer.array();
		out.write(data);
	}
	
	private static void writeEnvironment(Location loc, BufferedOutputStream out) throws IOException {
		int [] arr = loc.getLight().getIntArray();
		int lightingFileFlag = 1;
		int count = 1;
		int meta = 1;
		int capacity = Const.INTEGER_TYPE_SIZE*(arr.length + count + lightingFileFlag + meta); // array size + array data + flag
		ByteBuffer buffer = ByteBuffer.allocate(capacity);
		
		// buffering
		buffer.putInt(LocationManager.LOCATION_ENVIRONMENT_DATA_BLOCK);
		buffer.putInt(loc.getLight().size());
		
		// meta
		buffer.putInt(loc.proto.light());
		
		for(int i = 0; i < arr.length; ++i){
			buffer.putInt(arr[i]);
		}
		
		// write
		byte [] data = buffer.array();
		out.write(data);
	}
	
	private static void writeGO(Location loc, BufferedOutputStream out) throws IOException{
		ArrayList<GO> goBuffer = new ArrayList<GO>();
		
		for(LocationObject obj: loc.getPermanentValues()){			
			if(obj.isGO()){
				goBuffer.add((GO)obj);
			}
		}
		
		int goData = 8; // guid, protoId, x, y, param1, param2, param3, param4
		int containerData = 0;
		int scriptDataChar = 0;
		int scriptDataInt = 0;
		
		// calculate all GO inventory sizes
		for(GO go: goBuffer){
			if(go.proto.container()){
				containerData += go.inventory.getItemsCount()*3 + 1; // baseid, x, y + inventorySize
			}
			else{
				++containerData; // inventorySize = Const.INVALID_ID
			}
			
			if(go.script != null && !go.script.equals("null")){
				scriptDataChar += go.script.length();
				++scriptDataInt;
			}
			else{
				++scriptDataInt;
			}
		}
		
		int goFileFlag = 1;
		int goCount = 1;
		int capacity = Const.INTEGER_TYPE_SIZE*(goBuffer.size()*goData + containerData + goFileFlag + goCount + scriptDataInt) + Const.CHAR_TYPE_SIZE*(scriptDataChar);
		ByteBuffer buffer = ByteBuffer.allocate(capacity);
		
		// buffering
		buffer.putInt(LocationManager.LOCATION_GO_DATA_BLOCK);
		buffer.putInt(goBuffer.size());
		
		for(GO go: goBuffer){		
			// go data
			buffer.putInt(go.getGUID());
			buffer.putInt(go.proto.id());
			buffer.putInt((int)(go.getPosition().x));
			buffer.putInt((int)(go.getPosition().y));
			buffer.putInt(go.param1()); // param1
			buffer.putInt(go.param2()); // param2
			buffer.putInt(go.param3()); // param3
			buffer.putInt(go.param4()); // param4
			
			// container data
			if(go.proto.container()){
				int [] inventory = go.inventory.getIntArray();
				
				buffer.putInt(inventory.length);
				for(int i = 0; i < inventory.length; i += 3){
					buffer.putInt(inventory[i]);
					buffer.putInt(inventory[i+1]);
					buffer.putInt(inventory[i+2]);
				}
			}
			else{
				buffer.putInt(Const.INVALID_ID);
			}
			
			// write script data
			if(go.script != null && !go.script.equals("null")){
				buffer.putInt(go.script.length());
				
				char [] arr = go.script.toCharArray();
				for(char ch: arr){
					buffer.putChar(ch);
				}
			}
			else{
				buffer.putInt(Const.INVALID_ID);
			}
		}
		
		// write
		byte [] data = buffer.array();
		out.write(data);
	}
	
	private static void writeCreatures(Location loc, BufferedOutputStream out) throws IOException{
		ArrayList<Creature> creatureBuffer = new ArrayList<Creature>();
		for(LocationObject obj: loc.getPermanentValues()){
			if(obj.isCreature()){
				creatureBuffer.add((Creature)obj);
			}
		}
		
		int creatureData = 14; // charGUID,x,y,protoId,str,agi,stamina,pre,int,will,equipment(head, chest, h1, h2)
		int containerData = 0;
		int wayPointsData = 0;
		
		for(Creature creature: creatureBuffer){
			containerData += creature.inventory.getItemsCount()*3 + 1; // baseid, x, y + inventorySize
			
			if(creature.isNPC()){
				NPC npc = (NPC)creature;
				wayPointsData += npc.aidata.getWayPointsIntArraySize();
			}
			else{
				++wayPointsData; // wayPointsSize = Const.invalidId
			}
		}
		
		int creatureFileFlag = 1;
		int creatureCount = 1;
		int capacity = Const.INTEGER_TYPE_SIZE*(creatureBuffer.size()*creatureData + containerData + wayPointsData + creatureFileFlag + creatureCount);
		ByteBuffer buffer = ByteBuffer.allocate(capacity);
		
		// buffering
		buffer.putInt(LocationManager.LOCATION_CREATURE_DATA_BLOCK);
		buffer.putInt(creatureBuffer.size());
		
		for(Creature creature: creatureBuffer){
			// write creature
			buffer.putInt(creature.getGUID());
			buffer.putInt((int)(creature.getSpawnPosition().x));
			buffer.putInt((int)(creature.getSpawnPosition().y));
			buffer.putInt(creature.proto().id());
			
			// write equipment
			int [] equpment = creature.equipment().getIntArray();
			for(int i = 0; i < equpment.length; ++i){
				buffer.putInt(equpment[i]);
			}
			
			// write inventory
			int [] inventory = creature.inventory.getIntArray();
			buffer.putInt(creature.inventory.getItemsCount());
			
			for(int i = 0; i < inventory.length; ++i){
				buffer.putInt(inventory[i]);
			}
			
			// write waypoints
			NPC npc = (NPC)creature;
			if(npc.aidata.getWayPointsIntArraySize() == 0){
				buffer.putInt(Const.INVALID_ID);
			}
			else{
				buffer.putInt(npc.aidata.getWayPointsIntArraySize());
				
				int [] arr = npc.aidata.getWayPointsIntArray();
				for(int i = 0; i < arr.length; ++i){
					buffer.putInt(arr[i]);
				}
			}
		}
		
		// write
		byte [] data = buffer.array();
		out.write(data);
	}
}