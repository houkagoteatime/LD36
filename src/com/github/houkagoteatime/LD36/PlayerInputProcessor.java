package com.github.houkagoteatime.LD36;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.github.houkagoteatime.LD36.entities.Entity;
import com.github.houkagoteatime.LD36.entities.Player;

/**
 *Class for updating the player movement based on input
 */
public class PlayerInputProcessor {

	private Player player;
	
	/**
	 * @param player the player to set
	 */
	public PlayerInputProcessor(Player player) {
		this.player = player;
	}
	
	/**
	 * Updates the player based on input
	 */
	public void queryInput() {
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			float x = Gdx.input.getX();
			float y = Gdx.input.getY();
			Vector3 unprojectCoord = player.unproject(new Vector3(x, y, 0));
			float newX = unprojectCoord.x - player.getxPosition();
			float newY = unprojectCoord.y - player.getyPosition();
			double angleToShot = Math.atan2(newX, newY);
			player.rotate((float)Math.toDegrees(angleToShot));
			
			player.attack();
		}
		
		
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {

			if(Gdx.input.isKeyPressed(Input.Keys.A)) {
				player.rotate(-45);
				player.move(-Player.PLAYER_SPEED * Entity.DIAG_MULTIPLIER, Player.PLAYER_SPEED * Entity.DIAG_MULTIPLIER);
				
			} else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
				player.rotate(45);
				player.move(Player.PLAYER_SPEED * Entity.DIAG_MULTIPLIER, Player.PLAYER_SPEED * Entity.DIAG_MULTIPLIER);
			} else {
				player.rotate(0);
				player.move(0, Player.PLAYER_SPEED);
			}
		} else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			if(Gdx.input.isKeyPressed(Input.Keys.A)) {
				player.rotate(-135);
				player.move(-Player.PLAYER_SPEED * Entity.DIAG_MULTIPLIER, -Player.PLAYER_SPEED * Entity.DIAG_MULTIPLIER);
			} else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
				player.rotate(135);
				player.move(Player.PLAYER_SPEED * Entity.DIAG_MULTIPLIER, -Player.PLAYER_SPEED * Entity.DIAG_MULTIPLIER);
			} else {
				player.rotate(180); 
				player.move(0, -Player.PLAYER_SPEED);
			}
		} else if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			player.rotate(-90);
			player.move(-Player.PLAYER_SPEED, 0);
		} else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			player.rotate(90);
			player.move(Player.PLAYER_SPEED, 0);
		} else {
			player.move(0, 0);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
			player.meleeAttack();
	}

}
