package com.github.houkagoteatime.LD36.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.github.houkagoteatime.LD36.entities.Entity;
import com.github.houkagoteatime.LD36.levels.Level;

public class GateOfBabylon extends ProjectileBasedWeapon{
	private int swordsFired;
	public static final int TILE_SIZE = 16;
	public GateOfBabylon(Entity owner, Level level, Sprite proj) {
		super(owner, level);
		range = 1000;
		projectileSprite = new Sprite(new Texture(Gdx.files.internal("assets/pictures/sword1.png")));
		delay = 70;
		friendly = false;
		swordsFired = 1;
	}

	/* (non-Javadoc)
	 * @see com.github.houkagoteatime.LD36.weapons.ProjectileBasedWeapon#attack(float)
	 */
	@Override
	public void attack(float angle) {
		if(delayCounter >= delay) {
			fireProjectiles(angle);
			delayCounter = 0;
		}
	}

	public void fireProjectiles(float angle) {
		float startX = owner.getxPosition();
		float startY = owner.getyPosition();
		if(angle < 0)
			angle += 360;
		int xInc = (int)Math.signum(Math.sin(angle));
		int yInc = (int)Math.signum(Math.cos(angle));
		float xPos1 = startX;
		float yPos1 = startY;
		float xPos2 = startX;
		float yPos2 = startY;
		fireProjectile(angle, xPos1, yPos1);
		for(int i = 0; i<swordsFired; i++) {
			xPos1 += xInc * TILE_SIZE;
			yPos1 += yInc * TILE_SIZE;
			fireProjectile(angle, xPos1, yPos1);
			xPos2 -= xInc * TILE_SIZE;
			yPos2 -= yInc * TILE_SIZE;
			fireProjectile(angle, xPos2, yPos2);
		}
	}
	
	public void fireProjectile(float angle, float xPos, float yPos) {
		projectileSprite.setRotation(-angle);
		new SwordProjectile(projectileSprite, owner.getDamage(), xPos, yPos, angle, range, friendly).spawn(level);
	}
	
	public class SwordProjectile extends Projectile {

		public SwordProjectile(Sprite sprite, int damage, float xPosition, float yPosition, float angle, float range,
				boolean isFriendly) {
			super(sprite, damage, xPosition, yPosition, angle, range, isFriendly);
		}
		
	}

	/**
	 * @return the swordsFired
	 */
	public int getSwordsFired() {
		return swordsFired;
	}

	/**
	 * @param swordsFired the swordsFired to set
	 */
	public void setSwordsFired(int swordsFired) {
		this.swordsFired = swordsFired;
	}

	@Override
	public void fireProjectile(float angle) {
		
	}

}
