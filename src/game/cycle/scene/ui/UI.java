package game.cycle.scene.ui;

import game.cycle.input.UserInput;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

abstract public class UI {
	
	// widgets container
	private TreeMap<Integer, HashMap<String, Widget>> widgets;
	
	// selecting
	public boolean selected;
	public Widget widgetSelected;
	
	// mouse click
	protected boolean leftClick;
	protected boolean rightClick;
	
	public UI() {
		widgets = new TreeMap<Integer, HashMap<String, Widget>>(Collections.reverseOrder());
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
				if(element.isVisible()){
					element.draw(sprites);
				}
			}
		}
	}
	
	public void update() {
		updateSelect();
		updateClick();
	}
	
	private void updateSelect(){
		selected = false;
		widgetSelected = null;
		
		Set<Integer> keys = widgets.keySet();
		for(Integer key: keys){
			HashMap<String, Widget> layer = widgets.get(key);
			
			for(Widget element: layer.values()){
				if(element.isVisible() && !selected && element.mouseCollision()){
					element.selected = true;
					selected = true;
					widgetSelected = element;
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
					UserInput.draggedElement = (Dragged)widgetSelected;
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
		if(selected){
			if(widgetSelected.keyInput){
				KeyInput element = (KeyInput)widgetSelected;
				element.key(key);
			}
		}
	}
	
	abstract public void onload();
	abstract public void onclose();
}
