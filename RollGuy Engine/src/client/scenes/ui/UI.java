package client.scenes.ui;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import client.scenes.ui.interfaces.Dragged;
import client.scenes.ui.interfaces.KeyInput;
import client.scenes.ui.interfaces.Scroll;
import client.scenes.ui.widget.Console;
import client.scenes.ui.widget.Information;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

abstract public class UI {
	
	// keys
	public static final String UI_CONSOLE = "ui-console";
	public static final String UI_INFORMATION = "ui-information-label";
	
	// data
	private TreeMap<Integer, HashMap<String, Widget>> widgets;
	
	// selecting
	public boolean selected;
	public Widget widgetSelected;
	public Dragged widgetDragged;
	
	// global widgets
	private static Console console;
	private static Information information;
	private static Tooltip tooltip;
	
	// mouse click
	private boolean leftClick;
	private boolean rightClick;
	
	public UI() {
		widgets = new TreeMap<Integer, HashMap<String, Widget>>(Collections.reverseOrder());
		
		// global
		if(console == null){
			console = new Console(UI_CONSOLE);
			console.layer = Integer.MAX_VALUE;
		}
		
		if(information == null){
			information = new Information(UI_INFORMATION);
			information.layer = Integer.MAX_VALUE;
		}
		
		this.add(console);
		this.add(information);
	}
	
	public void add(Widget element){
		HashMap<String, Widget> layer = widgets.get(element.layer);
		
		if(layer == null){
			layer = new HashMap<String, Widget>();
			widgets.put(element.layer, layer);
		}
		
		layer.put(element.title, element);
	}
	
	public Widget get(String title){
		for(HashMap<String, Widget> layer: widgets.values()){
			for(Widget element: layer.values()){
				if(element.title.equals(title)){
					return element;
				}
			}
		}
		
		return null;
	}
	
	public void remove(String title){
		for(HashMap<String, Widget> layer: widgets.values()){
			layer.remove(title);
		}
	}
	
	public void draw(SpriteBatch sprites){
		for(HashMap<String, Widget> layer: widgets.values()){
			for(Widget element: layer.values()){
				if(element.visible){
					element.draw(sprites);
				}
			}
		}
		
		if(tooltip != null && widgetDragged == null){
			tooltip.draw(sprites);
		}
	}
	
	public void update() {
		updateSelect();
		updateClick();
	}

	private void updateSelect() {
		selected = false;
		widgetSelected = null;
		tooltip = null;
		
		Set<Integer> keys = widgets.keySet();
		for(Integer key: keys){
			HashMap<String, Widget> layer = widgets.get(key);
			
			for(Widget element: layer.values()){
				if(element.visible && !selected && element.mouseCollision()){
					element.selected = true;
					selected = true;
					widgetSelected = element;
					if(element.tooltip != null){
						tooltip = element.tooltip;
					}
					break;
				}
				else{
					element.selected = false;
				}
			}
		}
	}

	private void updateClick() {
		if(this.leftClick){
			if(selected){
				widgetSelected.execute();
			}
			
			this.leftClick = false;
		}
		
		if(this.rightClick){
			this.rightClick = false;
		}
	}
	
	// UserInput events
	public void click(int button) {
		if(button == Input.Buttons.LEFT){
			this.leftClick = true;
			
			if(selected){
				if(widgetSelected.draggble){
					widgetDragged = (Dragged)widgetSelected;
				}
			}
		}
		
		if(button == Input.Buttons.RIGHT){
			this.rightClick = true;
		}
	}
	
	public void scroll(int amount) {
		if(selected){
			if(widgetSelected.scroll){
				Scroll element = (Scroll)widgetSelected;
				element.scroll(amount);
			}
		}
	}
	
	public void inputChar(char key){
		if(console != null && console.visible){
			KeyInput element = (KeyInput)console;
			element.key(key);
		}
		else{
			if(selected){
				if(widgetSelected.keyInput){
					KeyInput element = (KeyInput)widgetSelected;
					element.key(key);
				}
			}
		}
	}

	abstract public void onload();
	abstract public void onclose();
}
