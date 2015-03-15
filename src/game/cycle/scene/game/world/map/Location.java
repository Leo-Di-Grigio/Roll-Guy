package game.cycle.scene.game.world.map;

import java.util.ArrayList;

import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.game.world.creature.NPC;
import game.cycle.scene.game.world.creature.Player;
import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.go.GO;
import game.cycle.scene.game.world.go.GOFactory;
import game.cycle.scene.ui.list.UIGame;
import game.tools.Const;
import game.tools.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Location implements Disposable {
	
	public LocationProto proto;
	public static final int tileSize = 32;
	
	public int sizeX;
	public int sizeY;
	public Terrain [][] map;
	public Sprite [] sprites;
	
	public Location() {
		
	}
	
	public int counter;
	public void draw(OrthographicCamera camera, SpriteBatch batch) {
		ArrayList<Creature> creatures = new ArrayList<Creature>();
		Terrain node = null;
		counter = 0;
		
		int x = (int)(camera.position.x / tileSize);
		int y = (int)(camera.position.y / tileSize);
		int w = (Gdx.graphics.getWidth()/tileSize + 4)/2;
		int h = (Gdx.graphics.getHeight()/tileSize + 4)/2;
		
		int xmin = Math.max(0, x - w);
		int ymin = Math.max(0, y - h);
		int xmax = Math.min(sizeX, x + w);
		int ymax = Math.min(sizeY, y + h);
		
		for(int i = xmin; i < xmax; ++i){
			for(int j = ymin; j < ymax; ++j){
				counter++;
				node = map[i][j];
				
				sprites[node.proto.texture].setPosition(i*tileSize, j*tileSize);
				sprites[node.proto.texture].draw(batch);
				
				if(node.go != null){
					node.go.sprite.draw(batch);
				}

				if(node.creature != null){
					creatures.add(node.creature);
				}
			}
		}
		
		for(Creature creature: creatures){
			creature.draw(batch);
		}
	}

	@Override
	public void dispose() {
		for(int i = 0; i < sprites.length; ++i){
			sprites[i] = null;
		}
	}
	
	public boolean inBound(int x, int y){
		return (x >= 0 && x < sizeX && y >= 0 && y < sizeY);
	}

	// EDITOR
	public void editorTerrain(int x, int y, UIGame ui) {
		if(inBound(x, y)){
			int terrainid = ui.getSelectedListTerrain();
			
			if(terrainid != Const.invalidId){
				map[x][y].proto = Database.getTerrainProto(terrainid);	
			}
		}
	}

	public void editorNpc(int x, int y, UIGame ui) {
		if(inBound(x, y)){
			int id = ui.getSelectedListNpc();
			
			if(id != Const.invalidId){
				if(map[x][y].creature == null){
					map[x][y].creature = new NPC(Database.getCreature(id));
					map[x][y].creature.sprite.setPosition(x*tileSize, y*tileSize);
				}
				else{
					map[x][y].creature = null;
				}
			}
			else{
				map[x][y].creature = null;
			}
		}
	}

	public void editorGO(int x, int y, UIGame ui) {
		if(inBound(x, y)){
			int id = ui.getSelectedListGO();
			if(id != Const.invalidId){
				if(map[x][y].go == null){
					map[x][y].go = GOFactory.getGo(id, x, y, 0, 0, 0, 0);
				}
				else{
					map[x][y].go = null;
				}
			}
		}
	}

	public void editorGOParams(int x, int y, UIGame ui) {
		if(inBound(x, y)){
			ui.editorGOEdit.setGO(map[x][y].go);
		}
	}
	
	// PLAYER ACTIONS
	private float interactRange = 2*Location.tileSize * 1.45f;
	
	public void talkWithNpc(Player player, UIGame ui, int x, int y) {
		Creature npc = map[x][y].creature;
		
		if(npc != null && npc.id != player.id){
			float delta = getRange(player.sprite, npc.sprite);
			
			if(delta < interactRange){
				ui.npcTalk(ui, player, npc);
			}
		}
	}
	
	public void useGO(Player player, int x, int y){
		GO go = map[x][y].go;
		
		if(go != null && go.script1 != null){
			float delta = getRange(player.sprite, go.sprite);
			
			if(delta < interactRange){
				go.script1.execute();
			}
		}
	}

	public void attack(int x, int y, Creature damager) {
		int damage = damager.proto.stats.strength;
		
		if(inBound(x, y)){
			// attack the Creature
			Creature creature = map[x][y].creature;
			if(creature != null && creature.id != damager.id){
				float delta = getRange(damager.sprite, creature.sprite);
				
				if(delta < interactRange){
					boolean targetIsAlive = creature.damage(damage);
					
					if(!targetIsAlive){
						map[x][y].creature = null;
						Log.debug("Creature id: " + creature.id + " died");
					}
				}
				
				return;
			}
			
			// attack the GO
			GO go = map[x][y].go;
			if(go != null && go.proto.durabilityMax > 0){
				 go.durability -= damage;
				 
				 Log.debug("GO id: " + go.id + " get " + damage + " hp: " + go.durability + "/" + go.proto.durabilityMax);
				 
				 if(go.durability <= 0){
					 map[x][y].go = null;
					 Log.debug("GO id: " + go.id + " destroyed");
				 }
				 
				 return;
			}
		}
	}
	
	private float getRange(Sprite a, Sprite b){
		return new Vector2(a.getX() - b.getX(), a.getY() - b.getY()).len();
	}
}
