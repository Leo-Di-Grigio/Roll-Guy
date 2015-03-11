package game.cycle.scene.game.world.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.badlogic.gdx.utils.Disposable;

import game.cycle.scene.game.world.go.GOProto;
import game.tools.Log;

public class Database implements Disposable {
	// SQLite
	private static Connection connection;
	private static Statement state;
	
	// Bases
	private static HashMap<Integer, GOProto> go;
	private static HashMap<Integer, String> locations;
	
	public Database() {
		locations = new HashMap<Integer, String>();
		go = new HashMap<Integer, GOProto>();
		
		connect();
		
		// load data
		loadLocations();
		loadGO();
	}
	
	// Get data
	public static String getLocation(int id) {
		return locations.get(id);
	}
	
	public static GOProto getGO(int baseid){
		return go.get(baseid);
	}
	
	public static HashMap<Integer, GOProto> getBaseGO(){
		return go;
	}
	
	public static HashMap<Integer, String> getBaseLocations(){
		return locations;
	}
	
	// Loading
	public static void loadLocations(){
		try {
			state = connection.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM LOCATION;");
			
			while(result.next()) {
				int id = result.getInt("id");
				String file = result.getString("file");
				locations.put(id, file);
			}
		}
		catch (SQLException e) {
			Log.err("GOBase: Statement SQLite Error");
		}
	}
	
	public static void loadGO() {
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
			Log.err("GOBase: Statement SQLite Error");
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
