package game.script.game.event;

import tools.Const;
import cycle.scene.SceneMng;
import game.SceneGame;
import game.cycle.scene.ui.list.UIGame;
import game.script.game.effect.effect_Drag;
import game.state.Globals;
import game.state.State;
import game.state.database.Database;
import game.state.database.proto.LocationProto;
import game.state.event.Event;
import game.state.location.Location;
import game.state.location.LocationObject;
import game.state.location.creature.NPC;
import game.state.location.creature.Player;
import game.state.location.go.GO;
import game.state.skill.Skill;

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
			user.setSpritePosition(x*Const.TILE_SIZE, y*Const.TILE_SIZE);
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
			user.getDraggedObject().setSpritePosition(x*Const.TILE_SIZE, y*Const.TILE_SIZE);
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