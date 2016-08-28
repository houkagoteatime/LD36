package com.github.houkagoteatime.LD36.weapons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.houkagoteatime.LD36.levels.Level;

public abstract class Projectile{
	private boolean isFriendly;
	private float xPosition, yPosition;
	private float startXPosition, startyPosition;
	private float angle;
	private int damage;
	public static final int SPEED = 50;
	private Rectangle bounds;
	private Sprite sprite;
	private Level level;
	private float range;
	private TiledMapTileLayer collisionLayer;
	private boolean collide;
	/**
	 * @param sprite the sprite
	 * @param damage the amount of damage
	 * @param xPosition the x position of the projectile
	 * @param yPosition the y position of the projectile
	 * @param angle the angle of the projectile
	 */
	public Projectile(Sprite sprite, int damage, float xPosition, float yPosition, float angle, float range, boolean isFriendly, TiledMapTileLayer collisionLayer) {

		this.collisionLayer = collisionLayer;
		this.isFriendly = isFriendly;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.startXPosition = xPosition;
		this.startyPosition = yPosition; 
		//System.out.print(startXPosition + "" + startyPosition);
		this.angle = angle;
		this.damage = damage;
		this.sprite = sprite;
		
		bounds = new Rectangle(xPosition, yPosition, sprite.getWidth(), sprite.getHeight());
		this.range = range;
	}

	public boolean isFriendly() {
		return isFriendly;
	}
	/*public void update(float dt) {

		updateBounds();
		xPosition += Math.sin(Math.toRadians(angle)) * SPEED * dt;
		yPosition += Math.cos(Math.toRadians(angle)) * SPEED * dt;
	}*/

	public Vector2 getPosition() {
		return new Vector2(xPosition, yPosition);
	}
	
	public void updateBounds() {
		bounds = new Rectangle(getPosition().x, getPosition().y, getPosition().x + sprite.getWidth(), getPosition().y +sprite.getHeight());
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

	private boolean isCellBlocked(float x, float y) {
		Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
		if(cell == null)
			return true;
		return cell.getTile() != null && cell.getTile().getProperties().containsKey("b");
	}

	public boolean collidesRight() {
		for(float step = 0; step < sprite.getHeight(); step += collisionLayer.getTileHeight() / 2)
			if(isCellBlocked(getxPosition() + sprite.getWidth(), getyPosition() + step))
				return true;
		return false;
	}

	public boolean collidesLeft() {
		for(float step = 0; step < sprite.getHeight(); step += collisionLayer.getTileHeight() / 2)
			if(isCellBlocked(getxPosition(), getyPosition() + step))
				return true;
		return false;
	}

	public boolean collidesTop() {
		for(float step = 0; step < sprite.getWidth(); step += collisionLayer.getTileWidth() / 2)
			if(isCellBlocked(getxPosition() + step, getyPosition() + sprite.getHeight()))
				return true;
		return false;

	}

	public boolean collidesBottom() {
		for(float step = 0; step < sprite.getWidth(); step += collisionLayer.getTileWidth() / 2)
			if(isCellBlocked(getxPosition() + step, getyPosition()))
				return true;
		return false;
	}
	
	public void update(float dt) {
		
		updateBounds();
		xPosition += Math.sin(Math.toRadians(angle)) * SPEED * dt;
		yPosition += Math.cos(Math.toRadians(angle)) * SPEED * dt;
		boolean collideX = false;
		boolean collideY = false;
		
		if(Math.sin(Math.toRadians(angle)) * SPEED * dt < 0) {
			collideX = collidesLeft();
		} else if(Math.sin(Math.toRadians(angle)) * SPEED * dt > 0) {
			collideX = collidesRight();
		}
		
		if(collideX) {
			collide = true;
		}
		
		if(Math.cos(Math.toRadians(angle)) * SPEED * dt> 0) {
			collideY = collidesTop();
		} else if(Math.cos(Math.toRadians(angle)) * SPEED * dt < 0) {
			collideY = collidesBottom();
		}
		if(collideY) {
			collide = true;
		}
		
	}
}
