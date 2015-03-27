package game.cycle.scene.game.world.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.badlogic.gdx.utils.Disposable;

import game.cycle.scene.game.world.creature.CreatureProto;
import game.cycle.scene.game.world.creature.items.ItemProto;
import game.cycle.scene.game.world.creature.struct.Stats;
import game.cycle.scene.game.world.dialog.DialogProto;
import game.cycle.scene.game.world.go.GOProto;
import game.cycle.scene.game.world.map.LocationProto;
import game.cycle.scene.game.world.map.TerrainProto;
import game.cycle.scene.game.world.skill.Skill;
import game.resources.Resources;
import game.resources.Tex;
import game.tools.Log;

public class Database implements Disposable {
	// SQLite
	private static Connection connection;
	
	// Bases
	private static HashMap<Integer, GOProto> go;
	private static HashMap<Integer, LocationProto> locations;
	private static HashMap<Integer, TerrainProto> terrain;
	private static HashMap<Integer, CreatureProto> creature;
	private static HashMap<Integer, DialogProto> dialog;
	private static HashMap<Integer, Skill> skills;
	private static HashMap<Integer, ItemProto> items;
	
	public Database() {
		connect();
		
		// load data
		loadLocations();
		loadTerrain();
		loadGO();
		loadCreatures();
		loadDialogs();
		loadSkills();
		loadItems();
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
	
	public static DialogProto getDialog(int id) {
		return dialog.get(id);
	}
	
	public static Skill getSkill(int id) {
		return skills.get(id);
	}
	
	public static ItemProto getItem(int id){
		return items.get(id);
	}
	
	// Get base
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
	
	public static HashMap<Integer, DialogProto> getBaseDialog(){
		return dialog;
	}
	
	public static HashMap<Integer, ItemProto> getBaseItems(){
		return items;
	}
	
	// Insert
	public static void insertLocation(LocationProto proto){
		try {
			int id = proto.id;
			String title = "'" + proto.title + "'";
			String file = "'" + proto.filePath + "'";
			String note = "'" + proto.note + "'";
			
			Statement state = connection.createStatement();
			String sql = "INSERT INTO LOCATION (ID,TITLE,FILE,NOTE) " +
	                     "VALUES ("+id+","+title+","+file+","+note+");";
			
			state.executeUpdate(sql);
			state.close();
			connection.commit();
		}
		catch (SQLException e) {
			Log.err("SQLite error on insert (DB:Locations)");
		}
	}

	public static void insertCreature(CreatureProto creature) {
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
			int fraction = creature.fraction;
			
			Statement state = connection.createStatement();
			
			String sql = "INSERT INTO CREATURE (ID,NAME,STRENGTH,AGILITY,STAMINA,PERCEPTION,INTELLIGENCE,WILLPOWER,TEXTURE,FRACTION) " +
                    "VALUES ("+id+","+name+","+strength+","+agility+","+stamina+","+perception+","+intelligence+","+willpower+","+texture+","+fraction+");";
			
			state.executeUpdate(sql);
			state.close();
			connection.commit();
		}
		catch (SQLException e) {
			Log.err("SQLite error on insert (DB:Creature)");
			e.printStackTrace();
		}
	}
	
