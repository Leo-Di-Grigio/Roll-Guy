package game.cycle.scene.ui.widgets;

import java.util.HashMap;
import java.util.TreeMap;

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
	
	public Window(String title, UI ui, int sizeX, int sizeY, int posX, int posY, int layer) {
		this(title, ui, Alignment.DOWNLEFT, sizeX, sizeY, posX, posY, layer);
	}
	
	public Window(String title, UI ui, Alignment alignment, int sizeX, int sizeY, int posX, int posY, int layer) {
		super(title);
		this.ui = ui;
		this.draggble = true;
		widgets = new TreeMap<Integer, HashMap<String,Widget>>();
		
		setSize(sizeX, sizeY);
		setPosition(alignment, posY, posY);
		setLayer(1);
		ui.add(this);
		
		button = new Button(title + "-close-button", "x");
		button.visible = true;
		button.setSize(24, 24);
		button.setPosition(Alignment.UPRIGTH, 0, 0);
		button.setLayer(2);
		button.setScript(new ui_WindowClose(this));
		ui.add(button);
		this.add(button);
	}

	public void add(Widget element){
		HashMap<String, Widget> layer = widgets.get(element.getLayer());
		
		if(layer == null){
			layer = new HashMap<String, Widget>();
			widgets.put(element.getLayer(), layer);
		}
		
		layer.put(element.title, element);
		ui.add(element);
		element.setPosition(element.getPosX(), element.getPosY(), x, y, sizeY, sizeX);
	}
	
	public void remove(String title){
		for(HashMap<String, Widget> layer: widgets.values()){
			layer.remove(title);
		}
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		this.button.visible = visible;
	}

	@Override
	public void draw(SpriteBatch sprites) {
		sprites.draw(texNormal, x, y, sizeX, sizeY);
		
		for(HashMap<String, Widget> layer: widgets.values()){
			for(Widget element: layer.values()){
				if(element.visible){
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
		this.translate(dx, dy);
		
		// drag all elements
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
