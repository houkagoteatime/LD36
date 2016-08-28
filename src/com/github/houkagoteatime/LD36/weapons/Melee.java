package com.github.houkagoteatime.LD36.weapons;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.houkagoteatime.LD36.entities.Entity;

public class Melee implements Weapon{
	private Entity owner;
	private Rectangle extension;	
	public Melee(Entity owner) {
		this.owner = owner;
	}

	@Override
	public void attack(float angle) {
		
		float xPos = owner.getxPosition();
		float yPos = owner.getyPosition();		
		float xSide = owner.getBounds().x;
		float ySide = owner.getBounds().y;
		extension = new Rectangle(xPos, yPos, xSide, ySide);
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

}
