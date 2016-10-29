package com.github.houkagoteatime.LD36.entities;

/**
 * Something that can be spawned
 */
public interface Spawnable {
	
	/**
	 * @param xPos y position
	 * @param yPos x position
	 */
	public void spawn(int xPos, int yPos);
}
