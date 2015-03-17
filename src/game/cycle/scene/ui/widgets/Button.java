package game.cycle.scene.ui.widgets;

import game.cycle.input.UserInput;
import game.resources.Resources;
import game.resources.Tex;
import game.script.Script;
import game.script.ui.ui_ButtonClick;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button extends AbstractButton {

	private Script buttonScript;
	
	private boolean active;
	private boolean click;
	private Texture texClick;
	private Texture icon;
	
	public Button(String title, String text) {
		super(title, text);
		setText(text);
		setTexClick(Tex.texNull);
		
		setTexNormal(Tex.uiButtonNormal);
		setTexSelected(Tex.uiButtonSelected);
		setTexClick(Tex.uiButtonClick);
		
		super.setScript(new ui_ButtonClick(this));
	}
	
	public void setTexClick(int texKey){
		this.texClick = Resources.getTex(texKey);
	}
	
	public void setIcon(int texKey){
		this.icon = Resources.getTex(texKey);
	}

	public void setIcon(Texture tex) {
		this.icon = tex;
	}
	
	public void click(){
		click = true;
	}
	
	public void setActive(boolean active){
		this.active = active;
	}
	
	public boolean getActive() {
		return active;
	}
	
	@Override
	public void execute() {
		super.execute();
		if(this.buttonScript != null){
			this.buttonScript.execute();
		}
	}
	
	@Override
	public void setScript(Script script) {
		this.buttonScript = script; 
	}
	
	@Override
	public void draw(SpriteBatch sprites) {
		if(!UserInput.mouseLeft){
			click = false;
		}
		
		if(selected){
			if(click || active){
				sprites.draw(texClick, x, y, sizeX, sizeY);
			}
			else{
				sprites.draw(texSelected, x, y, sizeX, sizeY);
			}
		}
		else{
			if(active){
				sprites.draw(texClick, x, y, sizeX, sizeY);
			}
			else{
				sprites.draw(texNormal, x, y, sizeX, sizeY);
			}
		}

		
		if(icon == null){
			font.drawWrapped(sprites, this.text, x, y + this.bounds.height * 2, sizeX, BitmapFont.HAlignment.CENTER);
		}
		else{
			sprites.draw(icon, x, y, sizeX, sizeY);
		}
	}
}