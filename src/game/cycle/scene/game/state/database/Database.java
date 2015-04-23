package game.cycle.scene.game.state.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Set;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.utils.Disposable;

import game.cycle.scene.game.state.database.proto.CreatureProto;
import game.cycle.scene.game.state.database.proto.DialogProto;
import game.cycle.scene.game.state.database.proto.GOProto;
import game.cycle.scene.game.state.database.proto.ItemProto;
import game.cycle.scene.game.state.database.proto.LocationProto;
import game.cycle.scene.game.state.database.proto.NodeProto;
import game.cycle.scene.game.state.location.creature.struct.Stats;
import game.cycle.scene.game.state.skill.Skill;
import game.cycle.scene.game.state.skill.SkillLoader;
import game.resources.Resources;
import game.resources.tex.Tex;
import game.script.game.effect.effect_Damage;
import game.tools.Log;

public class Database implements Disposable {
	// SQLite
	private static Connection connection;
	
	// Bases
	private static HashMap<Integer, GOProto> go;
	private static HashMap<Integer, LocationProto> locations;
	private static HashMap<Integer, NodeProto> terrain;
	private static HashMap<Integer, CreatureProto> creature;
	private static HashMap<Integer, String> dialogPermanent;
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
		loadDialogsPermanent();
		loadDialogs();
		loadSkills();
		loadItems();
	}

	// Get data
	public static LocationProto getLocation(int id) {
		return locations.get(id);
	}
	
	public static NodeProto getTerrainProto(int id){
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
	
	public static HashMap<Integer, NodeProto> getBaseTerrain(){
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
	
	public static Set<Integer> getPermanentDialogKeys(){
		return dialogPermanent.keySet();
	}
	
	// Insert
	public static void insertLocation(LocationProto proto){
		try {
			int id = proto.id();
			String title = "'" + proto.title() + "'";
			String file = "'" + proto.file() + "'";
			String note = "'" + proto.note() + "'";
			String eventScript = "'" + proto.eventScript() + "'";
			
			Statement state = connection.createStatement();
			String sql = "INSERT INTO LOCATION (ID,TITLE,FILE,NOTE,EVENT_SCRIPT) " +
	                     "VALUES ("+id+","+title+","+file+","+note+","+eventScript+");";
			
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
			int id = creature.id();
			String name = "'" + creature.name() + "'";
			int strength = creature.stats().strength;
			int agility = creature.stats().agility;
			int stamina = creature.stats().stamina;
			int perception = creature.stats().perception;
			int intelligence = creature.stats().intelligence;
			int willpower = creature.stats().willpower;
			int texture = creature.tex();
			int fraction = creature.fraction();
			
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
			int id = creature.id();
			String name = "'" + creature.name() + "'";
			int strength = creature.stats().strength;
			int agility = creature.stats().agility;
			int stamina = creature.stats().stamina;
			int perception = creature.stats().perception;
			int intelligence = creature.stats().intelligence;
			int willpower = creature.stats().willpower;
			int texture = creature.tex();
			int fraction = creature.fraction();
			
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
				int id = result.getInt("id");
				String title = result.getString("title");
				String file = result.getString("file");
				String note = result.getString("note");
				String eventScript = result.getString("event_script");
				
				if(eventScript != null && (eventScript.equals("") || eventScript.equals("null"))){
					eventScript = null;
				}
				
				LocationProto proto = new LocationProto(id, title, file, note, eventScript);
				locations.put(proto.id(), proto);
			}
			
			state.close();
		}
		catch (SQLException e) {
			Log.err("SQLite error on load (DB:Locations)");
		}
	}

	private void loadTerrain() {
		terrain = new HashMap<Integer, NodeProto>();
		try {
			Statement state = connection.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM TERRAIN;");
			
			while(result.next()) {
				int id = result.getInt("id");
				int texture = result.getInt("texture");
				int atlasId = result.getInt("atlas_id");
				int soundMush = result.getInt("sound_mush");
				String title = result.getString("title");
				boolean passable = result.getBoolean("passable");
				boolean losBlock = result.getBoolean("los_block");
				
				NodeProto proto = new NodeProto(id, texture, atlasId, soundMush, title, passable, losBlock);
				
				terrain.put(proto.id(), proto);
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
				int id = result.getInt("id");
				String title = result.getString("title");
				int tex1 = result.getInt("texture");
				int durabilityMax = result.getInt("durability");
				int fraction = result.getInt("fraction");
				int containerSizeX = result.getInt("container_size_x");
				int containerSizeY = result.getInt("container_size_y");
				int lightPower = result.getInt("lighting_power");
				
				boolean visible = result.getBoolean("visible");
				boolean trigger = result.getBoolean("trigger");
				boolean teleport = result.getBoolean("teleport");
				boolean usable = result.getBoolean("usable");
				boolean container = result.getBoolean("container");
				boolean passable = result.getBoolean("passable");
				boolean los = result.getBoolean("los_block");
				boolean dragble = result.getBoolean("dragble");
				boolean light = result.getBoolean("lighting");
				
				String script = result.getString("lua_script");
				int sizeX = result.getInt("size_x");
				int sizeY = result.getInt("size_y");
				
				GOProto proto = new GOProto(id, title, tex1, durabilityMax, fraction, containerSizeX,
											containerSizeY, lightPower, visible, trigger, teleport,
											usable, container, passable, los, dragble, light,
											script, sizeX, sizeY);
				go.put(proto.id(), proto);
			}
			
			state.close();
		}
		catch (SQLException e) {
			Log.err("SQLite Error on load (DB:GO)");
		}
	}

	public static void loadCreatures() {
		creature = new HashMap<Integer, CreatureProto>();
		
		try {
			Statement state = connection.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM CREATURE;");
			
			while(result.next()) {
				
				int id = result.getInt("id");
				int race = result.getInt("race");
				String name = result.getString("name");
				int tex = result.getInt("texture");
				int fraction = result.getInt("fraction");
				int dialogStart = result.getInt("dialog_start");
				boolean leaveCorpse = result.getBoolean("leave_a_corpse");
				
				String dialogs = result.getString("dialog_topics");
				dialogs.replace("\\s+", ""); // remove all whitespaces
				int [] dialogTopics = new int[0];
				
				if(dialogs != null && !dialogs.equals("NULL")){					
					String [] dialogTopicsData = dialogs.split(";");
					int [] dialogTopicsIds = new int[dialogTopicsData.length];
					
					for(int i = 0; i < dialogTopicsIds.length; ++i){
						dialogTopicsIds[i] = Integer.parseInt(dialogTopicsData[i]);
					}
					
					dialogTopics = dialogTopicsIds;
				}
				else{
					Log.err("SQLite Warning on load (DB:Creature), creature ID: " + id + " no have dialogs");
				}
						
				Stats stats = new Stats(0);
				stats.strength = result.getInt("strength");
				stats.agility = result.getInt("agility");
				stats.stamina = result.getInt("stamina");
				stats.perception = result.getInt("perception");
				stats.intelligence = result.getInt("intelligence");
				stats.willpower = result.getInt("willpower");
				
				CreatureProto proto = new CreatureProto(id, name, tex, fraction, dialogStart, leaveCorpse, race, dialogTopics, stats);
				creature.put(proto.id(), proto);
			}
			
			state.close();
		}
		catch (SQLException e) {
			Log.err("SQLite Error on load (DB:Creature)");
		}
	}
	
	private void loadDialogsPermanent() {
		dialogPermanent = new HashMap<Integer, String>();
		
		try {
			Statement state = connection.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM PERMANENT_DIALOGS;");
			
			while(result.next()) {
				int id = result.getInt("id");
				String title = result.getString("title");
				dialogPermanent.put(id, title);
			}
			
			state.close();
		}
		catch (SQLException e) {
			Log.err("SQLite Error on load (DB:Permanent_Dialog)");
		}
	}
	
	private void loadDialogs() {
		dialog = new HashMap<Integer, DialogProto>();
		
		try {
			Statement state = connection.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM DIALOG;");
			
			while(result.next()) {
				int id = result.getInt("id");
				int permanentId = result.getInt("permanent_id");
				String title = result.getString("title");
				String textBegin = result.getString("textBegin");
				String textEnd = result.getString("textEnd");
				String script = result.getString("lua_script");
				
				DialogProto proto = new DialogProto(id, permanentId, title, textBegin, textEnd, script);
				dialog.put(id, proto);
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
				skill.tex = Resources.getTex(Tex.SKILL_NULL + result.getInt("icon"));
				skill.tooltip = result.getString("tooltip");
				
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
				int id = result.getInt("id");
				int sizeX = result.getInt("sizeX");
				int sizeY = result.getInt("sizeY");
				int mass = result.getInt("mass");
				int slot = result.getInt("type");
				int tex = result.getInt("texture");
				String title = result.getString("title");
				boolean stackble = result.getBoolean("stackble");
				int stackcount = result.getInt("stack_count");
				
				ItemProto proto = new ItemProto(id, sizeX, sizeY, mass, slot, tex, title, stackble, stackcount);
				items.put(proto.id(), proto);
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