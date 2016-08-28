package com.github.houkagoteatime.LD36.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.github.houkagoteatime.LD36.entities.Entity;
import com.github.houkagoteatime.LD36.entities.Player;
import com.github.houkagoteatime.LD36.levels.Level;


/**
 * @param health health of the enemy
 * @param defaultDamage how much damage it does
 * @param sprite the enemy sprite
 * @param speed the speed
 * @param player the player playing the game
 */
public abstract class Enemy extends Entity{
	protected Player player;
	public int iFrameCounter;
	public static final float DEFAULT_AGGRO_RANGE = 90f;
	public Enemy(Level level, int health, int damage, int speed, Sprite sprite, Player player) {
		super(level, health, damage, speed, sprite);
		this.player = player;
	}



	@Override
	public void update(float dt) {
		updateBounds();
		//System.out.println(this.getxPosition() + "," + this.getyPosition());
		//kill the entity
		if(this.getHealth() <= 0)
			this.setDead(true);

		//determine direction of movement
		int directionX = (int)Math.signum(this.getxMovement());
		int directionY = (int)Math.signum(this.getyMovement());
		//calculate how much the entity needs to move based on speed
		float xCalculatedMovement = calculateMovement(this.getSpeed(), dt, directionX);
		float yCalculatedMovement = calculateMovement(this.getSpeed(), dt, directionY);

		//save old position before the collision
		float oldX = this.getxPosition();
		float oldY = this.getyPosition();
		boolean collideX = false;
		boolean collideY = false;

		this.setxPosition(this.getxPosition() + updateMovement(this.getxMovement(), xCalculatedMovement));
		this.setyPosition(this.getyPosition() + updateMovement(this.getyMovement(), yCalculatedMovement));

		//set the desired movement equal to 0 if the amount moved is equal to the desired movements else decrement the desired movement by how much the entity moved
		this.setxMovement(updateMovement(this.getxMovement(), xCalculatedMovement) == this.getxMovement() ? 0 : this.getxMovement() - xCalculatedMovement);
		this.setyMovement(updateMovement(this.getyMovement(), yCalculatedMovement) == this.getyMovement() ? 0 : this.getyMovement() - yCalculatedMovement);

		for (PolygonMapObject rectangleObject : this.getLevel().getMapObjects().getByType(PolygonMapObject.class)) {
			if(collidesObj(rectangleObject.getPolygon())) {
				collideX = true;
				collideY = true;
			}
		}

		if(collideX) {
			setxPosition(oldX);
			setxMovement(0);
			move(0,this.getSpeed()/4);
		}

		if(collideY) {
			setyPosition(oldY);
			move(this.getSpeed()/4,0);
		}
	}

	public boolean collidesObj(Polygon p) {
		Rectangle n = new Rectangle(this.getxPosition(), this.getyPosition(), this.getSprite().getWidth(), this.getSprite().getHeight());
		if(p.getBoundingRectangle().overlaps((n)))
			return true;
		return false;
	}
	public abstract void init();

	/**
	 * @return true if the player is within the aggro range
	 */
	public boolean isPlayerNearby(float aggro) {
		float distance = pythagoreanize(player.getPosition().x - getPosition().x, player.getPosition().y - getPosition().y);
		return Math.abs(distance) <= aggro;
	}

	public float getAngleToPlayer() {
		float angle = (float)Math.toDegrees(Math.atan2(player.getxPosition() - getxPosition(), player.getyPosition() - getyPosition()));
		return angle;
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
	
}



