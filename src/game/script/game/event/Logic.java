package game.script.game.event;

import game.cycle.scene.SceneMng;
import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.state.Globals;
import game.cycle.scene.game.state.State;
import game.cycle.scene.game.state.database.Database;
import game.cycle.scene.game.state.database.GameConst;
import game.cycle.scene.game.state.database.proto.LocationProto;
import game.cycle.scene.game.state.event.Event;
import game.cycle.scene.game.state.location.Location;
import game.cycle.scene.game.state.location.LocationObject;
import game.cycle.scene.game.state.location.creature.NPC;
import game.cycle.scene.game.state.location.creature.Player;
import game.cycle.scene.game.state.location.go.GO;
import game.cycle.scene.game.state.skill.Skill;
import game.cycle.scene.ui.list.UIGame;
import game.script.game.effect.effect_Drag;

public class Logic {

	private static SceneGame game;
	private static State state;
	private static UIGame ui;
	
	public Logic(SceneGame scene, UIGame ui){
		Logic.game = scene;
		Logic.state = scene.getState();
		Logic.ui = ui;
		new GameConsole(state);
	}
	
	// GET
	public static State getState(){
		return state;
	}
	
	public static Globals getGlobals() {
		return state.getGlobals();
	}
	
	public static Location getLocation(){
		return state.getLocation();
	}

	public static Player getPlayer() {
		return state.getPlayer();
	}

	public static UIGame getUI() {
		return ui;
	}
	
	// LOCATION
	public static void requestUpdate() {
		state.getLocation().requestUpdate();
	}
	
	// ACTION
	public static void teleport(LocationObject user, GO go) {
		int mapId = go.param1();
		int x = go.param2();
		int y = go.param3();
		teleport(user, mapId, x, y);
	}
	
	public static void teleport(LocationObject user, int mapId, int x, int y) {
		if(state.getLocation().proto.id() == mapId){
			state.getLocation().map[user.getPosition().x][user.getPosition().y].creature = null;
			user.setPosition(x, y);
			user.setSpritePosition(x*GameConst.TILE_SIZE, y*GameConst.TILE_SIZE);
		}
		else{
			LocationProto location = Database.getLocation(mapId);
			user.resetPath();
			if(location != null){
				game.loadLocation(mapId, x, y);
			}
		}
		
		if(user.getDraggedObject() != null){
			user.getDraggedObject().setPosition(x, y);
			user.getDraggedObject().setSpritePosition(x*GameConst.TILE_SIZE, y*GameConst.TILE_SIZE);
		}
		
		state.getLocation().requestUpdate();
	}
	
	// TURN SYSTEM
	public static void requestSwitchMode(boolean playerInit){
		state.requestSwitchMode(playerInit);
	}
	
	public static void requestTurnMode(boolean playerInit){
		state.requestTurnMode(playerInit);
	}
	
	public static void requestEndTurn(){
		state.requestEndTurn();
	}
	
	// INTERACTIVE
	public static void playerUseSelfCastSkill(Skill skill) {
		state.playerSelfCastSkill(skill);
	}
	
	public static void playerUseSkill(Skill skill){
		if(skill == null){
			ui.setMode(ui.getMode());
		}
		
		state.playerUseSkill(ui, skill);
	}

	public static void dialogBegin(NPC npc) {
		ui.npcTalk(npc);
		SceneMng.pause(true);
	}

	public static void dialogEnd() {
		SceneMng.pause(false);
	}

	// AI
	public static void addEvent(Event event) {
		getLocation().addEvent(event);
	}
	
	// MISC
	public static void destroyed(LocationObject object){
		getLocation().killObject(object);
	}

	public static void characterDragObject(LocationObject caster, LocationObject target) {
		effect_Drag.characterDragObject(state.getLocation(), caster, target);
	}

	public static void characterDropObject(LocationObject caster) {
		effect_Drag.characterDropObject(state.getLocation(), caster);
	}
}