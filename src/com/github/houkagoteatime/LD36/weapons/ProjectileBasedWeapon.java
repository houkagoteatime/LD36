package com.github.houkagoteatime.LD36.weapons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.github.houkagoteatime.LD36.entities.Entity;
import com.github.houkagoteatime.LD36.levels.Level;

public abstract class ProjectileBasedWeapon implements Weapon{
	protected Sprite projectileSprite;
	protected Entity owner;
	protected Level level;
	public int delayCounter;
	protected float range;
	protected boolean friendly = false;
	protected int delay;
	
	public ProjectileBasedWeapon(Entity owner, Level level) {
		this.level = level;
		this.owner = owner;
	}

	@Override
	public void attack(float angle) {
		// TODO Auto-generated method stub
		if(delayCounter >= delay) {
			fireProjectile(angle);
			delayCounter = 0;
		}
	}
	
	public abstract void fireProjectile(float angle);

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

	/**
	 * @return the friendly
	 */
	public boolean isFriendly() {
		return friendly;
	}

	/**
	 * @param friendly the friendly to set
	 */
	public void setFriendly(boolean friendly) {
		this.friendly = friendly;
	}

	/**
	 * @return the range
	 */
	public float getRange() {
		return range;
	}

	/**
	 * @param range the range to set
	 */
	public void setRange(float range) {
		this.range = range;
	}

}
