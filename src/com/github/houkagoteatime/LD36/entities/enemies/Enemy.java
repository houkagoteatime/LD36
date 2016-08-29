package com.github.houkagoteatime.LD36.entities.enemies;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.houkagoteatime.LD36.entities.Entity;
import com.github.houkagoteatime.LD36.entities.Player;
import com.github.houkagoteatime.LD36.levels.Level;
import com.github.houkagoteatime.LD36.utils.Node;
import com.github.houkagoteatime.LD36.utils.PathFinder;


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
	private PathFinder pathFinder;
	private ArrayList<Node> pathToPlayer;
	private boolean collidable = true;
	private int counter;
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
		/*if(getyMovement() == 0 || getxMovement() == 0) {
			if(pathToPlayer != null) {
				followPath(counter);
				counter++;
			} else {
				//getPath();
			}

		}*/
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
		

		//set the desired movement equal to 0 if the amount moved is equal to the desired movements else decrement the desired movement by how much the entity moved
		this.setxMovement(updateMovement(this.getxMovement(), xCalculatedMovement) == this.getxMovement() ? 0 : this.getxMovement() - xCalculatedMovement);
		

		for (PolygonMapObject rectangleObject : this.getLevel().getMapObjects().getByType(PolygonMapObject.class)) {
			if(collidesObj(rectangleObject.getPolygon())) {
				collideX = true;
			}
		}
		
		if(collideX && collidable) {
			setxPosition(oldX);
			//setxMovement(0);
			move(0,this.getSpeed()/4);
		}
		
		
		this.setyPosition(this.getyPosition() + updateMovement(this.getyMovement(), yCalculatedMovement));
		this.setyMovement(updateMovement(this.getyMovement(), yCalculatedMovement) == this.getyMovement() ? 0 : this.getyMovement() - yCalculatedMovement);
		for (PolygonMapObject rectangleObject : this.getLevel().getMapObjects().getByType(PolygonMapObject.class)) {
			if(collidesObj(rectangleObject.getPolygon())) {
				collideY = true;
			}
		}


		if(collideY && collidable) {
			setyPosition(oldY);
			//setyMovement(0);
			move(this.getSpeed()/4,0);
		}

		//System.out.println(getyMowvement() + "" + getxMovement());
		
	}

	public void followPath(int step) {
		if(step < pathToPlayer.size()) {
			//setxPosition(pathToPlayer.get(step).x * 16);
			//setyPosition( pathToPlayer.get(step).y * 16);
			move(pathToPlayer.get(step).x *16- getxPosition(), pathToPlayer.get(step).y*16-getyPosition());

		} else {
			//getPath();
			//step = 0;
		}
	}

	public void getPath() {
		pathToPlayer = pathFinder.findPath(new Vector2(getxPosition()/16, getyPosition()/16), new Vector2(player.getxPosition()/16, player.getyPosition()/16));
	}
	public boolean collidesObj(Polygon p) {
		Rectangle n = new Rectangle(this.getxPosition(), this.getyPosition(), this.getSprite().getWidth(), this.getSprite().getHeight());
		if(p.getBoundingRectangle().overlaps((n)))
			return true;
		return false;
	}
	public abstract void init();

	/**
	 * @return the collidable
	 */
	public boolean isCollidable() {
		return collidable;
	}



	/**
	 * @param collidable the collidable to set
	 */
	protected void setCollidable(boolean collidable) {
		this.collidable = collidable;
	}



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



	/**
	 * @return the pathFinder
	 */
	public PathFinder getPathFinder() {
		return pathFinder;
	}



	/**
	 * @param pathFinder the pathFinder to set
	 */
	public void setPathFinder(PathFinder pathFinder) {
		this.pathFinder = pathFinder;
	}

}



