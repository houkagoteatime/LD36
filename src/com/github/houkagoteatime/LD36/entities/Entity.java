package com.github.houkagoteatime.LD36.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
	/**
	 * game objects that interact with each other should inherit this
	 */
	public abstract class Entity {
		protected Sprite sprite;
		private int health;
		private int damage;
		private int speed;
		private float xPosition,yPosition, xMovement, yMovement;
		
		private boolean dead = false;
		
		/**Constructor for entity
		 * @param health how much health it has
		 * @param damage how much damage it does
		 * @param sprite the sprite that is being used
		 */
		public Entity(int health, int damage, Sprite sprite) {
			this.sprite = sprite;
			this.health = health;
			this.damage = damage;
			sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		}
		
		/**
		 * @param moveX how much the sprite should move relative to its x position
		 * @param moveY how much the sprite should move relative to its y position
		 */
		public void move(float moveX, float moveY) {
			xMovement = moveX;
			yMovement = moveY;
		}

		/**Update the entity
		 * @param deltaTime the amount of time that has passed
		 */
		public void update(float deltaTime) {
			//kill the entity
			if(health == 0)
				dead = true;
			
			//determine direction of movement
			int directionX = (int)Math.signum(xMovement);
			int directionY = (int)Math.signum(yMovement);
			//calculate how much the entity needs to move based on speed
			float xCalculatedMovement = calculateMovement(speed, deltaTime, directionX);
			float yCalculatedMovement = calculateMovement(speed, deltaTime, directionY);
			
			//move the positions
			xPosition += updateMovement(xMovement, xCalculatedMovement);
			yPosition += updateMovement(yMovement, yCalculatedMovement);
			
			//set the desired movement equal to 0 if the amount moved is equal to the desired movements else decrement the desired movement by how much the entity moved
			xMovement = updateMovement(xMovement, xCalculatedMovement) == xMovement ? 0 : xMovement - xCalculatedMovement;
			yMovement = updateMovement(yMovement, yCalculatedMovement) == yMovement ? 0 : yMovement - yCalculatedMovement;

		}
		
		/**
		 * @param actualMovement how much the entity actually needs to move
		 * @param calculatedMovement movement based on entities speed
		 * @return actualMovement if the amount to move is less than calculatedMovement to avoid overshooting
		 */
		public float updateMovement(float actualMovement, float calculatedMovement) {
			if(Math.abs(actualMovement) < Math.abs(calculatedMovement)) {
				return actualMovement;
			} else {
				return calculatedMovement;
			}
		}
		
		/**
		 * @param speed
		 * @param deltaTime
		 * @param direction
		 * @return how much the entity should move based on speed, time, and direction
		 */
		public float calculateMovement(float speed, float deltaTime, int direction) {
			return speed * direction * deltaTime;
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

		
		/**
		 * @return the health
		 */
		public int getHealth() {
			return health;
		}

		/**
		 * @param health the health to set
		 */
		public void setHealth(int health) {
			this.health = health;
		}

		/**
		 * @return the damage
		 */
		public int getDamage() {
			return damage;
		}

		/**
		 * @param damage the damage to set
		 */
		public void setDamage(int damage) {
			this.damage = damage;
		}

		/**
		 * @return is dead
		 */
		public boolean isDead() {
			return dead;
		}
		
		public Vector2 getPosition() {
			return new Vector2(xPosition, yPosition);
		}
}
