package com.github.houkagoteatime.LD36;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.github.houkagoteatime.LD36.entities.Entity;
import com.github.houkagoteatime.LD36.entities.Player;

/**
 *Class for updating the player movement based on input
 */
public class PlayerInputProcessor {

	private Player player;
	
	public PlayerInputProcessor(Player player) {
		this.player = player;
	}
	
	/**
	 * Updates the player based on input
	 */
	public void queryInput() {
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {

			if(Gdx.input.isKeyPressed(Input.Keys.A)) {
				player.rotate(-45);
				player.move(-Player.PLAYER_SPEED * Entity.DIAG_MULTIPLIER, -Player.PLAYER_SPEED * Entity.DIAG_MULTIPLIER);
				
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
				player.move(-Player.PLAYER_SPEED, -Player.PLAYER_SPEED * Entity.DIAG_MULTIPLIER);
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
		}
	}

}