	// Update
	public static void updateCreature(CreatureProto creature){
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
			int fraction = creature.fraction;
			
			Statement state = connection.createStatement();
			
			String sql = "UPDATE CREATURE set "
					+ " NAME = "+name
					+", STRENGTH = "+strength
					+", AGILITY = "+agility
					+", STAMINA = "+stamina
					+", PERCEPTION = "+perception
					+", INTELLIGENCE = "+intelligence
					+", WILLPOWER = "+willpower
					+", TEXTURE = "+texture
					+", FRACTION = "+fraction
					+ " where ID="+id+";";
			
			state.executeUpdate(sql);
			state.close();
			connection.commit();
		}
		catch (SQLException e) {
			Log.err("SQLite error on update (DB:Creature)");
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
				proto.losBlock = result.getBoolean("los_block");
				
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
				proto.scriptId = result.getInt("script_1");
				proto.fraction = result.getInt("fraction");
				proto.containerSizeX = result.getInt("container_size_x");
				proto.containerSizeY = result.getInt("container_size_y");
				proto.losBlock = result.getBoolean("los_block");
				
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
				proto.fraction = result.getInt("fraction");
				proto.dialogStart = result.getInt("dialog_start");
				proto.leaveCorpse = result.getBoolean("leave_a_corpse");
				
				String dialogs = result.getString("dialog_topics");
				if(dialogs != null && !dialogs.equals("NULL")){
					String [] dialogTopics = dialogs.split(";");
					int [] dialogTopicsIds = new int[dialogTopics.length];
					for(int i = 0; i < dialogTopics.length; ++i){
						dialogTopicsIds[i] = Integer.parseInt(dialogTopics[i]);
					}
					proto.dialogTopics = dialogTopicsIds;
				}
				else{
					proto.dialogTopics = new int[0];
					Log.err("SQLite Warning on load (DB:Creature), creature ID: " + proto.id + " no have dialogs");
				}
						
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
			Log.err("SQLite Error on load (DB:Creature)");
		}
	}

	public static void updateCreatures() {
		try {
			Statement state = connection.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM CREATURE;");
			
			while(result.next()) {
				int id = result.getInt("id");
				
				CreatureProto proto = null;
				
				if(creature.containsKey(id)){
					proto = creature.get(id);
				}
				else{
					proto = new CreatureProto();
					proto.id = id;
					creature.put(proto.id, proto);
				}
				
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
			
			state.close();
		}
		catch (SQLException e) {
			Log.err("SQLite Error on load (DB:Creture)");
		}
	}
	
	private void loadDialogs() {
		dialog = new HashMap<Integer, DialogProto>();
		
		try {
			Statement state = connection.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM DIALOG;");
			
			while(result.next()) {
				DialogProto proto = new DialogProto();
				proto.id = result.getInt("id");
				proto.title = result.getString("title");
				proto.textBegin = result.getString("textBegin");
				proto.textEnd = result.getString("textEnd");
				
				dialog.put(proto.id, proto);
			}
			
			state.close();
		}
		catch (SQLException e) {
			Log.err("SQLite Error on load (DB:Dialog)");
		}
	}

	private void loadSkills() {
		skills = new HashMap<Integer, Skill>();
		
		try {
			Statement state = connection.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM SKILLS;");
			
			while(result.next()) {
				Skill skill = new Skill();
				skill.id = result.getInt("id");
				skill.title = result.getString("title");
				skill.type = result.getInt("type");
				skill.ap = result.getInt("ap");
				skill.range = result.getFloat("range");
				skill.tex = Resources.getTex(Tex.skill + result.getInt("icon"));
			
				int effect1 = result.getInt("effect_1");
				int effect2 = result.getInt("effect_2");
				int effect3 = result.getInt("effect_3");
				int effect4 = result.getInt("effect_4");
				skill = SkillLoader.build(skill, effect1, effect2, effect3, effect4);
				
				skills.put(skill.id, skill);
			}
			
			state.close();
		}
		catch (SQLException e) {
			Log.err("SQLite Error on load (DB:Skills)");
		}
	}

	private void loadItems() {
		items = new HashMap<Integer, ItemProto>();
		
		try {
			Statement state = connection.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM ITEMS;");
			
			while(result.next()) {
				ItemProto proto = new ItemProto();
				proto.id = result.getInt("id");
				proto.type = result.getInt("type");
				proto.sizeX = result.getInt("sizeX");
				proto.sizeY = result.getInt("sizeY");
				proto.mass = result.getInt("mass");
				proto.title = result.getString("title");
				proto.tex = result.getInt("texture");
				proto.stackble = result.getBoolean("stackble");
				proto.stackcount = result.getInt("stack_count");
				
				items.put(proto.id, proto);
			}
			
			state.close();
		}
		catch (SQLException e) {
			Log.err("SQLite Error on load (DB:Items)");
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