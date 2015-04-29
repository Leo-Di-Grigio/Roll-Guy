package client.scenes.ui;

import client.input.UserInput;
import client.resources.Resources;
import client.scenes.ui.script.UIScript;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

import common.resources.Fonts;
import common.resources.Tex;

public abstract class Widget implements Disposable {

	// data
	protected String title;
	protected boolean visible;
	
	protected boolean keyInput;
	protected boolean scroll;
	protected boolean draggble;
	
	protected Tooltip tooltip;
	protected boolean selected;
	
	protected int layer;
	protected int alignment;
	protected int x;
	protected int y;
	protected int posX;
	protected int posY;
	protected int sizeX;
	protected int sizeY;
	
	protected Texture texNormal;
	protected Texture texSelected;
	protected BitmapFont font;
	
	protected UIScript script;
	
	public Widget(String title) {
		this.title = title;
		
		// default
		setTexNormal(Tex.NULL);
		setTexSelected(Tex.NULL);
		setFont(Fonts.DEFAULT);
		setAlignment(Alignment.DOWNLEFT);
	}

	public String title() {
		return title;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean getVisible(){
		return visible;
	}
	
	public void setPos(int x, int y){
		this.posX = x;
		this.posY = y;
	}
	
	private void setAbsolutePosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void setPosition(int x, int y){
		setPosition(alignment, x, y);
	}
	
	public void setPosition(int x, int y, int deltaX, int deltaY, int frameX, int frameY){
		setPosition(alignment, x, y, deltaX, deltaY, frameX, frameY);
	}
	
	public void setPosition(int alignment, int x, int y){
		setPosition(alignment, x, y, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	public void setPosition(int alignment, int x, int y, int deltaX, int deltaY, int frameX, int frameY){
		this.alignment = alignment;
		this.setPos(x, y);
		
		switch(alignment){
			
			case Alignment.CENTER:
				setAbsolutePosition(deltaX + frameX/2 - sizeX/2 + x, deltaY + frameY/2 - sizeY/2 + y);
				break;
				
			case Alignment.CENTERLEFT:
				setAbsolutePosition(deltaX + x, deltaY + frameY/2 - sizeY/2  + y);
				break;
				
			case Alignment.CENTERRIGHT:
				setAbsolutePosition(deltaX + frameX - sizeX + x, deltaY + frameY/2  - sizeY/2 + y);
				break;
				
			case Alignment.DOWNLEFT:
				setAbsolutePosition(deltaX + x, deltaY + y);
				break;
				
			case Alignment.DOWNCENTER:
				setAbsolutePosition(deltaX + frameX/2 - sizeX/2 + x, deltaY + y);
				break;
				
			case Alignment.DOWNRIGHT:
				setAbsolutePosition(deltaX + frameX - sizeX + x, deltaY + y);
				break;
				
			case Alignment.UPLEFT:
				setAbsolutePosition(deltaX + x, deltaY + frameY - sizeY + y);
				break;
				
			case Alignment.UPCENTER:
				setAbsolutePosition(deltaX + frameX/2 - sizeX/2 + x, deltaY +  frameY - sizeY + y);
				break;
				
			case Alignment.UPRIGTH:
				setAbsolutePosition(deltaX + frameX - sizeX + x, deltaY + frameY - sizeY + y);
				break;
				
			default: 
				break;
		}
	}

	private void setAlignment(int alignment) {
		this.alignment = alignment;
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
	
	public void setScript(UIScript script){
		this.script = script;
	}
	
	public UIScript getScript(){
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

	public void setFont(int fontKey) {
		this.font = Resources.getFont(fontKey);
	}
	
	public void setTooltip(Tooltip tooltip){
		this.tooltip = tooltip;
	}
	
	public boolean mouseCollision(){
		int mouseX = UserInput.mouseX;
		int mouseY = Gdx.graphics.getHeight() - UserInput.mouseY;
		
		if(mouseX > x && mouseX < x + sizeX && mouseY > y && mouseY < y + sizeY){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public void dispose() {
		texNormal = null;
		texSelected = null;
		font = null;
	}
	
	abstract public void draw(SpriteBatch sprites);
}
