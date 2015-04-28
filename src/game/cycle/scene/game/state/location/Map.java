package game.cycle.scene.game.state.location;

import java.util.HashMap;

import game.cycle.scene.game.state.database.GameConst;
import game.cycle.scene.game.state.database.proto.LocationProto;
import game.cycle.scene.game.state.location.creature.Player;
import game.cycle.scene.game.state.skill.Skill;
import game.cycle.scene.ui.list.UIGame;
import game.resources.Resources;
import game.resources.tex.Tex;
import game.resources.tex.TexAtlas;
import game.resources.tex.TexLighting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Map implements Disposable {

	// data
	private Node [][] map;
	private Chunk [][] chunks;
	
	// resources
	private final Texture [] texes;
	private final HashMap<Integer, TexAtlas> atlases;
	
	// buffers
	private TexLighting lightingTex;
	
	// pools
	private Array<PooledEffect> effects;
	private Array<Bullet> bullets;
	
	public Map(LocationProto proto, Node [][] map) {
		this.map = map;
		
		// init chunks
		this.chunks = new Chunk[proto.sizeX()/GameConst.MAP_CHUNK_SIZE][proto.sizeY()/GameConst.MAP_CHUNK_SIZE];
		for(int i = 0; i < proto.sizeX()/GameConst.MAP_CHUNK_SIZE; ++i){
			for(int j = 0; j < proto.sizeY()/GameConst.MAP_CHUNK_SIZE; ++j){
				this.chunks[i][j] = new Chunk(i, j, map);
			}
		}
		
		// graphics
		texes = Resources.getLocationSpriteSet();
		lightingTex = (TexLighting)(Resources.getTexWrap(Tex.LIGHT));
		atlases = new HashMap<Integer, TexAtlas>();
		atlases.put(Tex.TEX_ATLAS_0, (TexAtlas)Resources.getTexWrap(Tex.TEX_ATLAS_0));
		
		// pools
		effects = new Array<PooledEffect>();
		bullets = new Array<Bullet>();
	}

	// obj managment
	public boolean addObject(LocationObject object, float x, float y) {
		int posx = (int)(x/(GameConst.TILE_SIZE*GameConst.MAP_CHUNK_SIZE));
		int posy = (int)(y/(GameConst.TILE_SIZE*GameConst.MAP_CHUNK_SIZE));
		return chunks[posx][posy].addObject(object, x, y);
	}
	
	public void removeObject(int guid) {
		
	}
	
	/// draw
	public void draw(LocationProto proto, OrthographicCamera camera, SpriteBatch batch, boolean los, UIGame ui, Player player){
		float delta = Gdx.graphics.getDeltaTime();
		
		Node node = null;
		
		int x = (int)(camera.position.x / GameConst.TILE_SIZE);
		int y = (int)(camera.position.y / GameConst.TILE_SIZE);
		int w = (Gdx.graphics.getWidth() /GameConst.TILE_SIZE + 4)/2;
		int h = (Gdx.graphics.getHeight()/GameConst.TILE_SIZE + 4)/2;
		

		int xmin = Math.max(0, x - w);
		int ymin = Math.max(0, y - h);
		int xmax = Math.min(proto.sizeX(), x + w);
		int ymax = Math.min(proto.sizeY(), y + h);
		
		for(int i = xmin; i < xmax; ++i){
			for(int j = ymax - 1; j >= ymin; --j){
				node = map[i][j];
				
				if(los){
					drawLos(batch, node, i*GameConst.TILE_SIZE, j*GameConst.TILE_SIZE, player, ui);
				}
				else{
					drawNoLos(batch, node, i*GameConst.TILE_SIZE, j*GameConst.TILE_SIZE, player, ui);
				}
			}
		}
		
		for(int i = 0; i < effects.size; ++i){
			PooledEffect effect = effects.get(i);
			effect.draw(batch, delta);

			if(effect.isComplete()){
				effect.free();
				effects.removeIndex(i);
			}
		}
		
		for(int i = 0; i < bullets.size; ++i){
			Bullet bullet = bullets.get(i);
			bullet.draw(batch, delta);
			
			if(bullet.isEnded()){
				bullets.removeIndex(i);
				Resources.bulletPool.free(bullet);
			}
		}
	}
	
	private void drawLos(SpriteBatch batch, Node node, int x, int y, Player player, UIGame ui){
		if(node.explored && node.viewed){
			if(node.proto.tex() >= Tex.TEX_ATLAS_0){
				batch.draw(atlases.get(node.proto.tex()).arr[node.proto.atlasId()], x, y);
			}
			else{
				batch.draw(texes[node.proto.tex()], x, y, GameConst.TILE_SIZE, GameConst.TILE_SIZE);
			}
	
			// lighting
			int power = node.lighting/10;
			power = Math.max(0, power);
			power = Math.min(10, power);
			
			// draw lighting
			batch.draw(lightingTex.power[power], x, y);
		}
		else{
			batch.draw(lightingTex.power[0], x, y);
		}
	}
	
	private void drawNoLos(SpriteBatch batch, Node node, int x, int y, Player player, UIGame ui){
		if(node.proto.tex() >= Tex.TEX_ATLAS_0){
			batch.draw(atlases.get(node.proto.tex()).arr[node.proto.atlasId()], x, y);
		}
		else{
			batch.draw(texes[node.proto.tex()], x, y, GameConst.TILE_SIZE, GameConst.TILE_SIZE);
		}
	}

	public void addEffect(Skill skill, LocationObject caster, LocationObject target) {
		PooledEffect effect = Resources.getEffect(skill.partical);
		effect.start();
		
		if(skill.type == Skill.TYPE_SELFCAST){
			effect.setPosition(caster.getSpriteX() + GameConst.TILE_SIZE/2, caster.getSpriteY() + GameConst.TILE_SIZE/2);
			effects.add(effect);
		}
		else if(skill.type == Skill.TYPE_RANGE){
			Bullet bullet = Resources.bulletPool.obtain();
			bullet.init(effect, caster.getSpriteX(), caster.getSpriteY(), target.getSpriteX(), target.getSpriteY());
			bullets.add(bullet);
		}
		else{
			return;
		}
	}

	@Override
	public void dispose() {
		for(int i = 0; i < effects.size; ++i){
			effects.get(i).free();
			effects.clear();
		}
	}
}
