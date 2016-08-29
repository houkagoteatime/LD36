package com.github.houkagoteatime.LD36.weapons;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.houkagoteatime.LD36.entities.Entity;

public class Melee implements Weapon{
	private Entity owner;
	private Rectangle extension;
	private float range;
	private Sprite sprite;
	public static final int DAMAGE = 100;
	public Melee(Sprite sprite, Entity owner, float range) {
		this.owner = owner;
		this.range = range;
		extension = new Rectangle(owner.getxPosition(), owner.getyPosition(), sprite.getWidth(), sprite.getHeight());
		this.sprite = sprite;
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
	}

	@Override
	public void attack(float angle) {

		float xPos = owner.getxPosition();
		float yPos = owner.getyPosition();
		sprite.setRotation(-angle);
		extension.setPosition(rectangleOrigin(angle, xPos, yPos));
	}

	public Vector2 rectangleOrigin(float angle, float xPos, float yPos) {
		if(angle < 0)
			angle += 360;
		//these aren't perfect
		if(angle > 330 && angle <= 360 || angle <= 30) {
			yPos += range;
		} else if(angle > 30 && angle <= 85) {
			xPos += range;
			yPos += range;
		} else if(angle > 85 && angle <= 100) {
			xPos += range;
		} else if(angle > 100 && angle <= 145) {
			xPos += range;
			yPos -= range;
		} else if(angle > 145 && angle <= 215) {
			yPos -= range;
		} else if(angle > 215 && angle <= 245) {
			xPos -= range;
			yPos -= range;
		} else if(angle > 245 && range <= 285) {
			xPos -= range;
		} else if(angle > 285 && angle <=330) {
			xPos -= range;
			yPos += range;
		}
		return new Vector2(xPos,yPos);
	}

	@Override
	public void incrementDelayCounter() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getDelayCounter() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setDelayCounter(int delay) {
		// TODO Auto-generated method stub

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
	 * @return the extension
	 */
	public Rectangle getExtension() {
		return extension;
	}

	/**
	 * @param extension the extension to set
	 */
	public void setExtension(Rectangle extension) {
		this.extension = extension;
	}

	/**
	 * @return the range
	 */
	public float getRange() {
		return range;
	}

	/**
	 * @param range the range to set
	 */
	public void setRange(float range) {
		this.range = range;
	}
	
	
}
