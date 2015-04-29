package client.scenes.ui.widget;

import java.util.HashMap;
import java.util.TreeMap;

import client.scenes.ui.Alignment;
import client.scenes.ui.UI;
import client.scenes.ui.Widget;
import client.scenes.ui.interfaces.Dragged;
import client.scenes.ui.script.list.ui_WindowClose;
import client.scenes.ui.script.list.ui_WindowLock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;

public class Window extends Widget implements Dragged {

	protected String text;
	protected TextBounds bounds;
	
	protected UI ui;
	protected TreeMap<Integer, HashMap<String, Widget>> widgets;
	
	protected boolean dragged;
	protected int clickDeltax;
	protected int clickDeltay;
	
	protected Button closeButton;
	protected Button lockButton;
	
	public Window(String title, UI ui, int alignment,  int sizeX, int sizeY, int x, int y, int layer) {
		super(title);
		this.ui = ui;
		this.draggble = true;
		widgets = new TreeMap<Integer, HashMap<String,Widget>>();
		
		setSize(sizeX, sizeY);
		setPosition(alignment, x, y);
		layer = 1;
		ui.add(this);
	}
	
	public void closeButton(boolean load){
		closeButton = new Button(title + "-close-button", "x");
		closeButton.setSize(24, 24);
		closeButton.setPosition(Alignment.UPRIGTH, 0, 0);
		closeButton.setScript(new ui_WindowClose(this));
		this.add(closeButton);
	}
	
	public void lockButton(boolean load){
		lockButton = new Button(title + "-lock-button", "L");
		lockButton.setSize(24, 24);
		
		if(closeButton == null)
			lockButton.setPosition(Alignment.UPRIGTH, 0, 0);
		else
			lockButton.setPosition(Alignment.UPRIGTH, -24, 0);
		
		lockButton.setScript(new ui_WindowLock(this));
		this.add(lockButton);
	}

	public void add(Widget element){
		element.setLayer(layer + element.getLayer() + 1);
		HashMap<String, Widget> layer = widgets.get(element.getLayer());
		
		if(layer == null){
			layer = new HashMap<String, Widget>();
			widgets.put(element.getLayer(), layer);
		}
		
		layer.put(element.title(), element);
		ui.add(element);
		element.setPosition(element.getPosX(), element.getPosY(), x, y, sizeX, sizeY);
	}
	
	public void remove(String title){
		for(HashMap<String, Widget> layer: widgets.values()){
			layer.remove(title);
		}
		ui.remove(title);
	}

	public void setVisible(boolean visible) {
		super.setVisible(visible);
		
		for(HashMap<String, Widget> layer: widgets.values()){
			for(Widget element: layer.values()){
				element.setVisible(visible);
			}
		}
	}
	
	public void setText(String text){
		this.text = text;
		bounds = font.getBounds(text);
	}
	
	public String getText(){
		return text;
	}
	
	public void lock() {
		this.draggble = !draggble;
		this.lockButton.setActive(!draggble);
	}
	
	@Override
	public void draw(SpriteBatch sprites) {
		sprites.draw(texNormal, x, y, sizeX, sizeY);
		
		if(text != null){
			int width = sizeX;
			if(this.closeButton != null){
				width -= closeButton.getSizeX();
			}
			if(this.lockButton != null){
				width -= lockButton.getSizeY();
			}
			
			font.drawWrapped(sprites, this.text, x, y + this.bounds.height * 2, width, BitmapFont.HAlignment.CENTER);
		}
		
		for(HashMap<String, Widget> layer: widgets.values()){
			for(Widget element: layer.values()){
				if(element.getVisible()){
					element.draw(sprites);
				}
			}
		}
	}
	
	@Override
	public void dragg(int x, int y) {
		if(!dragged){
			clickDeltax = this.x - x;
			clickDeltay = this.y - y;
			dragged = true;
		}
		
		int dx = this.x - x - clickDeltax;
		int dy = this.y - y - clickDeltay;
		
		//
		if(this.x - dx < 0){
			dx = this.x;
		}
		if(this.y - dy < 0){
			dy = this.y;
		}
		
		int addx = Gdx.graphics.getWidth() - (this.x - dx + sizeX);
		if(addx < 0){
			dx -= addx;
		}
		
		int addy = Gdx.graphics.getHeight() - (this.y - dy + sizeY);
		if(addy < 0){
			dy -= addy;
		}

		// drag all elements
		this.translate(dx, dy);
	}

	@Override
	public void translate(int dx, int dy) {
		super.translate(dx, dy);
		for(HashMap<String, Widget> layer: widgets.values()){
			for(Widget element: layer.values()){
				element.translate(dx, dy);
			}
		}
	}
	
	@Override
	public void resetDragg() {
		this.dragged = false;
	}
}
