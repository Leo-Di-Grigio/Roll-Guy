package game.cycle.scene.game.state.skill;

import com.badlogic.gdx.graphics.Texture;

public class Skill {
	// cursor
	public static final int typeNull = 0;
	public static final int typeSpell = 1;
	public static final int typeMelee = 2;
	public static final int typeRange = 3;
	
	public int id;
	public int type;
	public Texture tex;
	public String title;
	public String tooltip;
	
	// gameplay
	public int ap;
	public float range;
	
	public Effect [] effects;
}
