package game.resources;

import game.resources.tex.Tex;
import game.resources.tex.TexChar;
import game.resources.tex.TexLighting;
import game.resources.tex.TexAtlas;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Disposable;

public class Resources implements Disposable {
	// misc
	private static final String russianChars = "ÀÁÂÃÄÅ¨ÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäå¸æçèéêëìíîïðñòóôõö÷øùúûüýþÿ";
	
	// file patches
	private static final String folderTextures = "assets/textures/";
	private static final String folderFonts = "assets/font/";
	private static final String folderCursors = "assets/cursor/";
	
	// data
	private static HashMap<Integer, Tex> texturesId;
	private static HashMap<Integer, BitmapFont> fonts;
	private static HashMap<Integer, Pixmap> cursors;
	
	public Resources() {
		texturesId = new HashMap<Integer, Tex>();
		fonts = new HashMap<Integer, BitmapFont>();
		cursors = new HashMap<Integer, Pixmap>();
		new Cursors(cursors);
		
		loadTexes();
		loadFonts();
		loadCursors();
	}
	
	private void loadTexes() {
		// null
		loadTex(Tex.NULL, "null.png");
		
		// ui
		loadTex(Tex.UI_BUTTON_NORMAL, "ui/button-normal.png");
		loadTex(Tex.UI_BUTTON_SELECTED, "ui/button-selected.png");
		loadTex(Tex.UI_BUTTON_CLICK, "ui/button-click.png");
		loadTex(Tex.UI_BACKGROUND_NORMAL, "ui/background-normal.png");
		loadTex(Tex.UI_BACKGROUND_SELECTED, "ui/background-selected.png");
		loadTex(Tex.UI_BACKGROUND_SELECTED_LIGHT, "ui/background-light-normal.png");
		loadTex(Tex.UI_LIST_LINE, "ui/list-select-line.png");
		loadTex(Tex.UI_INVENTORY_SLOT, "ui/inventory-slot.png");
		loadTex(Tex.UI_INVENTORY_SLOT_HELMET, "ui/inventory-slot-head.png");
		loadTex(Tex.UI_INVENTORY_SLOT_CHEST, "ui/inventory-slot-chest.png");
		loadTex(Tex.UI_INVENTORY_SLOT_HAND_1, "ui/inventory-slot-h1.png");
		loadTex(Tex.UI_INVENTORY_SLOT_HAND_2, "ui/inventory-slot-h2.png");
		loadTex(Tex.UI_SKILL_FRAME, "ui/useSkillFrame.png");
		loadTex(Tex.UI_BACKGROUND_INFORMATION, "ui/background-information.png");
		
		// tile select
		loadTex(Tex.GAMEPLAY_SELECT, "tiles/select.png");
		loadTex(Tex.GAMEPLAY_WP, "tiles/waypoint.png");
		
		// tiles
		loadTex(Tex.TILE_NULL, "tiles/surface/null.png");
		loadTex(Tex.TILE_GRASS, "tiles/surface/grass.png");
		loadTex(Tex.TILE_WALL, "tiles/surface/wall.png");
		loadTex(Tex.TILE_WATER, "tiles/surface/water.png");
		loadTex(Tex.TILE_ALUMINA, "tiles/surface/alumina.png");
		loadTex(Tex.TILE_CRACKED_ALUMINA, "tiles/surface/alumina-cracked.png");
		loadTex(Tex.TILE_STONE, "tiles/surface/stone.png");
		loadTex(Tex.TILE_SAND_STONE_COAST, "tiles/surface/coast-sand-stone.png");
		loadTex(Tex.TILE_RIVER, "tiles/surface/river.png");
		loadTex(Tex.TILE_SAND, "tiles/surface/sand.png");
		loadTex(Tex.TILE_SAND_STONE, "tiles/surface/sand-stone.png");
		
		// atlases
		loadTexTerrain(Tex.TEX_ATLAS_0, "tiles/atlases/wallSet1.png");
		
		// ligting
		loadTexLighting(Tex.LIGHT, "tiles/lighting/lighting.png");
		
		// creatures
		loadTexChar(Tex.CREATURE_0, "creatures/player.png");
		loadTexChar(Tex.CREATURE_1, "creatures/npc.png");
		loadTexChar(Tex.CREATURE_2, "creatures/char.png");
		
		// creatures avatar
		loadTex(Tex.AVATAR_0, "creatures/avatar/npc.png");
		
		// go
		loadTex(Tex.GO_NULL, "tiles/go/go.png");
		loadTex(Tex.GO_DOOR_OPEN, "tiles/go/door_open.png");
		loadTex(Tex.GO_DOOR_CLOSE, "tiles/go/door_close.png");
		loadTex(Tex.GO_CHEST, "tiles/go/chest.png");
		loadTex(Tex.GO_BAG, "tiles/go/loot.png");
		loadTex(Tex.GO_WP, "tiles/go/way_point.png");
		loadTex(Tex.GO_TORCH, "tiles/go/torch.png");
		loadTex(Tex.GO_MINE_WALL, "tiles/go/mine_wall.png");
		loadTex(Tex.GO_BONFIRE, "tiles/go/bonfire.png");
		
		// skills
		loadTex(Tex.SKILL_MELEE, "skills/melee.png");
		loadTex(Tex.SKILL_HEAL, "skills/heal.png");
		loadTex(Tex.SKILL_DRAG, "skills/drag.png");
		
		// creature events
		loadTex(Tex.NPC_WARNING, "creatures/event/warning.png");
		
		// items
		loadTex(Tex.ITEM_0, "items/null0.png");
		loadTex(Tex.ITEM_1, "items/null1.png");
		loadTex(Tex.ITEM_2, "items/null2.png");
		loadTex(Tex.ITEM_3, "items/null3.png");
	}

