package ui.widgets;

import game.resources.Fonts;
import game.resources.Resources;
import game.resources.tex.Tex;

import org.apache.commons.lang3.text.WordUtils;

import ui.Alignment;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;

public class Information extends Image {
	//
	private static final int lineHeight = 15;
	private static final int perCharacterTic = 5;
	private static final int wrapCharactersCount = 30;
	private static final String wraper = "\n";
	
	// 
	private int showTime;
	private int currentShowTimer;
	private TextBounds bounds;
	private String [] text;
	
	public Information(String title) {
		super(title);
		this.font = Resources.getFont(Fonts.fontDefault);
		this.setTexNormal(Tex.UI_BACKGROUND_INFORMATION);
	}

	public void setText(String infoText){
		if(infoText == null){
			this.setVisible(false);
		}
		else{
			this.text = WordUtils.wrap(infoText, wrapCharactersCount, wraper, true).split(wraper);
			this.showTime = Information.perCharacterTic * infoText.length();
			bounds = font.getBounds(infoText);
			
			if(bounds.width + 25 < 300){
				this.setSize((int)bounds.width + 25, lineHeight*text.length + 15);
			}
			else{
				this.setSize(300, lineHeight*text.length + 15);
			}
			
			this.setPosition(Alignment.DOWNCENTER, 0, 100);
			
			this.setVisible(true);
		}
	}
	
	@Override
	public void draw(SpriteBatch sprites) {
		currentShowTimer++;
		
		if(currentShowTimer < showTime){
			super.draw(sprites);
			
			for(int i = 0; i < text.length; ++i){
				font.drawWrapped(sprites, text[i], x, y + sizeY - i*lineHeight - 7, sizeX, BitmapFont.HAlignment.CENTER);
			}
		}
		else{
			this.currentShowTimer = 0;
			this.setVisible(false);
		}
	}
}
