package ui.widgets;

import java.util.Vector;

import ui.Widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.cycle.scene.ui.interfaces.Dragged;
import game.cycle.scene.ui.interfaces.Scroll;
import game.resources.Cursors;
import game.resources.tex.Tex;
import game.script.ui.ui_SkillListSlect;
import game.tools.Const;

public class SpellBook extends Widget implements Scroll, Dragged{

	private static final int lineHeight = 50;
	private static final int maxDisplay = 10;
	public Vector<SpellBookItem> items;
	
	private int lineSelected;
	private int scrollAmount;
	
	// spell drag
	private int clickDeltax;
	private int clickDeltay;
	private boolean dragged;
	
	public SpellBook(String title) {
		super(title);
		this.draggble = true;
		this.scroll = true;
		this.items = new Vector<SpellBookItem>();
		setScript(new ui_SkillListSlect(this));
		setTexNormal(Tex.UI_BACKGROUND_NORMAL);
		setTexSelected(Tex.UI_LIST_LINE);
	}
	
	public void addElement(SpellBookItem item){
		this.items.addElement(item);
	}
	
	public SpellBookItem remove(int line){
		if(line < items.size() && line >= 0){
			return this.items.remove(line);
		}
		else{
			return null;
		}
	}
	
	public SpellBookItem selectLine(int mouseX, int mouseY) {
		int elementY = mouseY - (Gdx.graphics.getHeight() - (y + sizeY));
		lineSelected = (elementY / lineHeight) + scrollAmount;
		
		if(lineSelected < 0 || lineSelected >= items.size()){
			lineSelected = Const.INVALID_ID;
		}
		
		return getSelected();
	}
	
	public SpellBookItem getSelected(){
		if(lineSelected != Const.INVALID_ID){
			return items.get(lineSelected);
		}
		else{
			return null;
		}
	}
	
	public void clear(){
		this.selected = false;
		this.lineSelected = Const.INVALID_ID;
		this.items.clear();
	}
	
	@Override
	public void scroll(int amount) {
		scrollAmount += amount;
		
		if(scrollAmount < 0 || scrollAmount >= items.size()){
			scrollAmount -= amount;
		}
	}

	@Override
	public void draw(SpriteBatch sprites) {
		sprites.draw(texNormal, x, y, sizeX, sizeY);
		
		if(lineSelected != Const.INVALID_ID){
			int drawY = y + sizeY - (lineSelected - scrollAmount) * lineHeight;
			
			if(drawY < (y + sizeY + lineHeight) && drawY > y){
				sprites.draw(texSelected, x, drawY, sizeX, -lineHeight);
			}
		}
		
		for(int i = scrollAmount, j = 0; j < maxDisplay && i < items.size(); ++i, ++j){
			items.get(i).draw(sprites, font, x + 5, y + sizeY - (j+1) * lineHeight - 4);
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
		this.items.clear();
		this.items = null;
	}

	@Override
	public void dragg(int x, int y) {
		if(getSelected() != null && Cursors.getSelectedItem() == null){
			if(!dragged){
				clickDeltax = this.x - x;
				clickDeltay = this.y - y;
				dragged = true;
			}
		
			int dx = this.x - x - clickDeltax;
			int dy = this.y - y - clickDeltay;
		
			if(dx*dx+dy*dy > 256){ // 16px - skill pickup sensivity
				Cursors.setSelectedSkill(getSelected().getSkill());
			}
		}
	}

	@Override
	public void resetDragg() {
		dragged = false;
	}
}
