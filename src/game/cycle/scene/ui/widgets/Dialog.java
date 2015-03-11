package game.cycle.scene.ui.widgets;

import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.ui.Widget;
import game.resources.Resources;
import game.resources.Tex;

import org.apache.commons.lang3.text.WordUtils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Dialog extends Widget {

	private Creature npc;
	
	private Texture back;
	
	public Dialog(String title) {
		super(title);
		setPosition(Alignment.CENTERLEFT, 0, -300);
		setSize(500, 600);
		back = Resources.getTex(Tex.uiBackground);
	}
	
	public void setCreature(Creature character) {
		this.npc = character;
	}
	
	private final int wrapCharactersCount = 60;
	private final String wraper = "\n";
	
	public void setDialog(SpriteBatch sprites){
		String text = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum.";
		text = WordUtils.wrap(text, wrapCharactersCount, wraper, true);
		String [] enterText = text.split(wraper);
		
		int i = 0;
		for(String line: enterText){
			font.drawWrapped(sprites, line, x + 10, y -  font.getBounds(line).height * (i * 2) + sizeY - 140, font.getBounds(line).width, BitmapFont.HAlignment.CENTER);
			i++;
		}
	}
	
	@Override
	public void draw(SpriteBatch sprites) {
		if(npc != null){
			sprites.draw(back, x, y, sizeX, sizeY);
			sprites.draw(npc.avatar, x, y + sizeY - 128, 128, 128);
			
			String textNM = "" + npc.name;
			String textHP = "HP: " + npc.hp + "/" + npc.hpMax;
			String textEN = "Energy:  " + npc.energy + "/" + npc.energyMax;
			
			font.drawWrapped(sprites, textNM, x + 128, y -  font.getBounds(textNM).height * 2 + sizeY, font.getBounds(textNM).width, BitmapFont.HAlignment.CENTER);
			font.drawWrapped(sprites, textHP, x + 128, y -  font.getBounds(textHP).height * 4 + sizeY, font.getBounds(textHP).width, BitmapFont.HAlignment.CENTER);
			font.drawWrapped(sprites, textEN, x + 128, y -  font.getBounds(textEN).height * 6 + sizeY, font.getBounds(textEN).width, BitmapFont.HAlignment.CENTER);
			
			setDialog(sprites);
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
		back = null;
		npc = null;
	}
}