	private void loadFonts() {
		loadFont(Fonts.fontDefault, "font.ttf", 14);
		loadFont(Fonts.fontConsolas, "consolas.ttf", 13);
		loadFont(Fonts.fontDamage, "font.ttf", 16);
	}

	private void loadCursors() {
		loadCursor(Cursors.cursorDefault, "default.png");
		loadCursor(Cursors.cursorTalking, "talk.png");
		loadCursor(Cursors.cursorCast, "cast.png");
		
		Cursors.setCursor(Cursors.cursorDefault);
	}

	// ----------------------------------------------------------------------
	// Textures
	public static void loadTex(int id, String filePath){
		Tex tex = new Tex(id, new Texture(Gdx.files.internal(folderTextures + filePath)));
		texturesId.put(tex.id, tex);
	}
	
	public static void loadTexChar(int id, String filePath){
		TexChar tex = new TexChar(id, new Texture(Gdx.files.internal(folderTextures + filePath)));
		texturesId.put(tex.id, tex);
	}

	private void loadTexTerrain(int id, String filePath) {
		TexAtlas tex = new TexAtlas(id, new Texture(Gdx.files.internal(folderTextures + filePath)));
		texturesId.put(tex.id, tex);
	}
	
	public static void loadTexLighting(int id, String filePath){
		TexLighting tex = new TexLighting(id, new Texture(Gdx.files.internal(folderTextures + filePath)));
		texturesId.put(tex.id, tex);
	}
		
	public static Texture getTex(int id){
		return texturesId.get(id).tex;
	}
	
	public static Tex getTexWrap(int id){
		return texturesId.get(id);
	}
	
	// Font
	public static void loadFont(int id, String filePath, int fontSize){
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(folderFonts+filePath));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + Resources.russianChars;
		parameter.size = fontSize;
		 
		BitmapFont font = generator.generateFont(parameter);
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		fonts.put(id, font);
		generator.dispose();
	}

	public static BitmapFont getFont(Integer fontTitle){
		return fonts.get(fontTitle);
	}
	
	// Cursor
	private void loadCursor(int key, String filePath) {
		Pixmap cursor = new Pixmap(Gdx.files.internal(folderCursors+filePath));
		cursors.put(key, cursor);
	}
	
	public static Texture [] getLocationSpriteSet(){
		// ïîçæå äàííûå áóäóò ïåðåíåñåíû â ìåòà-áëîê çàãðóç÷èêà ëîêàöèé
		Texture [] arr = new Texture[20];
		arr[0] = getTex(Tex.TILE_NULL);
		arr[1] = getTex(Tex.TILE_GRASS);
		arr[2] = getTex(Tex.TILE_WALL);
		arr[3] = getTex(Tex.TILE_WATER);
		arr[4] = getTex(Tex.TILE_ALUMINA);
		arr[5] = getTex(Tex.TILE_CRACKED_ALUMINA);
		arr[6] = getTex(Tex.TILE_STONE);
		arr[7] = getTex(Tex.TILE_SAND_STONE_COAST);
		arr[8] = getTex(Tex.TILE_RIVER);
		arr[9] = getTex(Tex.TILE_SAND);
		arr[10] = getTex(Tex.TILE_SAND_STONE);
		
		return arr;
	}
	
	@Override
	public void dispose() {
		for(Integer key: texturesId.keySet()){
			Tex tex = texturesId.get(key);
			tex.dispose();
		}
		
		for(Integer key: fonts.keySet()){
			BitmapFont font = fonts.get(key);
			font.dispose();
		}
		
		for(Integer key: cursors.keySet()){
			Pixmap pix = cursors.get(key);
			pix.dispose();
		}
	}
}