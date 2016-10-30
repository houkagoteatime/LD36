package com.github.houkagoteatime.LD36.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.github.houkagoteatime.LD36.levels.Level;
import com.github.houkagoteatime.LD36.weapons.Melee;
import com.github.houkagoteatime.LD36.weapons.Rock;
import com.github.houkagoteatime.LD36.weapons.Weapon;
import com.badlogic.gdx.maps.MapObjects;

/**
 * User controlled entity
 */
public class Player extends Entity{
	
	public boolean isGod;
	public static final int PLAYER_SPEED = 100;
	public static final int MAX_HEALTH = 150;
	public static final int I_FRAME = 45;
	public int iFrameCounter;
	public float spawnX;
	public float spawnY;
	private Weapon wep;
	private Weapon meleeWep;
	private OrthographicCamera cam;
	public static final float HEIGHT = 10;
	public static final float WIDTH = 10;
	private Sprite sword;
	public MapObjects mapObj;
	
	/**
	 * @return the weapon
	 */
	public Weapon getWeapon() {
		return wep;
	}
	
	/**
	 * @param level current level
	 * @param damage entity damage
	 * @param sprite entity sprite
	 */
	public Player(Level level, int damage, Sprite sprite) {
		super(level, MAX_HEALTH, damage, PLAYER_SPEED, sprite);
		wep = new Rock(this, level);
		sword = new Sprite(new Texture(Gdx.files.internal("assets/pictures/sword1.png")));
		meleeWep = new Melee(sword, this, 15, 10);
		sprite.setOrigin(HEIGHT / 2f, WIDTH / 2f);
	}

	/**
	 * @param dt delta time
	 */
	public void fly(float dt) {
		super.update(dt);
	}
	
	/* (non-Javadoc)
	 * @see com.github.houkagoteatime.LD36.entities.Entity#spawn(int, int)
	 */
	@Override
	public void spawn(int xPos, int yPos) {
		super.spawn(xPos, yPos);
		updateBounds();
	}

	/* (non-Javadoc)
	 * @see com.github.houkagoteatime.LD36.entities.Entity#update(float)
	 */
	@Override
	public void update(float deltaTime) {
		//System.out.println(this.getxPosition() + "," + this.getyPosition());
		//kill the entity
		if(getHealth() <= 0) {			
			this.setDead(true);
		}
		updateBounds();
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
			move(0,PLAYER_SPEED/4);
		}
		
		if(collideY) {
			setyPosition(oldY);
			move(PLAYER_SPEED/4,0);
		}
	}
	
	/**
	 * @param worldCoord the world coordinate
	 * @return 3D vector
	 */
	public Vector3 unproject(Vector3 worldCoord) {
		return cam.unproject(worldCoord);
	}

	/* (non-Javadoc)
	 * @see com.github.houkagoteatime.LD36.entities.Entity#attack()
	 */
	@Override
	public void attack() {

		//System.out.println(this.getxPosition() + "," + this.getyPosition());
		wep.attack(sprite.getRotation());
	}
	
	/**
	 * Attach without range
	 */
	public void meleeAttack() {
		meleeWep.attack(sprite.getRotation());
		getLevel().handlePlayerMelee((Melee)meleeWep);
	}
	
	/**
	 * @param p the bounding polygon
	 * @return true if there is collision false otherwise
	 */
	public boolean collidesObj(Polygon p) {
		Rectangle n = new Rectangle(this.getxPosition(), this.getyPosition(), this.getSprite().getWidth(), this.getSprite().getHeight());
		if(p.getBoundingRectangle().overlaps((n)))
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

	/**
	 * @return the width
	 */
	public float getWidth() {
		return sprite.getWidth();
	}
	
	/**
	 * @return the height
	 */
	public float getHeight() {
		return sprite.getHeight();
	}

	/**
	 * @return the wep
	 */
	public Weapon getWep() {
		return wep;
	}

	/**
	 * @param wep the wep to set
	 */
	public void setWep(Weapon wep) {
		this.wep = wep;
	}

	/**
	 * @return the cam
	 */
	public OrthographicCamera getCam() {
		return cam;
	}

	/**
	 * @param cam the cam to set
	 */
	public void setCam(OrthographicCamera cam) {
		this.cam = cam;
	}

}
