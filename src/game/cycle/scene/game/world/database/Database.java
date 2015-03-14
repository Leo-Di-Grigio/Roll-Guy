package game.cycle.scene.game.world.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.badlogic.gdx.utils.Disposable;

import game.cycle.scene.game.world.creature.CreatureProto;
import game.cycle.scene.game.world.creature.Stats;
import game.cycle.scene.game.world.go.GOProto;
import game.cycle.scene.game.world.map.LocationProto;
import game.cycle.scene.game.world.map.TerrainProto;
import game.tools.Log;

public class Database implements Disposable {
	// SQLite
	private static Connection connection;
	
	// Bases
	private static HashMap<Integer, GOProto> go;
	private static HashMap<Integer, LocationProto> locations;
	private static HashMap<Integer, TerrainProto> terrain;
	private static HashMap<Integer, CreatureProto> creature;
	
	public Database() {
		connect();
		
		// load data
		loadLocations();
		loadTerrain();
		loadGO();
		loadCreatures();
	}

	// Get data
	public static LocationProto getLocation(int id) {
		return locations.get(id);
	}
	
	public static TerrainProto getTerrainProto(int id){
		return terrain.get(id);
	}
	
	public static GOProto getGO(int id){
		return go.get(id);
	}
	
	public static CreatureProto getCreature(int id){
		return creature.get(id);
	}
	
	public static HashMap<Integer, GOProto> getBaseGO(){
		return go;
	}
	
	public static HashMap<Integer, LocationProto> getBaseLocations(){
		return locations;
	}
	
	public static HashMap<Integer, TerrainProto> getBaseTerrain(){
		return terrain;
	}
	
	public static HashMap<Integer, CreatureProto> getBaseCreature() {
		return creature;
	}
	
	// Insert
	public static void insertLocation(LocationProto proto){
		try {
			String title = "'" + proto.title + "'";
			String file = "'" + proto.filePath + "'";
			String note = "'" + proto.note + "'";
			
			Statement state = connection.createStatement();
			String sql = "INSERT INTO LOCATION (TITLE,FILE,NOTE) " +
	                     "VALUES ("+title+","+file+","+note+");";
			
			state.executeUpdate(sql);
			state.close();
			connection.commit();
		}
		catch (SQLException e) {
			Log.err("SQLite error on insert (DB:Locations)");
		}
	}
	
	public static void insertCreature(CreatureProto creature){
		try {
			int id = creature.id;
			String name = "'" + creature.name + "'";
			int strength = creature.stats.strength;
			int agility = creature.stats.agility;
			int stamina = creature.stats.stamina;
			int perception = creature.stats.perception;
			int intelligence = creature.stats.intelligence;
			int willpower = creature.stats.willpower;
			int texture = creature.texture;
			
			Statement state = connection.createStatement();
			
			String sql = "UPDATE CREATURE set "
					+ " NAME = "+name
					+", STRENGTH = "+strength
					+", AGILITY = "+agility
					+", STAMINA = "+stamina
					+", PERCEPTION = "+perception
					+", INTELLIGENCE = "+intelligence
					+", WILLPOWER = "+willpower
					+", TEXTURE = "+texture+""
					+ " where ID="+id+";";
			
			state.executeUpdate(sql);
			state.close();
			connection.commit();
		}
		catch (SQLException e) {
			Log.err("SQLite error on insert (DB:Creature)");
			e.printStackTrace();
		}
	}
	
	// Delete
	public static void deleteLocation(int id){
		try {
			Statement state = connection.createStatement();
			String sql = "DELETE from LOCATION where ID = " + id + ";";
	    	state.executeUpdate(sql);
	    	state.close();
	    	connection.commit();
		}
		catch (SQLException e) {
			Log.err("SQLite error on delete (DB:Locations)");
		}
	}
	
	// Loading
	public static void loadLocations(){
		locations = new HashMap<Integer, LocationProto>();
		try {
			Statement state = connection.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM LOCATION;");
			
			while(result.next()) {
				LocationProto proto = new LocationProto();
				
				proto.id = result.getInt("id");
				proto.title = result.getString("title");
				proto.filePath = result.getString("file");
				proto.note = result.getString("note");
				
				locations.put(proto.id, proto);
			}
			
			state.close();
		}
		catch (SQLException e) {
			Log.err("SQLite error on load (DB:Locations)");
		}
	}

