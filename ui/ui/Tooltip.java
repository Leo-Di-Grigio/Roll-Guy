package ui;

import org.apache.commons.lang3.text.WordUtils;

import resources.Fonts;
import resources.Resources;
import resources.tex.Tex;
import game.cycle.input.UserInput;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;

public class Tooltip {

	private static final String WRAPPER = "\n";
	private static final int WRAPPER_CHARACTERS_COUNT = 40;
	
	private BitmapFont fontTitle;
	private BitmapFont fontText;
	
	private Texture tex;
	private String title;
	private String [] text;
	
	protected TextBounds titleBound;
	protected TextBounds textBound;
	
	protected float sizeX;
	protected float sizeY;
	
	public Tooltip(String title, String text) {
		this.title = title;
		this.text =  WordUtils.wrap(text, WRAPPER_CHARACTERS_COUNT, WRAPPER, true).split(WRAPPER);
		
		this.tex = Resources.getTex(Tex.UI_BACKGROUND_SELECTED_LIGHT);
		this.fontTitle = Resources.getFont(Fonts.FONT_DEFAULT);
		this.fontText = Resources.getFont(Fonts.FONT_DEFAULT);
		
		titleBound = fontTitle.getBounds(title);
		textBound = fontText.getBounds(text);
		
		sizeX = Math.max(titleBound.width, textBound.width) * 2;
		sizeY = (titleBound.height + textBound.height*this.text.length + 10);
	}
	
	public void draw(SpriteBatch batch){
		float x = UserInput.mouseX;
		float y = Gdx.graphics.getHeight() - UserInput.mouseY;
		
		batch.draw(tex, x + 32, y - sizeY, sizeX, sizeY);
		fontTitle.drawWrapped(batch, this.title, x + 32, y, sizeX, BitmapFont.HAlignment.LEFT);
		
		for(int i = 0; i < text.length; ++i){
			fontText.drawWrapped(batch, text[i], x + 32, y - (i + 1)*textBound.height - 5, sizeX, BitmapFont.HAlignment.LEFT);	
		}
	}
}
