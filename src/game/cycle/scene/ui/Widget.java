package game.cycle.scene.ui;

import game.cycle.input.UserInput;
import game.resources.Fonts;
import game.resources.Resources;
import game.resources.Tex;
import game.script.Script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

abstract public class Widget implements Disposable {

	public static enum Alignment {
		UPLEFT,
		UPCENTER,
		UPRIGTH,
		CENTERLEFT,
		CENTER,
		CENTERRIGHT,
		DOWNLEFT,
		DOWNCENTER,
		DOWNRIGHT,
	}
	
	public String title;
	public boolean visible;
	
	private Script script;
	protected int layer;
	protected Alignment alignment;
	protected int x;
	protected int y;
	public int sizeX;
	public int sizeY;
	
	public boolean selected;
	
	protected Texture texNormal;
	protected Texture texSelected;
	protected BitmapFont font;
	
	protected boolean keyInput;
	protected boolean scroll;
	
	public Widget(String title) {
		this.title = title;
		
		texNormal = Resources.getTex(Tex.texNull);
		texSelected = Resources.getTex(Tex.texNull);
		
		font = Resources.getFont(Fonts.fontDefault);
		
		alignment = Alignment.DOWNLEFT;
	}
	
	private void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void setPosition(Alignment alignment, int x, int y){
		this.alignment = alignment;
		
		int frameX = Gdx.graphics.getWidth();
		int frameY = Gdx.graphics.getHeight();
				
		switch(alignment){
			
			case CENTER:
				setPosition(frameX/2 - sizeX/2 + x, frameY/2 - sizeY/2 + y);
				break;
				
			case CENTERLEFT:
				setPosition(x, frameY/2 + y);
				break;
				
			case CENTERRIGHT:
				setPosition(frameX - sizeX + x, frameY/2 + y);
				break;
				
			case DOWNLEFT:
				setPosition(x, y);
				break;
				
			case DOWNCENTER:
				setPosition(frameX/2 - sizeX/2 + x, y);
				break;
				
			case DOWNRIGHT:
				setPosition(frameX - sizeX + x, y);
				break;
				
			case UPLEFT:
				setPosition(x, frameY - sizeY + y);
				break;
				
			case UPCENTER:
				setPosition(frameX/2 - sizeX/2 + x, frameY - sizeY + y);
				break;
				
			case UPRIGTH:
				setPosition(frameX - sizeX + x, frameY - sizeY + y);
				break;
				
			default: 
				break;
		}
	}
	
	public void setTexNormal(Integer texTitle){
		this.texNormal = Resources.getTex(texTitle); 
	}
	
	public void setTexNormal(Texture tex){
		this.texNormal = tex;
	}
	
	public void setTexSelected(Integer texTitle){
		this.texSelected = Resources.getTex(texTitle); 
	}
	
	public void setTexSelected(Texture tex){
		this.texSelected = tex;
	}
	
	public void setSize(int sizeX, int sizeY){
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}
	
	public void setScript(Script script){
		this.script = script;
	}
	
	public Script getScript(){
		return this.script;
	}
	
	public void execute(){
		if(this.script != null){
			this.script.execute();
		}
	}
	
	public void setFont(BitmapFont font){
		this.font = font;
	}
	
	public void setLayer(int layer){
		this.layer = layer;
	}
	
	public boolean mouseCollision(){
		int mouseX = UserInput.mouseX;
		int mouseY = Gdx.graphics.getHeight() - UserInput.mouseY;
		
		if(mouseX > x &&
		   mouseX < x + sizeX &&
		   mouseY > y && 
		   mouseY < y + sizeY)
		{
			return true;
		}
		
		return false;
	}
	
	@Override
	public void dispose() {
		texNormal = null;
		texSelected = null;
		font = null;
	}
	
	abstract public void draw(SpriteBatch sprites);
}
