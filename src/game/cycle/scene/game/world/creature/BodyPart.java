package game.cycle.scene.game.world.creature;

public class BodyPart {

	public int hp;
	public int hpmax;
	
	public BodyPart(int stamina) {
		this.hp = this.hpmax = stamina;
	}
}
