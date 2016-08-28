package com.github.houkagoteatime.LD36;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.github.houkagoteatime.LD36.entities.Entity;
import com.github.houkagoteatime.LD36.entities.Player;

/**
 *Class for updating the player movement based on input
 */
public class PlayerInputProcessor {

	private Player player;
	private static final int SCREEN_HALF_WIDTH = 600/2;
	private static final int SCREEN_HALF_HEIGHT= 735/2;
	private static final int LEEWAY = 90;
	
	public PlayerInputProcessor(Player player) {
		this.player = player;
	}
	
	/**
	 * Updates the player based on input
	 */
	public void queryInput() {
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			
			int x = Gdx.input.getX();
			int y = Gdx.input.getY();
			
			//(0,0) is top left of screen
			//(max,max) is bottom right
			
			//horizontal
			if(SCREEN_HALF_HEIGHT - LEEWAY < y  && y < SCREEN_HALF_HEIGHT + LEEWAY) {
				if(x < SCREEN_HALF_WIDTH) {
					player.rotate(-90);
				} else {
					player.rotate(90);
				}
			}  else if(SCREEN_HALF_WIDTH - LEEWAY < x  && x < SCREEN_HALF_WIDTH + LEEWAY) {
				if(y < SCREEN_HALF_WIDTH) {
					player.rotate(0);
				} else {
					player.rotate(180);
				}
			} else if(x < SCREEN_HALF_WIDTH - LEEWAY) {
				if(y < SCREEN_HALF_WIDTH) {
					player.rotate(-45);
				} else {
					player.rotate(-135);
				}
			} else if(x > SCREEN_HALF_WIDTH - LEEWAY) {
				if(y < SCREEN_HALF_WIDTH) {
					player.rotate(45);
				} else {
					player.rotate(135);
				}
			} 
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
		} else {
			player.move(0, 0);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
			player.attack();
	}

}
