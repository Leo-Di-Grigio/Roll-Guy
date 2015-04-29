package client.resources;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Disposable;

import common.resources.Fonts;
import common.resources.Tex;

public class Resources implements Disposable {
	// 
	private static boolean loaded;
	private static final String CIRILIC_CHARACTERS = "ÀÁÂÃÄÅ¨ÆÇÈÉÊËÌÍÎÏĞÑÒÓÔÕÖ×ØÙÚÛÜİŞßàáâãäå¸æçèéêëìíîïğñòóôõö÷øùúûüışÿ";
	
	// data
	private static HashMap<Integer, Texture> textures;
	private static HashMap<Integer, BitmapFont> fonts;
	
	public Resources() {
		textures = new HashMap<Integer, Texture>();
		fonts = new HashMap<Integer, BitmapFont>();
		
		load();
	}	
	
	private void load() {
		loadTextures();
		loadFonts();
	}

	private void loadTextures() {
		// 
		loadTex(Tex.NULL, "assets/textures/null.png");
		
		// UI
		loadTex(Tex.UI_BUTTON_NORMAL, "assets/textures/ui/button-normal.png");
		loadTex(Tex.UI_BUTTON_SELECTED, "assets/textures/ui/button-selected.png");
		loadTex(Tex.UI_BUTTON_CLICK, "assets/textures/ui/button-click.png");
	}
	
	private void loadFonts() {
		loadFont(Fonts.DEFAULT, "assets/font/sans_caption.ttf", 14);
		loadFont(Fonts.CONSOLAS, "assets/font/consolas.ttf", 14);
	}
	
	public static boolean loaded(){
		return loaded;
	}
	
	// -----------------------------------
	// Textures
	private static void loadTex(int id, String filePath){
		Texture tex = new Texture(Gdx.files.internal(filePath));
		textures.put(id, tex);
	}
	
	// Fonts
	private static void loadFont(int id, String filePath, int fontSize){
		loadFont(id, filePath, fontSize, 1.0f, 1.0f, 1.0f, 1.0f);
	}
	
	private static void loadFont(int id, String filePath, int fontSize, float r, float g, float b, float a){
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(filePath));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + CIRILIC_CHARACTERS;
		parameter.size = fontSize;
		 
		BitmapFont font = generator.generateFont(parameter);
		font.setColor(r, g, b, a);

		generator.dispose();
		fonts.put(id, font);
	}
	
	// -----------------------------------
	// Get
	public static Texture getTex(int id){
		return Resources.textures.get(id);
	}
	
	public static BitmapFont getFont(int id){
		return Resources.fonts.get(id);
	}

	@Override
	public void dispose() {
		for(Texture tex: textures.values()){
			tex.dispose();
		}
		
		for(BitmapFont font: fonts.values()){
			font.dispose();
		}
		
		fonts.clear();
		textures.clear();
	}
}
