package game.cycle.scene.game.world.location.creature.struct;

public class BodyPart {

	public int hp;
	public int hpmax;
	
	public BodyPart(int stamina) {
		this.hp = this.hpmax = stamina;
	}
}
