package game.cycle.scene.ui.widgets;

import java.util.HashMap;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.cycle.scene.ui.Dragged;
import game.cycle.scene.ui.UI;
import game.cycle.scene.ui.Widget;
import game.script.ui.ui_WindowClose;

public class Window extends Widget implements Dragged {

	protected UI ui;
	protected TreeMap<Integer, HashMap<String, Widget>> widgets;
	
	protected boolean dragged;
	protected int clickDeltax;
	protected int clickDeltay;
	
	protected Button button;
	
	public Window(String title, UI ui, Alignment alignment,  int sizeX, int sizeY, int x, int y, int layer) {
		super(title);
		this.ui = ui;
		this.draggble = true;
		widgets = new TreeMap<Integer, HashMap<String,Widget>>();
		
		setSize(sizeX, sizeY);
		setPosition(alignment, x, y);
		setLayer(1);
		ui.add(this);
		
		button = new Button(title + "-close-button", "x");
		button.setSize(24, 24);
		button.setPosition(Alignment.UPRIGTH, 0, 0);
		button.setLayer(this.getLayer() + 1);
		button.setScript(new ui_WindowClose(this));
		this.add(button);
		ui.add(button);
	}

	public void add(Widget element){
		HashMap<String, Widget> layer = widgets.get(element.getLayer());
		
		if(layer == null){
			layer = new HashMap<String, Widget>();
			widgets.put(element.getLayer(), layer);
		}
		
		layer.put(element.title, element);
		ui.add(element);
		element.setPosition(element.getPosX(), element.getPosY(), x, y, sizeX, sizeY);
	}
	
	public void remove(String title){
		for(HashMap<String, Widget> layer: widgets.values()){
			layer.remove(title);
		}
	}

	public void setVisible(boolean visible) {
		super.setVisible(visible);
		this.button.setVisible(visible);
	}

	@Override
	public void draw(SpriteBatch sprites) {
		sprites.draw(texNormal, x, y, sizeX, sizeY);
		
		for(HashMap<String, Widget> layer: widgets.values()){
			for(Widget element: layer.values()){
				if(element.isVisible()){
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
