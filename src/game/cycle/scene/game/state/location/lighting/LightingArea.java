package game.cycle.scene.game.state.location.lighting;

public class LightingArea {

	public int x0;
	public int y0;
	
	public int x1;
	public int y1;
	
	public int power;
	
	public LightingArea(int x0, int y0, int x1, int y1, int power) {
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
		this.power = power;
	}
}
