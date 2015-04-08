package game.resources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

public class Tex implements Disposable {
	
	// keys
	public static final int texNull = 0;
	
	// keys UI
	public static final int uiButtonNormal = 100;
	public static final int uiButtonSelected = 101;
	public static final int uiButtonClick = 102;
	public static final int uiBackgroundNormal = 103;
	public static final int uiBackgroundSelected = 104;
	public static final int uiBackgroundLightSelected = 105;
	public static final int uiListLine = 106;
	public static final int uiInventorySlot = 107;
	public static final int uiInventorySlotHead = 108;
	public static final int uiInventorySlotChest = 109;
	public static final int uiInventorySlotHand1 = 110;
	public static final int uiInventorySlotHand2 = 111;
	public static final int uiUseSkillFrame = 112;
	
	// tile select
	public static final int tileSelect = 150;
	public static final int tileWaypoint = 151;

	// keys tiles
	public static final int tileNull = 200;
	public static final int tileGrass = 201;
	public static final int tileWall = 202;
	public static final int tileWater = 203;
	public static final int tileAlumina = 204;
	public static final int tileCrackedAlumina = 205;
	public static final int tileStone = 206;
	public static final int tileSandStoneCoast = 207;
	public static final int tileRiver = 208;
	public static final int tileSand = 209;
	public static final int tileSandStone = 210;
	
	// lighting
	public static final int lightingFog = 300;
	public static final int lightingColors = 301;
	
	// creature
	public static final int creaturePlayer = 1000;
	public static final int creatureNpc = 1001;
	public static final int creatureCharacter = 1002;
	
	// creature avatar
	public static final int avatarNpc = 2000;
	
	// go
	public static final int go = 10000;
	public static final int goDoorOpen = 10001;
	public static final int goDoorClose = 10002;
	public static final int goChest = 10003;
	public static final int goLoot = 10004;
	public static final int goWayPoint = 10005;
	public static final int goTorch = 10006;
	
	// skills
	public static final int skill = 30000;
	public static final int skillMelee = 30001;
	public static final int skillHeal = 30002;
	public static final int skillDrag = 30003;
	
	// creature events
	public static final int npcWarning = 50000;
	
	// items
	public static final int itemNull0 = 100000;
	public static final int itemNull1 = 100001;
	public static final int itemNull2 = 100002;
	public static final int itemNull3 = 100003;
	
	// data
	public int id;
	public Texture tex;
	
	public Tex(int id, Texture tex) {
		this.id = id;
		this.tex = tex;
	}

	@Override
	public void dispose() {
		tex = null;
	}
}