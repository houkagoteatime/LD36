package com.github.houkagoteatime.LD36.weapons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.houkagoteatime.LD36.levels.Level;

public abstract class Projectile{
	private boolean isFriendly;
	private float xPosition, yPosition;
	private float startXPosition, startyPosition;
	private float angle;
	private int damage;
	public static final int SPEED = 90;
	private Rectangle bounds;
	private Sprite sprite;
	private Level level;
	private float range;
	private boolean collide;
	/**
	 * @param sprite the sprite
	 * @param damage the amount of damage
	 * @param xPosition the x position of the projectile
	 * @param yPosition the y position of the projectile
	 * @param angle the angle of the projectile
	 */
	public Projectile(Sprite sprite, int damage, float xPosition, float yPosition, float angle, float range, boolean isFriendly) {

		this.isFriendly = isFriendly;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.startXPosition = xPosition;
		this.startyPosition = yPosition; 
		//System.out.print(startXPosition + "" + startyPosition);
		this.angle = angle;
		this.damage = damage;
		this.sprite = sprite;
		
		bounds = new Rectangle(xPosition, yPosition, sprite.getWidth() /2, sprite.getHeight() /2);
		this.range = range;
	}

	public boolean isFriendly() {
		return isFriendly;
	}

	public Vector2 getPosition() {
		return new Vector2(xPosition, yPosition);
	}
	
	public void updateBounds() {
		bounds.setPosition(getPosition());
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	public boolean isOutOfRange() {
		if(pythagoreanize(xPosition - startXPosition, yPosition - startyPosition) > range) {
			//System.out.println(pythagoreanize(xPosition - startXPosition, yPosition - startyPosition) > range);
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


	
	public boolean getCollide() {
		return collide;
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
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void update(float dt) {
		
		updateBounds();
		
		xPosition += Math.sin(Math.toRadians(angle)) * SPEED * dt;
		yPosition += Math.cos(Math.toRadians(angle)) * SPEED * dt;
		
		for (PolygonMapObject rectangleObject : level.getMapObjects().getByType(PolygonMapObject.class)) {
			if(collidesObj(rectangleObject.getPolygon())) {
				if(rectangleObject.getProperties().get("stopProjectile").equals(true)) {
					collide = true;
				} else {
					collide = false;
				}
			}
		}
	}
		
	private boolean collidesObj(Polygon p) {
		Rectangle n = new Rectangle(this.getxPosition(), this.getyPosition(), this.getSprite().getWidth(), this.getSprite().getHeight());
		if(p.getBoundingRectangle().overlaps((n)))
			return true;
		return false;
	}
}
