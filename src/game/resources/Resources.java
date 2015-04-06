package game.resources;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Disposable;

public class Resources implements Disposable {
	// misc
	private static final String russianChars = "�����Ũ����������������������������������������������������������";
	
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
		loadTex(Tex.texNull, "null.png");
		
		// ui
		loadTex(Tex.uiButtonNormal, "ui/button-normal.png");
		loadTex(Tex.uiButtonSelected, "ui/button-selected.png");
		loadTex(Tex.uiButtonClick, "ui/button-click.png");
		loadTex(Tex.uiBackgroundNormal, "ui/background-normal.png");
		loadTex(Tex.uiBackgroundSelected, "ui/background-selected.png");
		loadTex(Tex.uiBackgroundLightSelected, "ui/background-light-normal.png");
		loadTex(Tex.uiListLine, "ui/list-select-line.png");
		loadTex(Tex.uiInventorySlot, "ui/inventory-slot.png");
		loadTex(Tex.uiInventorySlotHead, "ui/inventory-slot-head.png");
		loadTex(Tex.uiInventorySlotChest, "ui/inventory-slot-chest.png");
		loadTex(Tex.uiInventorySlotHand1, "ui/inventory-slot-h1.png");
		loadTex(Tex.uiInventorySlotHand2, "ui/inventory-slot-h2.png");
		loadTex(Tex.uiUseSkillFrame, "ui/useSkillFrame.png");
		
		// tile select
		loadTex(Tex.tileSelect, "tiles/select.png");
		loadTex(Tex.tileWaypoint, "tiles/waypoint.png");
		
		// tiles
		loadTex(Tex.tileNull, "tiles/surface/null.png");
		loadTex(Tex.tileGrass, "tiles/surface/grass.png");
		loadTex(Tex.tileWall, "tiles/surface/wall.png");
		loadTex(Tex.tileWater, "tiles/surface/water.png");
		loadTex(Tex.tileFog, "tiles/surface/fog.png");
		
		// creatures
		loadTexChar(Tex.creaturePlayer, "creatures/player.png");
		loadTexChar(Tex.creatureNpc, "creatures/npc.png");
		loadTexChar(Tex.creatureCharacter, "creatures/char.png");
		
		// creatures avatar
		loadTex(Tex.avatarNpc, "creatures/avatar/npc.png");
		
		// go
		loadTex(Tex.go, "tiles/go/go.png");
		loadTex(Tex.goDoorOpen, "tiles/go/door_open.png");
		loadTex(Tex.goDoorClose, "tiles/go/door_close.png");
		loadTex(Tex.goChest, "tiles/go/chest.png");
		loadTex(Tex.goLoot, "tiles/go/loot.png");
		loadTex(Tex.goWayPoint, "tiles/go/way_point.png");
		
		// skills
		loadTex(Tex.skillMelee, "skills/melee.png");
		loadTex(Tex.skillHeal, "skills/heal.png");
		loadTex(Tex.skillDrag, "skills/drag.png");
		
		// creature events
		loadTex(Tex.npcWarning, "creatures/event/warning.png");
		
		// items
		loadTex(Tex.itemNull0, "items/null0.png");
		loadTex(Tex.itemNull1, "items/null1.png");
		loadTex(Tex.itemNull2, "items/null2.png");
		loadTex(Tex.itemNull3, "items/null3.png");
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
	
	public static Sprite [] getLocationSpriteSet(){
		// ����� ������ ����� ���������� � ����-���� ���������� �������
		Sprite [] sprites = new Sprite[10];
		sprites[0] = new Sprite(getTex(Tex.tileNull));
		sprites[1] = new Sprite(getTex(Tex.tileGrass));
		sprites[2] = new Sprite(getTex(Tex.tileWall));
		sprites[3] = new Sprite(getTex(Tex.tileWater));
		sprites[9] = new Sprite(getTex(Tex.tileFog));
		return sprites;
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