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
	protected boolean visible;
	
	private Script script;
	protected int layer;
	protected Alignment alignment;
	protected int x;
	protected int y;
	protected int posX;
	protected int posY;
	public int sizeX;
	public int sizeY;
	
	public boolean selected;
	
	protected Texture texNormal;
	protected Texture texSelected;
	protected BitmapFont font;
	
	protected boolean keyInput;
	protected boolean scroll;
	protected boolean draggble;
	
	protected Tooltip tooltip;
	
	public Widget(String title) {
		this.title = title;
		
		texNormal = Resources.getTex(Tex.texNull);
		texSelected = Resources.getTex(Tex.texNull);
		
		font = Resources.getFont(Fonts.fontDefault);
		
		alignment = Alignment.DOWNLEFT;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void setPos(int x, int y){
		this.posX = x;
		this.posY = y;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}
	
	private void setAbsolutePosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void setPosition(int x, int y){
		setPosition(this.alignment, x, y);
	}
	
	public void setPosition(int x, int y, int deltaX, int deltaY, int frameX, int frameY){
		setPosition(this.alignment, x, y, deltaX, deltaY, frameX, frameY);
	}
	
	public void setPosition(Alignment alignment, int x, int y){
		setPosition(alignment, x, y, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	public void setPosition(Alignment alignment, int x, int y, int deltaX, int deltaY, int frameX, int frameY){
		this.alignment = alignment;
		this.setPos(x, y);
		
		switch(alignment){
			
			case CENTER:
				setAbsolutePosition(deltaX + frameX/2 - sizeX/2 + x, deltaY + frameY/2 - sizeY/2 + y);
				break;
				
			case CENTERLEFT:
				setAbsolutePosition(deltaX + x, deltaY + frameY/2 - sizeY/2  + y);
				break;
				
			case CENTERRIGHT:
				setAbsolutePosition(deltaX + frameX - sizeX + x, deltaY + frameY/2  - sizeY/2 + y);
				break;
				
			case DOWNLEFT:
				setAbsolutePosition(deltaX + x, deltaY + y);
				break;
				
			case DOWNCENTER:
				setAbsolutePosition(deltaX + frameX/2 - sizeX/2 + x, deltaY + y);
				break;
				
			case DOWNRIGHT:
				setAbsolutePosition(deltaX + frameX - sizeX + x, deltaY + y);
				break;
				
			case UPLEFT:
				setAbsolutePosition(deltaX + x, deltaY + frameY - sizeY + y);
				break;
				
			case UPCENTER:
				setAbsolutePosition(deltaX + frameX/2 - sizeX/2 + x, deltaY +  frameY - sizeY + y);
				break;
				
			case UPRIGTH:
				setAbsolutePosition(deltaX + frameX - sizeX + x, deltaY + frameY - sizeY + y);
				break;
				
			default: 
				break;
		}
	}
	
	public void translate(int dx, int dy){
		this.setAbsolutePosition(this.x - dx, this.y - dy);
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

	public int getLayer() {
		return layer;
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
	
	public void setTooltip(Tooltip tooltip){
		this.tooltip = tooltip;
	}
	
	@Override
	public void dispose() {
		texNormal = null;
		texSelected = null;
		font = null;
	}
	
	abstract public void draw(SpriteBatch sprites);
}
