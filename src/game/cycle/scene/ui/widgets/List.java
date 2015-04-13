package game.cycle.scene.ui.widgets;

import game.cycle.scene.ui.Widget;
import game.cycle.scene.ui.interfaces.Scroll;
import game.resources.tex.Tex;
import game.script.ui.ui_ListSelect;
import game.tools.Const;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class List extends Widget implements Scroll {
	
	protected static final int lineHeight = 18;
	protected int maxDisplay = 22;
	protected int lineSelected = Const.INVALID_ID;
	protected int scrollAmount = 0;
	
	protected Vector<ListItem> items;
	
	public List(String title) {
		super(title);
		this.scroll = true;
		
		setScript(new ui_ListSelect(this));
		items = new Vector<ListItem>();
		
		setTexNormal(Tex.UI_BACKGROUND_NORMAL);
		setTexSelected(Tex.UI_LIST_LINE);
	}
	
	public void addElement(ListItem item){
		this.items.addElement(item);
	}
	
	public ListItem remove(int line){
		if(line < items.size() && line >= 0){
			return this.items.remove(line);
		}
		else{
			return null;
		}
	}
	
	public void setVisible(int lines){
		if(lines > 0){
			this.maxDisplay = lines;
		}
	}
	
	public ListItem selectLine(int mouseX, int mouseY) {
		int elementY = mouseY - (Gdx.graphics.getHeight() - (y + sizeY));
		lineSelected = (elementY / lineHeight) + scrollAmount;
		
		if(lineSelected < 0 || lineSelected >= items.size()){
			lineSelected = Const.INVALID_ID;
		}
		
		return getSelected();
	}
	
	public ListItem getSelected(){
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
			font.draw(sprites, items.get(i).toString(), x + 5, y + sizeY - j * lineHeight - 4);
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
		this.items.clear();
		this.items = null;
	}
}
