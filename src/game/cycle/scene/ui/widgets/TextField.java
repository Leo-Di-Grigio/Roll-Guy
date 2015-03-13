package game.cycle.scene.ui.widgets;

import game.resources.Fonts;
import game.resources.Resources;
import game.resources.Tex;
import game.tools.Tools;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextField extends TextWidget {

	protected String text;
	
	public TextField(String title) {
		super(title);
		setText("");
		setTexNormal(Resources.getTex(Tex.uiButtonNormal));
		setFont(Resources.getFont(Fonts.fontConsolas));
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public String getText(){
		return text;
	}

	@Override
	public void draw(SpriteBatch sprites) {
		sprites.draw(texNormal, x, y, sizeX, sizeY);
		font.draw(sprites, text, x + 5, y + sizeY/2 + font.getBounds(text).height/2);
	}

	@Override
	public void key(char key) {
		if(key == 8){ // backspace == 8
			if(text.length() > 0){
				text = text.substring(0, text.length() - 1);
			}
		}
		else{
			if(text.length() < maxTextLength && Tools.checkCharacter(key)){
				text += key;
			}
		}
	}
	
	/*
	@Override
	public void key(String character) {		
		if(character.codePointAt(0) == 8){ // backspace == 8
			if(text.length() > 0){
				text = text.substring(0, text.length() - 1);
			}
		}
		else{
			char [] arr = character.toCharArray();
			if(Tools.checkCharacter(arr[0])){
				text += character;
			}
		}
	}
	*/
}
