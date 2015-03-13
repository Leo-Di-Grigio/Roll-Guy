package game.cycle.scene.game.world.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.badlogic.gdx.utils.Disposable;

import game.cycle.scene.game.world.go.GOProto;
import game.cycle.scene.game.world.map.Location;
import game.cycle.scene.game.world.map.LocationProto;
import game.cycle.scene.game.world.map.TerrainProto;
import game.tools.Log;

public class Database implements Disposable {
	// SQLite
	private static Connection connection;
	private static Statement state;
	
	// Bases
	private static HashMap<Integer, GOProto> go;
	private static HashMap<Integer, LocationProto> locations;
	private static HashMap<Integer, TerrainProto> terrain;
	
	public Database() {
		connect();
		
		// load data
		loadLocations();
		loadTerrain();
		loadGO();
	}

	// Get data
	public static LocationProto getLocation(int id) {
		return locations.get(id);
	}
	
	public static TerrainProto getTerrainProto(int id){
		return terrain.get(id);
	}
	
	public static GOProto getGO(int baseid){
		return go.get(baseid);
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
	
	// Insert
	public static void insertLocation(Location loc, String title, String file, String note){
		try {
			title = "'" + title + "'";
			file = "'" + file + "'";
			note = "'" + note + "'";
			
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
			state = connection.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM LOCATION;");
			
			while(result.next()) {
				LocationProto proto = new LocationProto();
				
				proto.id = result.getInt("id");
				proto.title = result.getString("title");
				proto.filePath = result.getString("file");
				proto.note = result.getString("note");
				
				locations.put(proto.id, proto);
			}
		}
		catch (SQLException e) {
			Log.err("SQLite error on load (DB:Locations)");
		}
	}

	private void loadTerrain() {
		terrain = new HashMap<Integer, TerrainProto>();
		try {
			state = connection.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM TERRAIN;");
			
			while(result.next()) {
				TerrainProto proto = new TerrainProto();
				proto.id = result.getInt("id");
				proto.texture = result.getInt("texture");
				proto.passable = result.getBoolean("passable");
				proto.title = result.getString("title");
				
				terrain.put(proto.id, proto);
			}
		}
		catch (SQLException e) {
			Log.err("SQLite error on load (DB:Terrain)");
		}
	}
	
	public static void loadGO() {
		go = new HashMap<Integer, GOProto>();
		try {
			state = connection.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM GO;");
			
			while(result.next()) {
				GOProto proto = new GOProto();
				proto.id = result.getInt("id");
				proto.title = result.getString("title");
				proto.texure = result.getInt("texture");
				proto.visible = result.getBoolean("visible");
				proto.trigger = result.getBoolean("trigger");
				proto.usable = result.getBoolean("usable");
				proto.container = result.getBoolean("container");
				proto.passable = result.getBoolean("passable");
				proto.durabilityMax = result.getInt("durability");
				
				go.put(proto.id, proto);
			}
		}
		catch (SQLException e) {
			Log.err("SQLite Error on load (DB:GO)");
		}
	}

	private void connect(){
		try {
			Class.forName("org.sqlite.JDBC");
			
			try {
				String classpath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
				classpath = classpath.substring(0, classpath.length() - 4); // remove /bin in classpath
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
		
	}
}
