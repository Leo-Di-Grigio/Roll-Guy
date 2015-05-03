package ui;

import resources.Fonts;
import resources.Resources;
import resources.tex.Tex;
import game.script.Script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

abstract public class Widget implements Disposable {

	// header
	protected String title;
	protected boolean visible;
	
	// data
	private Script script;
	protected int layer;
	protected int alignment;
	protected int x;
	protected int y;
	protected int posX;
	protected int posY;
	protected int sizeX;
	protected int sizeY;
	
	// flags
	protected boolean selected;
	
	// interfaces flags
	protected boolean focusable;
	protected boolean keyInput;
	protected boolean scroll;
	protected boolean draggble;
	
	// resources
	protected Texture texNormal;
	protected Texture texSelected;
	protected BitmapFont font;	
	protected Tooltip tooltip;
	
	public Widget(String title) {
		this.title = title;
		
		texNormal = Resources.getTex(Tex.NULL);
		texSelected = Resources.getTex(Tex.NULL);
		font = Resources.getFont(Fonts.FONT_DEFAULT);
		
		alignment = Alignment.DOWNLEFT;
	}

	public String getTitle(){
		return title;
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

	public int getSizeX(){
		return sizeX;
	}
	
	public int getSizeY(){
		return sizeY;
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
		setPosition(alignment, x, y);
	}
	
	public void setPosition(int x, int y, int deltaX, int deltaY, int frameX, int frameY){
		setPosition(alignment, x, y, deltaX, deltaY, frameX, frameY);
	}
	
	public void setPosition(int alignment, int x, int y){
		setPosition(alignment, x, y, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	public void setPosition(int alignment, int x, int y, int dx, int dy, int frameX, int frameY){
		this.alignment = alignment;
		this.setPos(x, y);
		
		switch(alignment){
			
			case Alignment.CENTER:
				setAbsolutePosition(dx + frameX/2 - sizeX/2 + x, dy + frameY/2 - sizeY/2 + y);
				break;
				
			case Alignment.CENTERLEFT:
				setAbsolutePosition(dx + x, dy + frameY/2 - sizeY/2  + y);
				break;
				
			case Alignment.CENTERRIGHT:
				setAbsolutePosition(dx + frameX - sizeX + x, dy + frameY/2  - sizeY/2 + y);
				break;
				
			case Alignment.DOWNLEFT:
				setAbsolutePosition(dx + x, dy + y);
				break;
				
			case Alignment.DOWNCENTER:
				setAbsolutePosition(dx + frameX/2 - sizeX/2 + x, dy + y);
				break;
				
			case Alignment.DOWNRIGHT:
				setAbsolutePosition(dx + frameX - sizeX + x, dy + y);
				break;
				
			case Alignment.UPLEFT:
				setAbsolutePosition(dx + x, dy + frameY - sizeY + y);
				break;
				
			case Alignment.UPCENTER:
				setAbsolutePosition(dx + frameX/2 - sizeX/2 + x, dy +  frameY - sizeY + y);
				break;
				
			case Alignment.UPRIGTH:
				setAbsolutePosition(dx + frameX - sizeX + x, dy + frameY - sizeY + y);
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

	public void setFont(int fontKey) {
		this.font = Resources.getFont(fontKey);
	}
	
	public void setLayer(int layer){
		this.layer = layer;
	}

	public int getLayer() {
		return layer;
	}
	
	public boolean mouseCollision(int inputX, int inputY){
		int mouseX = inputX;
		int mouseY = Gdx.graphics.getHeight() - inputY;
		
		if(mouseX > x && mouseX < x + sizeX && mouseY > y && mouseY < y + sizeY){
			return true;
		}
		else{
			return false;
		}
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
