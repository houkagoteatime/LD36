package com.github.houkagoteatime.LD36.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.github.houkagoteatime.LD36.entities.Entity;
import com.github.houkagoteatime.LD36.levels.Level;

public class Rock implements Weapon{
	private Entity owner;
	private Sprite rockProjectileSprite;
	private Level level;
	public int delayCounter;
	public static final int DAMAGE = 10;
	public static final int DELAY = 30;
	public static final float RANGE = 200;
	
	public Rock(Entity owner, Level level) {
		rockProjectileSprite = new Sprite(new Texture(Gdx.files.internal("assets/pictures/rockproj.png")));
		this.owner = owner;
		this.level = level;
	}

	@Override
	public void attack(float angle) {
		if(delayCounter >= DELAY) {
			new RockProjectile(rockProjectileSprite, DAMAGE, owner.getxPosition(), owner.getyPosition(), angle, RANGE, true, level).spawn(level);
			delayCounter = 0;
		}
	}
	
	public class RockProjectile extends Projectile {
		public RockProjectile(Sprite sprite, int damage, float xPosition, float yPosition, float angle, float range, boolean isFriendly, Level level) {
			super(sprite, damage, xPosition, yPosition, angle, range, isFriendly, level.getWallLayer());
		}
		
	}

	@Override
	public void incrementDelayCounter() {
		delayCounter++;
	}

	@Override
	public int getDelayCounter() {
		return delayCounter;
	}

	@Override
	public void setDelayCounter(int delay) {
		delayCounter = delay;
	}
}
