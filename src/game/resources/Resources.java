package game.resources;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Disposable;

public class Resources implements Disposable {
	// misc
	private static final String russianChars = "ÀÁÂÃÄÅ¨ÆÇÈÉÊËÌÍÎÏĞÑÒÓÔÕÖ×ØÙÚÛÜİŞßàáâãäå¸æçèéêëìíîïğñòóôõö÷øùúûüışÿ";
	
	// file patches
	private static final String folderTextures = "assets/textures/";
	private static final String folderFonts = "assets/font/";
	
	// data
	private static HashMap<Integer, Tex> texturesId;
	private static HashMap<Integer, BitmapFont> fonts;
	
	public Resources() {
		texturesId = new HashMap<Integer, Tex>();
		fonts = new HashMap<Integer, BitmapFont>();
		
		loadTexes();
		loadFonts();
	}

	private void loadTexes() {
		// null
		loadTex(Tex.texNull, "null.png");
		
		// ui
		loadTex(Tex.uiButtonNormal, "ui/button-normal.png");
		loadTex(Tex.uiButtonSelected, "ui/button-selected.png");
		loadTex(Tex.uiButtonClick, "ui/button-click.png");
		loadTex(Tex.uiBackground, "ui/background-normal.png");
		loadTex(Tex.uiListLine, "ui/list-select-line.png");
		
		// tiles
		loadTex(Tex.tileSelect, "tiles/select.png");
		loadTex(Tex.tileWaypoint, "tiles/waypoint.png");
		loadTex(Tex.tileGrass, "tiles/grass.png");
		loadTex(Tex.tileWall, "tiles/wall.png");
		
		// creatures
		loadTex(Tex.creatureCharacter, "creatures/char.png");
		loadTex(Tex.creatureNpc, "creatures/npc.png");
		
		// creatures avatar
		loadTex(Tex.avatarNpc, "creatures/avatar/npc.png");
	}
	
	private void loadFonts() {
		loadFont(Fonts.fontDefault, "font.ttf", 14);
	}
	
	// ----------------------------------------------------------------------
	// Textures
	public static void loadTex(int id, String filePath){
		Tex tex = new Tex(id, new Texture(Gdx.files.internal(folderTextures + filePath)));
		texturesId.put(tex.id, tex);
	}
		
	public static Texture getTex(int id){
		return texturesId.get(id).tex;
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
	}
}
