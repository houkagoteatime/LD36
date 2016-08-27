package com.github.houkagoteatime.LD36.weapons;

import com.badlogic.gdx.math.Rectangle;
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

}
