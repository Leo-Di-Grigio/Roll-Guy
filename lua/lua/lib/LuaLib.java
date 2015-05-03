package lua.lib;

import game.cycle.scene.game.state.event.Event;
import game.tools.Const;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import resources.tex.Tex;

public class LuaLib {

	public static LuaValue getGlobals(){
		LuaValue globals = JsePlatform.standardGlobals();
		initConsts(globals);
		return globals;
	}

	private static void initConsts(LuaValue globals) {
		initClasses(globals);
		initConstants(globals);
		initResourcesConst(globals);
		initEventConst(globals);
	}

	private static void initClasses(LuaValue globals) {
		globals.set("console", CoerceJavaToLua.coerce(new LuaLibConsole()));
		globals.set("game", CoerceJavaToLua.coerce(new LuaLibGame()));
		globals.set("location", CoerceJavaToLua.coerce(new LuaLibLocation()));
		globals.set("global", CoerceJavaToLua.coerce(new LuaLibGlobal()));
		globals.set("ui", CoerceJavaToLua.coerce(new LuaLibUI()));
		globals.set("ai", CoerceJavaToLua.coerce(new LuaLibAI()));
	}

	private static void initConstants(LuaValue globals){
		globals.set("INVALID_ID", LuaValue.valueOf(Const.INVALID_ID));
	}
	
	private static void initResourcesConst(LuaValue globals) {
		globals.set("TEX_NULL", LuaValue.valueOf(Tex.NULL));
		
		globals.set("TEX_UI_BUTTON_NORMAL", LuaValue.valueOf(Tex.UI_BUTTON_NORMAL));
		globals.set("TEX_UI_BUTTON_SELECTED", LuaValue.valueOf(Tex.UI_BUTTON_SELECTED));
		globals.set("TEX_UI_BUTTON_CLICK", LuaValue.valueOf(Tex.UI_BUTTON_CLICK));
		globals.set("TEX_UI_BACKGROUND_NORMAL", LuaValue.valueOf(Tex.UI_BACKGROUND_NORMAL));
		globals.set("TEX_UI_BACKGROUND_SELECTED", LuaValue.valueOf(Tex.UI_BACKGROUND_SELECTED));
		globals.set("TEX_UI_BACKGROUND_SELECTED_LIGHT", LuaValue.valueOf(Tex.UI_BACKGROUND_SELECTED_LIGHT));
		globals.set("TEX_UI_LIST_LINE", LuaValue.valueOf(Tex.UI_LIST_LINE));
		globals.set("TEX_UI_INVENTORY_SLOT", LuaValue.valueOf(Tex.UI_INVENTORY_SLOT));
		globals.set("TEX_UI_INVENTORY_SLOT_HELMET", LuaValue.valueOf(Tex.UI_INVENTORY_SLOT_HELMET));
		globals.set("TEX_UI_INVENTORY_SLOT_CHEST", LuaValue.valueOf(Tex.UI_INVENTORY_SLOT_CHEST));
		globals.set("TEX_UI_INVENTORY_SLOT_HAND_1", LuaValue.valueOf(Tex.UI_INVENTORY_SLOT_HAND_1));
		globals.set("TEX_UI_INVENTORY_SLOT_HAND_2", LuaValue.valueOf(Tex.UI_INVENTORY_SLOT_HAND_2));
		globals.set("TEX_UI_SKILL_FRAME", LuaValue.valueOf(Tex.UI_SKILL_FRAME));
		globals.set("TEX_UI_BACKGROUND_INFORMATION;", LuaValue.valueOf(Tex.UI_BACKGROUND_INFORMATION));
		
		globals.set("TEX_GAMEPLAY_SELECT", LuaValue.valueOf(Tex.GAMEPLAY_SELECT));
		globals.set("TEX_GAMEPLAY_WP", LuaValue.valueOf(Tex.GAMEPLAY_WP));
		
		globals.set("TEX_TILE_NULL", LuaValue.valueOf(Tex.TILE_NULL));
		globals.set("TEX_TILE_GRASS", LuaValue.valueOf(Tex.TILE_GRASS));
		globals.set("TEX_TILE_WALL", LuaValue.valueOf(Tex.TILE_WALL));
		globals.set("TEX_TILE_WATER", LuaValue.valueOf(Tex.TILE_WATER));
		globals.set("TEX_TILE_ALUMINA", LuaValue.valueOf(Tex.TILE_ALUMINA));
		globals.set("TEX_TILE_CRACKED_ALUMINA", LuaValue.valueOf(Tex.TILE_CRACKED_ALUMINA));
		globals.set("TEX_TILE_STONE", LuaValue.valueOf(Tex.TILE_STONE));
		globals.set("TEX_TILE_SAND_STONE_COAST", LuaValue.valueOf(Tex.TILE_SAND_STONE_COAST));
		globals.set("TEX_TILE_RIVER", LuaValue.valueOf(Tex.TILE_RIVER));
		globals.set("TEX_TILE_SAND", LuaValue.valueOf(Tex.TILE_SAND));
		globals.set("TEX_TILE_SAND_STONE", LuaValue.valueOf(Tex.TILE_SAND_STONE));
		
		globals.set("TEX_LIGHT", LuaValue.valueOf(Tex.LIGHT));
		
		globals.set("TEX_CREATURE_0", LuaValue.valueOf(Tex.CREATURE_0));
		globals.set("TEX_CREATURE_1", LuaValue.valueOf(Tex.CREATURE_1));
		globals.set("TEX_CREATURE_2", LuaValue.valueOf(Tex.CREATURE_2));
		
		globals.set("TEX_AVATAR_0", LuaValue.valueOf(Tex.AVATAR_0));
		
		globals.set("TEX_GO_NULL", LuaValue.valueOf(Tex.GO_NULL));
		globals.set("TEX_GO_DOOR_OPEN", LuaValue.valueOf(Tex.GO_DOOR_OPEN));
		globals.set("TEX_GO_DOOR_CLOSE", LuaValue.valueOf(Tex.GO_DOOR_CLOSE));
		globals.set("TEX_GO_CHEST", LuaValue.valueOf(Tex.GO_CHEST));
		globals.set("TEX_GO_BAG", LuaValue.valueOf(Tex.GO_BAG));
		globals.set("TEX_GO_BAG", LuaValue.valueOf(Tex.GO_WP));
		globals.set("TEX_GO_TORCH", LuaValue.valueOf(Tex.GO_TORCH));
		globals.set("TEX_GO_MINE_WALL", LuaValue.valueOf(Tex.GO_MINE_WALL));
		globals.set("TEX_GO_BONFIRE", LuaValue.valueOf(Tex.GO_BONFIRE));
		
		globals.set("TEX_SKILL_NULL", LuaValue.valueOf(Tex.SKILL_NULL));
		globals.set("TEX_SKILL_MELEE", LuaValue.valueOf(Tex.SKILL_MELEE));
		globals.set("TEX_SKILL_HEAL", LuaValue.valueOf(Tex.SKILL_HEAL));
		globals.set("TEX_SKILL_DRAG", LuaValue.valueOf(Tex.SKILL_DRAG));
		
		globals.set("TEX_NPC_WARNING", LuaValue.valueOf(Tex.NPC_WARNING));
		
		globals.set("TEX_ITEM_0", LuaValue.valueOf(Tex.ITEM_0));
		globals.set("TEX_ITEM_1", LuaValue.valueOf(Tex.ITEM_1));
		globals.set("TEX_ITEM_2", LuaValue.valueOf(Tex.ITEM_2));
		globals.set("TEX_ITEM_3", LuaValue.valueOf(Tex.ITEM_3));
	}
	
