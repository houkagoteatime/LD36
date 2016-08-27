package com.github.houkagoteatime.LD36.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.github.houkagoteatime.LD36.levels.Level;
import com.github.houkagoteatime.LD36.weapons.Rock;
import com.github.houkagoteatime.LD36.weapons.Weapon;

/**
 *
 */
public class Player extends Entity{
	
	public static final int PLAYER_SPEED = 50;
	
	private int health;
	private boolean dead = false;
	private Weapon wep;
	public static final float HEIGHT = 30;
	public static final float WIDTH = 30;
	
	private TiledMapTileLayer collisionLayer;
	
	public Player(Level level, int health, int damage, Sprite sprite, TiledMapTileLayer collisionLayer) {
		super(level, health, damage, PLAYER_SPEED, sprite);
		this.collisionLayer = collisionLayer;
		wep = new Rock(this, level);
		sprite.setOrigin(HEIGHT / 2f, WIDTH / 2f);
	}

	/* (non-Javadoc)
	 * @see com.github.houkagoteatime.LD36.entities.Entity#spawn(int, int)
	 */
	@Override
	public void spawn(int xPos, int yPos) {
		super.spawn(xPos, yPos);
		setBounds(new Rectangle(xPos, yPos, sprite.getWidth() - 20, sprite.getHeight() - 20));
	}

	@Override
	public void update(float deltaTime) {
		//kill the entity
		if(health == 0)
			dead = true;
		
		//determine direction of movement
		int directionX = (int)Math.signum(this.getxMovement());
		int directionY = (int)Math.signum(this.getyMovement());
		//calculate how much the entity needs to move based on speed
		float xCalculatedMovement = calculateMovement(PLAYER_SPEED, deltaTime, directionX);
		float yCalculatedMovement = calculateMovement(PLAYER_SPEED, deltaTime, directionY);
		
		//save old position before the collision
		float oldX = this.getxPosition();
		float oldY = this.getyPosition();
		boolean collideX = false;
		boolean collideY = false;

		this.setxPosition(this.getxPosition() + updateMovement(this.getxMovement(), xCalculatedMovement));
		this.setyPosition(this.getyPosition() + updateMovement(this.getyMovement(), yCalculatedMovement));
		//set the desired movement equal to 0 if the amount moved is equal to the desired movements else decrement the desired movement by how much the entity moved
		this.setxMovement(updateMovement(this.getxMovement(), xCalculatedMovement) == this.getxMovement() ? 0 : this.getxMovement() - xCalculatedMovement);
		if(directionX < 0) {
			collideX = collidesLeft();
		} else if(directionX > 0) {
			collideX = collidesRight();
		}
		
		if(collideX) {
			setxPosition(oldX);
			setxMovement(0);
			move(0,PLAYER_SPEED/4);
		}

		
		this.setyMovement(updateMovement(this.getyMovement(), yCalculatedMovement) == this.getyMovement() ? 0 : this.getyMovement() - yCalculatedMovement);
		
		if(directionY > 0) {
			collideY = collidesTop();
		} else if(directionY < 0) {
			collideY = collidesBottom();
		}
		if(collideY) {
			setyPosition(oldY);
			move(PLAYER_SPEED/4,0);
		}
		
	}

	@Override
	public void attack() {
		wep.attack(sprite.getRotation());
	}
	
	private boolean isCellBlocked(float x, float y) {
		Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
		if(cell == null)
			return true;
		return cell.getTile() != null && cell.getTile().getProperties().containsKey("b");
	}

	public boolean collidesRight() {
		for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
			if(isCellBlocked(getxPosition() + getWidth(), getyPosition() + step))
				return true;
		return false;
	}

	public boolean collidesLeft() {
		for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
			if(isCellBlocked(getxPosition(), getyPosition() + step))
				return true;
		return false;
	}

	public boolean collidesTop() {
		for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
			if(isCellBlocked(getxPosition() + step, getyPosition() + getHeight()))
				return true;
		return false;

	}

	public boolean collidesBottom() {
		for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
			if(isCellBlocked(getxPosition() + step, getyPosition()))
				return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see com.github.houkagoteatime.LD36.entities.Entity#rotate(float)
	 */
	@Override
	public void rotate(float degrees) {
		sprite.setRotation(degrees);
	}

	public float getWidth() {
		return sprite.getWidth();
	}
	
	public float getHeight() {
		return sprite.getHeight();
	}

}
