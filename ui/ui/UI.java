package ui;

import game.cycle.input.UserInput;
import game.cycle.scene.ui.interfaces.Dragged;
import game.cycle.scene.ui.interfaces.KeyInput;
import game.cycle.scene.ui.interfaces.Scroll;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import ui.widgets.Console;
import ui.widgets.Information;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

abstract public class UI {
	
	// 
	public static final String UI_CONSOLE = "ui-console";
	public static final String UI_INFORMATION = "ui-information-label";
	
	// widgets container
	private TreeMap<Integer, HashMap<String, Widget>> widgets;
	private static Console console;
	private static Information information;
	
	// tooltip
	private Tooltip tooltip;
	
	// selecting
	private Widget widgetSelected;
	private Widget widgetFocus;
	
	// mouse click
	protected boolean leftClick;
	protected boolean rightClick;
	
	public UI() {
		widgets = new TreeMap<Integer, HashMap<String, Widget>>(Collections.reverseOrder());
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
				if(element.isVisible()){
					element.draw(sprites);
				}
			}
		}
		
		if(tooltip != null && UserInput.draggedElement == null){
			tooltip.draw(sprites);
		}
	}

	private void updateSelect(int x, int y){
		widgetSelected = null;
		tooltip = null;
		
		Set<Integer> keys = widgets.keySet();
		for(Integer key: keys){
			HashMap<String, Widget> layer = widgets.get(key);
			
			for(Widget element: layer.values()){
				if(element.isVisible() && !isSelected() && element.mouseCollision(x, y)){
					element.selected = true;
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
			if(isSelected()){
				widgetSelected.execute();
			}
			
			this.leftClick = false;
		}
		
		if(this.rightClick){
			this.rightClick = false;
		}
	}
	
	// UserInput events
	public void mouseMoved(int x, int y) {
		updateSelect(x, y);
	}
	
	public void click(int button) {
		// left
		if(button == Input.Buttons.LEFT){
			this.leftClick = true;
			
			if(isSelected()){
				if(widgetSelected.draggble){
					UserInput.draggedElement = (Dragged)widgetSelected;
				}
			}
		}
		
		// right
		if(button == Input.Buttons.RIGHT){
			this.rightClick = true;
		}
		
		updateClick();
	}
	
	public void scroll(int amount) {
		if(isSelected()){
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
			if(isSelected()){
				if(widgetSelected.keyInput){
					KeyInput element = (KeyInput)widgetSelected;
					element.key(key);
				}
			}
		}
	}

	public static void initConsole() {
		if(UI.console == null){
			UI.console = new Console(UI_CONSOLE);
			UI.console.setLayer(Integer.MAX_VALUE);
			
			UI.information = new Information(UI_INFORMATION);
			UI.information.setLayer(Integer.MAX_VALUE);
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
	
	public static void setInformation(String text){
		information.setText(text);
	}
	
	// selected
	public boolean isSelected(){
		return widgetSelected != null;
	}
	
	public Widget getSelected(){
		return widgetSelected;
	}
	
	public boolean isFocused(){
		return widgetFocus != null;
	}
	
	abstract public void onload();
	abstract public void onclose();
}
