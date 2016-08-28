package com.github.houkagoteatime.LD36.weapons;

public interface Weapon {

	/**
	 * @param angle the angle to attack
	 */
	public void attack(float angle);
	
	public void incrementDelayCounter();
	
	public int getDelayCounter();
	
	public void setDelayCounter(int delay);
}
