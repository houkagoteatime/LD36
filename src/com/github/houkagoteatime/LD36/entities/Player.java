package com.github.houkagoteatime.LD36.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 */
public class Player extends Entity{
	
	public static final int PLAYER_SPEED = 50;
	
	private int health;
	private boolean dead = false;
	
	private TiledMapTileLayer collisionLayer;
	
	public Player(int health, int damage, Sprite sprite, TiledMapTileLayer collisionLayer) {
		super(health, damage, PLAYER_SPEED, sprite);
		this.collisionLayer = collisionLayer;
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
		
		System.out.println("Calc Move: " + xCalculatedMovement + " " + yCalculatedMovement);
		
		//save old position before the collision
		float oldX = this.getxPosition();
		float oldY = this.getyPosition();
		float tileWidth = collisionLayer.getTileWidth();
		float tileHeight = collisionLayer.getTileHeight();
		boolean collideX = false;
		boolean collideY = false;

		//System.out.println(directionX + " " + directionY);
		this.setxPosition(this.getxPosition() + updateMovement(this.getxMovement(), xCalculatedMovement));
		this.setyPosition(this.getyPosition() + updateMovement(this.getyMovement(), yCalculatedMovement));

		System.out.println("pos" + this.getxPosition() + " " + this.getyPosition());
		
		//set the desired movement equal to 0 if the amount moved is equal to the desired movements else decrement the desired movement by how much the entity moved
		this.setxMovement(updateMovement(this.getxMovement(), xCalculatedMovement) == this.getxMovement() ? 0 : this.getxMovement() - xCalculatedMovement);
		if(directionX == -1) {
			//debug();
			System.out.println("X-");
			//top left
			collideX = collisionLayer.getCell((int)(getxPosition() / tileWidth), (int)((getyPosition() + this.getSprite().getHeight()) /tileHeight)).getTile().getProperties().containsKey("b");
		
			//middle left
			if(!collideX)
			collideX = collisionLayer.getCell((int)(getxPosition() / tileWidth), (int)((getyPosition() + this.getSprite().getHeight()/2) /tileHeight)).getTile().getProperties().containsKey("b");
			
			//bottom left
			if(!collideX)
			collideX = collisionLayer.getCell((int)(getxPosition() / tileWidth), (int)(getyPosition()/tileHeight)).getTile().getProperties().containsKey("b");
			
		} else if(directionX == 1) {

			System.out.println("X+");
			//top right
			collideX = collisionLayer.getCell((int)((getxPosition())/tileWidth),(int)((getyPosition() + this.getSprite().getHeight())/tileHeight)).getTile().getProperties().containsKey("b");
			
			//middle right
			if(!collideX)
			collideX = collisionLayer.getCell((int)((getxPosition())/tileWidth),(int)((getyPosition() + this.getSprite().getHeight()/2)/tileHeight)).getTile().getProperties().containsKey("b");
			
			//bottom right
			if(!collideX)
			collideX = collisionLayer.getCell((int)((getxPosition())/tileWidth),(int)((getyPosition())/tileHeight)).getTile().getProperties().containsKey("b");
		}
		
		if(collideX) {
			System.out.println(" X COLLIDE");
			setxPosition(oldX);
			move(0,PLAYER_SPEED/4);
		}

		//getyPosition() += updateMovement(this.getyMovement(), yCalculatedMovement);
		//this.setxMovement(updateMovement(this.getxMovement(), xCalculatedMovement) == this.getxMovement() ? 0 : this.getxMovement() - xCalculatedMovement);
		
		this.setyMovement(updateMovement(this.getyMovement(), yCalculatedMovement) == this.getyMovement() ? 0 : this.getyMovement() - yCalculatedMovement);
		
		if(directionY == -1) {

			System.out.println("Y-");
			//bottomLeft
			collideY = collisionLayer.getCell((int)((getxPosition())/tileWidth),(int)((getyPosition())/tileHeight)).getTile().getProperties().containsKey("b");
			
			//bottomMiddle
			if(!collideY)
			collideY = collisionLayer.getCell((int)((getxPosition() + this.getSprite().getWidth()/2)/tileWidth),(int)((getyPosition())/tileHeight)).getTile().getProperties().containsKey("b");
			
			//bottomRight
			if(!collideY)
				collideY = collisionLayer.getCell((int)((getxPosition() + this.getSprite().getWidth())/tileWidth),(int)((getyPosition())/tileHeight)).getTile().getProperties().containsKey("b");
				
		} else if(directionY == 1) {

			System.out.println("Y+");
			//topLeft
			collideY = collisionLayer.getCell((int)((getxPosition() + this.getSprite().getWidth())/tileWidth),(int)((getyPosition() + this.getSprite().getHeight())/tileHeight)).getTile().getProperties().containsKey("b");
			
			
			//topMiddle
			if(!collideY)
				collideY = collisionLayer.getCell((int)((getxPosition() + this.getSprite().getWidth()/2)/tileWidth),(int)((getyPosition() + this.getSprite().getHeight()/2)/tileHeight)).getTile().getProperties().containsKey("b");
			
			//topRight
			if(!collideY)
				collideY = collisionLayer.getCell((int)((getxPosition() + this.getSprite().getWidth())/tileWidth),(int)((getyPosition() + this.getSprite().getHeight())/tileHeight)).getTile().getProperties().containsKey("b");
			
		}
		if(collideY) {
			System.out.println("Y COLLIDE");
			setyPosition(oldY);
			move(PLAYER_SPEED/4,0);
		}
		//set the desired movement equal to 0 if the amount moved is equal to the desired movements else decrement the desired movement by how much the entity moved
		//this.getxMovement() = updateMovement(this.getxMovement(), xCalculatedMovement) == this.getxMovement() ? 0 : this.getxMovement() - xCalculatedMovement;
		//this.getyMovement() = updateMovement(this.getyMovement(), yCalculatedMovement) == this.getyMovement() ? 0 : this.getyMovement() - yCalculatedMovement;
		
	}

	@Override
	public void attack() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.github.houkagoteatime.LD36.entities.Entity#rotate(float)
	 */
	@Override
	public void rotate(float degrees) {
		sprite.setRotation(degrees);
	}

	

}
