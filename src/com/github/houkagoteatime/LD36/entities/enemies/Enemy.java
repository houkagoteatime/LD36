package com.github.houkagoteatime.LD36.entities.enemies;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.github.houkagoteatime.LD36.entities.Entity;
import com.github.houkagoteatime.LD36.entities.Player;


public class Enemy extends Entity{

	private StateMachine<Enemy, EnemyState> stateMachine;
	private Player player;
	
	/**
	 *States that the enemy should be in
	 */
	public enum EnemyState implements State<Enemy> {
		
		/**
		 *Enemy will attempt to move towards the player until they are out of aggro range
		 */
		AGGRO() {

			@Override
			public void update(Enemy enemy) {
				
			}
			
		},
		
		/**
		 *An enemy that is sleeping will only attack if the player is nearby
		 */
		SLEEP() {

			@Override
			public void update(Enemy enemy) {
				
			}
			
		};
		
		@Override
		public void enter(Enemy arg0) {
			
		}

		@Override
		public void exit(Enemy arg0) {
			
		}

		@Override
		public boolean onMessage(Enemy arg0, Telegram arg1) {
			return false;
		}
		
	}
	/**
	 * @param health health of the enemy
	 * @param damage how much damage it does
	 * @param sprite the enemy sprite
	 * @param player the player playing the game
	 */
	public Enemy(int health, int damage, Sprite sprite, Player player) {
		super(health, damage, sprite);
		this.player = player;
	}
	
	/**
	 * @return the stateMachine
	 */
	public StateMachine<Enemy, EnemyState> getStateMachine() {
		return stateMachine;
	}

	/**
	 * @param stateMachine the stateMachine to set
	 */
	public void setStateMachine(StateMachine<Enemy, EnemyState> stateMachine) {
		this.stateMachine = stateMachine;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	

}