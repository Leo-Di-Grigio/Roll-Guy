package game.lua;

import game.cycle.scene.game.world.event.LocationEvent;
import game.lua.lib.LuaMethodsConsole;
import game.lua.lib.LuaMethodsLocation;
import game.resources.tex.Tex;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

public class LuaEngineGlobals {

	public static LuaValue getGlobals(){
		LuaValue globals = JsePlatform.standardGlobals();
		initConsts(globals);
		return globals;
	}

	private static void initConsts(LuaValue globals) {
		initClasses(globals);
		initResourcesConst(globals);
		initEventConst(globals);
	}

	private static void initClasses(LuaValue globals) {
		globals.set("console", CoerceJavaToLua.coerce(new LuaMethodsConsole()));
		globals.set("location", CoerceJavaToLua.coerce(new LuaMethodsLocation()));
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
		// TYPE
		globals.set("EVENT_VISUAL", LuaValue.valueOf(LocationEvent.EVENT_VISUAL));
		globals.set("EVENT_SOUND", LuaValue.valueOf(LocationEvent.EVENT_SOUND));
		globals.set("EVENT_SCRIPT", LuaValue.valueOf(LocationEvent.EVENT_SCRIPT));
		globals.set("EVENT_TRIGGER", LuaValue.valueOf(LocationEvent.EVENT_TRIGGER));
		
		// Context
		globals.set("CONTEXT_NULL", LuaValue.valueOf(LocationEvent.CONTEXT_NULL));
		globals.set("CONTEXT_ATTACK", LuaValue.valueOf(LocationEvent.CONTEXT_ATTACK));
		globals.set("CONTEXT_TALKING", LuaValue.valueOf(LocationEvent.CONTEXT_TALKING));
		globals.set("CONTEXT_DAMAGE", LuaValue.valueOf(LocationEvent.CONTEXT_DAMAGE));
		globals.set("CONTEXT_GO_USE", LuaValue.valueOf(LocationEvent.CONTEXT_GO_USE));
		globals.set("CONTEXT_LAND", LuaValue.valueOf(LocationEvent.CONTEXT_LAND));
		globals.set("CONTEXT_LINK", LuaValue.valueOf(LocationEvent.CONTEXT_LINK));
		globals.set("CONTEXT_DIALOG_BEGIN", LuaValue.valueOf(LocationEvent.CONTEXT_DIALOG_BEGIN));
		globals.set("CONTEXT_DIALOG_END", LuaValue.valueOf(LocationEvent.CONTEXT_DIALOG_END));
	}
}