	private static void initEventConst(LuaValue globals) {
		globals.set("EVENT_NULL", LuaValue.valueOf(Event.EVENT_NULL));
		globals.set("EVENT_DAMAGE", LuaValue.valueOf(Event.EVENT_DAMAGE));
		globals.set("EVENT_DIALOG_BEGIN", LuaValue.valueOf(Event.EVENT_DIALOG_BEGIN));
		globals.set("EVENT_DIALOG_END", LuaValue.valueOf(Event.EVENT_DIALOG_END));
		globals.set("EVENT_PLAYER_DEAD", LuaValue.valueOf(Event.EVENT_PLAYER_DEAD));
		globals.set("EVENT_LOCATION_LOAD", LuaValue.valueOf(Event.EVENT_LOCATION_LOAD));
		globals.set("EVENT_LOCATION_CHANGE", LuaValue.valueOf(Event.EVENT_LOCATION_CHANGE));
		
		globals.set("EVENT_SCRIPT_GO_USE", LuaValue.valueOf(Event.EVENT_SCRIPT_GO_USE));
		globals.set("EVENT_SCRIPT_LAND", LuaValue.valueOf(Event.EVENT_SCRIPT_LAND));
		
		globals.set("EVENT_VISUAL_ATTACK", LuaValue.valueOf(Event.EVENT_VISUAL_ATTACK));
		
		globals.set("EVENT_SOUND_ATTACK", LuaValue.valueOf(Event.EVENT_SOUND_ATTACK));
		globals.set("EVENT_SOUND_DIALOG_BEGIN", LuaValue.valueOf(Event.EVENT_SOUND_DIALOG_BEGIN));
		globals.set("EVENT_SOUND_SAY", LuaValue.valueOf(Event.EVENT_SOUND_SAY));
		globals.set("EVENT_SOUND_STEP", LuaValue.valueOf(Event.EVENT_SOUND_STEP));
	}
}