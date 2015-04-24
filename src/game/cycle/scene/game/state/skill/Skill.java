package game.cycle.scene.game.state.skill;

import com.badlogic.gdx.graphics.Texture;

public class Skill {
	// cursor
	public static final int TYPE_NULL = 0;
	public static final int TYPE_SELFCAST = 1;
	public static final int TYPE_POINT_BLANK = 2;
	public static final int TYPE_RANGE = 3;
	public static final int TYPE_AOE = 4;
	
	public int id;
	public int type;
	public Texture tex;
	public String title;
	public String tooltip;
	
	// gameplay
	public int ap;
	public float range;
	
	// sound\visual effect
	public int partical;
	public int sound;
	
	public SkillEffect [] effects;
}
