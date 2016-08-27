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
		private int xPosition,yPosition;
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
			
		}

		/**Update the entity
		 * @param deltaTime the amount of time that has passed
		 */
		public void update(float deltaTime) {
			

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
