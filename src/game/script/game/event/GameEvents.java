package game.script.game.event;

import game.cycle.scene.SceneMng;
import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.world.World;
import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.database.GameConst;
import game.cycle.scene.game.world.database.proto.LocationProto;
import game.cycle.scene.game.world.event.LocationEvent;
import game.cycle.scene.game.world.location.Location;
import game.cycle.scene.game.world.location.LocationObject;
import game.cycle.scene.game.world.location.creature.NPC;
import game.cycle.scene.game.world.location.go.GO;
import game.cycle.scene.game.world.skill.Skill;
import game.cycle.scene.ui.list.UIGame;
import game.script.game.effect.effect_Drag;

public class GameEvents {

	private static SceneGame game;
	private static World world;
	private static UIGame ui;
	
	public GameEvents(SceneGame scene, UIGame ui){
		GameEvents.game = scene;
		GameEvents.world = scene.getWorld();
		GameEvents.ui = ui;
		new GameConsole(world);
	}
	
	public static void teleport(LocationObject user, GO go) {
		int mapId = go.param1;
		int x = go.param2;
		int y = go.param3;
		
		LocationProto location = Database.getLocation(mapId);
		user.resetPath();
		if(location != null){
			game.loadLocation(mapId, x, y);
			
			if(user.getDraggedObject() != null){
				user.getDraggedObject().setPosition(x, y);
				user.getDraggedObject().setSpritePosition(x*GameConst.TILE_SIZE, y*GameConst.TILE_SIZE);
			}
		}
	}

	public static Location getLocation(){
		return world.getLocation();
	}
	
	// TURN SYSTEM
	public static void requestSwitchMode(boolean playerInit){
		world.requestSwitchMode(playerInit);
	}
	
	public static void requestTurnMode(boolean playerInit){
		world.requestTurnMode(playerInit);
	}
	
	public static void requestEndTurn(){
		world.requestEndTurn();
	}
	
	// INTERACTIVE
	public static void playerUseSelfCastSkill(Skill skill) {
		world.playerSelfCastSkill(skill);
	}
	
	public static void playerUseSkill(Skill skill){
		if(skill == null){
			ui.setMode(ui.getMode());
		}
		
		world.playerUseSkill(ui, skill);
	}

	public static void dialogBegin(NPC npc) {
		ui.npcTalk(npc);
		SceneMng.pause(true);
	}

	public static void dialogEnd() {
		SceneMng.pause(false);
	}

	// AI
	public static void addLocationEvent(LocationEvent event) {
		world.addLocationEvent(event);
	}
	
	// MISC
	public static void destroyed(LocationObject object){
		world.destroy(object);
	}

	public static void characterDragObject(LocationObject caster, LocationObject target) {
		effect_Drag.characterDragObject(world.getLocation(), caster, target);
	}

	public static void characterDropObject(LocationObject caster) {
		effect_Drag.characterDropObject(world.getLocation(), caster);
	}
}