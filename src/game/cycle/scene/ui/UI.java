package game.cycle.scene.ui;

import game.cycle.input.UserInput;
import game.cycle.scene.ui.interfaces.Dragged;
import game.cycle.scene.ui.interfaces.KeyInput;
import game.cycle.scene.ui.interfaces.Scroll;
import game.cycle.scene.ui.widgets.Console;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

abstract public class UI {
	
	// 
	public static final String uiConsole = "ui-console";
	
	// widgets container
	private TreeMap<Integer, HashMap<String, Widget>> widgets;
	private static Console console;
	
	// tooltip
	private Tooltip tooltip;
	
	// selecting
	public boolean selected;
	public Widget widgetSelected;
	
	// mouse click
	protected boolean leftClick;
	protected boolean rightClick;
	
	public UI() {
		widgets = new TreeMap<Integer, HashMap<String, Widget>>(Collections.reverseOrder());
		this.add(console);
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
		
		if(tooltip != null && UserInput.draggedElement == null){
			tooltip.draw(sprites);
		}
	}
	
	public void update() {
		updateSelect();
		updateClick();
	}
	
	private void updateSelect(){
		selected = false;
		widgetSelected = null;
		tooltip = null;
		
		Set<Integer> keys = widgets.keySet();
		for(Integer key: keys){
			HashMap<String, Widget> layer = widgets.get(key);
			
			for(Widget element: layer.values()){
				if(element.isVisible() && !selected && element.mouseCollision()){
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
		if(console != null && console.isVisible()){
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

	public static void initConsole() {
		if(UI.console == null){
			UI.console = new Console(uiConsole);
			UI.console.setLayer(Integer.MAX_VALUE);
		}
	}
	
	public static void addConsoleLine(String text){
		console.addLine(text);
	}
	
	public static void showConsole(boolean visible) {
		console.setVisible(true);
	}

	public static void showConsole() {
		console.setVisible(!console.isVisible());
	}
	
	public static boolean isConsoleVisible() {
		return console.isVisible();
	}
	
	abstract public void onload();
	abstract public void onclose();
}
