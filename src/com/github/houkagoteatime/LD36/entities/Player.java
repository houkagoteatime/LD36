package com.github.houkagoteatime.LD36.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 */
public class Player extends Entity{
	
	public static final int PLAYER_SPEED = 10;
	
	private int health;
	private boolean dead = false;
	
	private float xPosition,yPosition, xMovement, yMovement;
	
	private TiledMapTileLayer collisionLayer;
	
	public Player(int health, int damage, Sprite sprite, TiledMapTileLayer collisionLayer) {
		super(health, damage, PLAYER_SPEED, sprite);
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
		int directionX = (int)Math.signum(xMovement);
		int directionY = (int)Math.signum(yMovement);
		//calculate how much the entity needs to move based on speed
		float xCalculatedMovement = calculateMovement(PLAYER_SPEED, deltaTime, directionX);
		float yCalculatedMovement = calculateMovement(PLAYER_SPEED, deltaTime, directionY);
		
		//save old position before the collision
		float oldX = xPosition;
		float oldY = yPosition;
		float tileWidth = collisionLayer.getTileWidth();
		float tileHeight = collisionLayer.getTileHeight();
		boolean collideX = false;
		boolean collideY = false;
		
		/*//TextureMapObject mapObject = (TextureMapObject) object;
        this.bounds = new Rectangle(mapObject.getX(), mapObject.getY(),
                mapObject.getTextureRegion().getRegionWidth(),
                mapObject.getTextureRegion().getRegionHeight());*/
		//check for collision in surrounding tiles
		

		xPosition += updateMovement(xMovement, xCalculatedMovement);
		
		
		if(directionX == -1) {
			
			//top left
			collideX = collisionLayer.getCell((int)(xPosition / tileWidth), (int)((yPosition + this.getSprite().getHeight()) /tileHeight)).getTile().getProperties().containsKey("blocked");
		
			//middle left
			if(!collideX)
			collideX = collisionLayer.getCell((int)(xPosition / tileWidth), (int)((yPosition + this.getSprite().getHeight()/2) /tileHeight)).getTile().getProperties().containsKey("blocked");
			
			//bottom left
			if(!collideX)
			collideX = collisionLayer.getCell((int)(xPosition / tileWidth), (int)(yPosition/tileHeight)).getTile().getProperties().containsKey("blocked");
			
		} else if(directionX == 1) {
			
			//top right
			collideX = collisionLayer.getCell((int)((xPosition + this.getSprite().getWidth())/tileWidth),(int)((yPosition + this.getSprite().getHeight())/tileHeight)).getTile().getProperties().containsKey("blocked");
			
			//middle right
			if(!collideX)
			collideX = collisionLayer.getCell((int)((xPosition + this.getSprite().getWidth())/tileWidth),(int)((yPosition + this.getSprite().getHeight())/tileHeight)).getTile().getProperties().containsKey("blocked");
			
			//bottom right
			if(!collideX)
			collideX = collisionLayer.getCell((int)((xPosition + this.getSprite().getWidth())/tileWidth),(int)((yPosition)/tileHeight)).getTile().getProperties().containsKey("blocked");
		}
		
		if(collideX) {
			xPosition = oldX;
			move(0,PLAYER_SPEED);
		}

		yPosition += updateMovement(yMovement, yCalculatedMovement);
		
		if(directionY == -1) {
			
			//bottomLeft
			collideY = collisionLayer.getCell((int)((xPosition)/tileWidth),(int)((yPosition)/tileHeight)).getTile().getProperties().containsKey("blocked");
			
			//bottomMiddle
			if(!collideY)
			collideY = collisionLayer.getCell((int)((xPosition + this.getSprite().getWidth()/2)/tileWidth),(int)((yPosition)/tileHeight)).getTile().getProperties().containsKey("blocked");
			
			//bottomRight
			if(!collideY)
				collideY = collisionLayer.getCell((int)((xPosition + this.getSprite().getWidth())/tileWidth),(int)((yPosition)/tileHeight)).getTile().getProperties().containsKey("blocked");
				
		} else if(directionY == 1) {
			
			//topLeft
			collideY = collisionLayer.getCell((int)((xPosition + this.getSprite().getWidth())/tileWidth),(int)((yPosition + this.getSprite().getHeight())/tileHeight)).getTile().getProperties().containsKey("blocked");
			
			
			//topMiddle
			if(!collideY)
				collideY = collisionLayer.getCell((int)((xPosition + this.getSprite().getWidth()/2)/tileWidth),(int)((yPosition + this.getSprite().getHeight()/2)/tileHeight)).getTile().getProperties().containsKey("blocked");
			
			//topRight
			if(!collideY)
				collideY = collisionLayer.getCell((int)((xPosition + this.getSprite().getWidth())/tileWidth),(int)((yPosition + this.getSprite().getHeight())/tileHeight)).getTile().getProperties().containsKey("blocked");
			
		}
		if(collideY) {
			yPosition = oldY;
			move(PLAYER_SPEED,0);
		}
		//set the desired movement equal to 0 if the amount moved is equal to the desired movements else decrement the desired movement by how much the entity moved
		xMovement = updateMovement(xMovement, xCalculatedMovement) == xMovement ? 0 : xMovement - xCalculatedMovement;
		yMovement = updateMovement(yMovement, yCalculatedMovement) == yMovement ? 0 : yMovement - yCalculatedMovement;
		
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