	private void loadTerrain() {
		terrain = new HashMap<Integer, TerrainProto>();
		try {
			Statement state = connection.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM TERRAIN;");
			
			while(result.next()) {
				TerrainProto proto = new TerrainProto();
				proto.id = result.getInt("id");
				proto.texture = result.getInt("texture");
				proto.passable = result.getBoolean("passable");
				proto.title = result.getString("title");
				
				terrain.put(proto.id, proto);
			}
			
			state.close();
		}
		catch (SQLException e) {
			Log.err("SQLite error on load (DB:Terrain)");
		}
	}
	
	public static void loadGO() {
		go = new HashMap<Integer, GOProto>();
		try {
			Statement state = connection.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM GO;");
			
			while(result.next()) {
				GOProto proto = new GOProto();
				proto.id = result.getInt("id");
				proto.title = result.getString("title");
				proto.texure_1 = result.getInt("texture_first");
				proto.texure_2 = result.getInt("texture_second");
				proto.visible = result.getBoolean("visible");
				proto.trigger = result.getBoolean("trigger");
				proto.usable = result.getBoolean("usable");
				proto.container = result.getBoolean("container");
				proto.passable = result.getBoolean("passable");
				proto.durabilityMax = result.getInt("durability");
				proto.scriptId_1 = result.getInt("script_1");
				
				go.put(proto.id, proto);
			}
			
			state.close();
		}
		catch (SQLException e) {
			Log.err("SQLite Error on load (DB:GO)");
		}
	}

	private static void loadCreatures() {
		creature = new HashMap<Integer, CreatureProto>();
		
		try {
			Statement state = connection.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM CREATURE;");
			
			while(result.next()) {
				CreatureProto proto = new CreatureProto();
				proto.id = result.getInt("id");
				proto.name = result.getString("name");
				proto.texture = result.getInt("texture");
				
				Stats stats = new Stats(0);
				stats.strength = result.getInt("strength");
				stats.agility = result.getInt("agility");
				stats.stamina = result.getInt("stamina");
				stats.perception = result.getInt("perception");
				stats.intelligence = result.getInt("intelligence");
				stats.willpower = result.getInt("willpower");
				
				proto.stats = stats;
				
				creature.put(proto.id, proto);
			}
			
			state.close();
		}
		catch (SQLException e) {
			Log.err("SQLite Error on load (DB:Creture)");
		}
	}

	public static void updateCreatures() {
		try {
			Statement state = connection.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM CREATURE;");
			
			while(result.next()) {
				int id = result.getInt("id");
				
				if(creature.containsKey(id)){
					CreatureProto proto = creature.get(id);
					
					proto.name = result.getString("name");
					proto.texture = result.getInt("texture");
					
					Stats stats = new Stats(0);
					stats.strength = result.getInt("strength");
					stats.agility = result.getInt("agility");
					stats.stamina = result.getInt("stamina");
					stats.perception = result.getInt("perception");
					stats.intelligence = result.getInt("intelligence");
					stats.willpower = result.getInt("willpower");
					
					proto.stats = stats;
				}
				else{
					Log.debug("Creature " + id + " deleted from Databse");
				}
			}
			
			state.close();
		}
		catch (SQLException e) {
			Log.err("SQLite Error on load (DB:Creture)");
		}
	}
	
	// Connections
	private void connect(){
		try {
			Class.forName("org.sqlite.JDBC");
			
			try {
				String classpath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
				
				if(!classpath.startsWith(".")){
					classpath = classpath.substring(0, classpath.length() - 4); // remove /bin in classpath
				}
				
				String path = "jdbc:sqlite:" + classpath + "data/data.db";
				connection = DriverManager.getConnection(path);
				connection.setAutoCommit(false);
				
				Log.debug("SQLite connection succesful");
			}
			catch (SQLException e) {
				Log.err("SQLite connection error");
			}
		}
		catch (ClassNotFoundException e) {
			Log.err("SQLite driver error");
		}
	}

	@Override
	public void dispose() {
		try {
			connection.close();
		} 
		catch (SQLException e) {
			Log.err("SQLite connection close error");
		}
	}
}