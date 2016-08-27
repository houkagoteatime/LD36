package com.github.houkagoteatime.LD36.weapons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.github.houkagoteatime.LD36.levels.Level;

public abstract class Projectile {
	private float xPosition, yPosition;
	private float angle;
	private int damage;
	public static final int SPEED = 20;
	private Rectangle bounds;
	private Sprite sprite;
	private Level level;
	
	/**
	 * @param sprite the sprite
	 * @param damage the amount of damage
	 * @param xPosition the x position of the projectile
	 * @param yPosition the y position of the projectile
	 * @param angle the angle of the projectile
	 */
	public Projectile(Sprite sprite, int damage, float xPosition, float yPosition, float angle) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.angle = angle;
		this.damage = damage;
		this.sprite = sprite;
		bounds = new Rectangle(xPosition, yPosition, sprite.getWidth(), sprite.getHeight());
		
	}
	 
	public void update(float dt) {
		xPosition += Math.signum(Math.cos(angle)) * SPEED * dt;
		yPosition += Math.signum(Math.sin(yPosition)) * SPEED * dt;
	}
	
	public void spawn(Level level) {
		this.level = level;
	}
}
