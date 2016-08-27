package com.github.houkagoteatime.LD36.weapons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.github.houkagoteatime.LD36.levels.Level;

public abstract class Projectile{
	private float xPosition, yPosition;
	private float startXPosition, startyPosition;
	private float angle;
	private int damage;
	public static final int SPEED = 50;
	private Rectangle bounds;
	private Sprite sprite;
	private Level level;
	private float range;
	/**
	 * @param sprite the sprite
	 * @param damage the amount of damage
	 * @param xPosition the x position of the projectile
	 * @param yPosition the y position of the projectile
	 * @param angle the angle of the projectile
	 */
	public Projectile(Sprite sprite, int damage, float xPosition, float yPosition, float angle, float range) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.startXPosition = xPosition;
		this.startyPosition = yPosition; 
		this.angle = angle;
		this.damage = damage;
		this.sprite = sprite;
		bounds = new Rectangle(xPosition, yPosition, sprite.getWidth(), sprite.getHeight());
		this.range = range;
	}

	public void update(float dt) {
		xPosition += Math.sin(Math.toRadians(angle)) * SPEED * dt;
		yPosition += Math.cos(Math.toRadians(angle)) * SPEED * dt;
	}

	public boolean isOutOfRange() {
		if(pythagoreanize(xPosition - startXPosition, yPosition - startyPosition) > range) {
			return true;
		}
		return false;
	}

	public void spawn(Level level) {
		this.level = level;
		level.addProjectile(this);
	}

	public void remove(){
		level.removeProjectile(this);
	}

	/**
	 * @param side1 the first side of the triangle(not the hypotenuse)
	 * @param side2 the second side of the triangle(not the hypotenuse)
	 * @return the length of the hypotenuse
	 * Tribute to Gal Egozi who invented this word
	 */
	public float pythagoreanize(float side1, float side2) {
		return (float)Math.sqrt((double)(Math.pow(side1, 2)) + Math.pow(side2, 2));
	}

	/**
	 * @return the xPosition
	 */
	public float getxPosition() {
		return xPosition;
	}

	/**
	 * @param xPosition the xPosition to set
	 */
	public void setxPosition(float xPosition) {
		this.xPosition = xPosition;
	}

	/**
	 * @return the yPosition
	 */
	public float getyPosition() {
		return yPosition;
	}

	/**
	 * @param yPosition the yPosition to set
	 */
	public void setyPosition(float yPosition) {
		this.yPosition = yPosition;
	}

	/**
	 * @return the sprite
	 */
	public Sprite getSprite() {
		return sprite;
	}

	/**
	 * @param sprite the sprite to set
	 */
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

}
