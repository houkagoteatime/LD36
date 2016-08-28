package com.github.houkagoteatime.LD36.entities.enemies;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.github.houkagoteatime.LD36.entities.Player;
import com.github.houkagoteatime.LD36.levels.Level;
import com.github.houkagoteatime.LD36.weapons.BowAndArrow;

public class Archer extends Enemy{
	public static final int HEALTH = 200;
	public static final int DAMAGE = 500;
	public static final int SPEED = 40;
	public static final int RANGE = 500;
	public static final int AGGRO_RANGE = 900;
	private BowAndArrow weapon;
	private StateMachine<Archer, ArcherState> stateMachine;
	
	public Archer(Level level,Sprite sprite, Sprite weaponSprite, Player player) {
		super(level, HEALTH, DAMAGE, SPEED, sprite, player);
		weapon = new BowAndArrow(this, getLevel());
		weapon.setRange(500);
	}
	/**
	 *States that the enemy should be in
	 */
	private enum ArcherState implements State<Archer> {
		/**
		 *Enemy will attempt to move towards the player until they are out of aggro range
		 */
		AGGRO() {

			@Override
			public void update(Archer enemy) {
				if(!enemy.isPlayerNearby(AGGRO_RANGE)) {
					enemy.getStateMachine().changeState(STATIONARY);
				} else {
					if(enemy.isPlayerNearby(RANGE)) {
						enemy.attack();
					}
					enemy.move(enemy.player.getPosition().x - enemy.getPosition().x, enemy.player.getPosition().y - enemy.getPosition().y);
				}
			}

			@Override
			public void enter(Archer arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void exit(Archer arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean onMessage(Archer arg0, Telegram arg1) {
				// TODO Auto-generated method stub
				return false;
			}
			
		},
		
		/**
		 *An enemy that is sleeping will only attack if the player is nearby
		 */
		STATIONARY() {

			@Override
			public void update(Archer enemy) {
				if(enemy.isPlayerNearby(AGGRO_RANGE)) {
					enemy.getStateMachine().changeState(AGGRO);
				} else {
					if(enemy.isPlayerNearby(RANGE)) {
						enemy.attack();
					}
					enemy.move(0,0);
				}
			}

			@Override
			public void enter(Archer arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void exit(Archer arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean onMessage(Archer arg0, Telegram arg1) {
				// TODO Auto-generated method stub
				return false;
			}
			
		};
	}

	@Override
	public void init() {
		stateMachine = new DefaultStateMachine<Archer, ArcherState>(this, ArcherState.STATIONARY);
	}
	
	/* (non-Javadoc)
	 * @see com.github.houkagoteatime.LD36.entities.enemies.Enemy#update(float)
	 */
	@Override
	public void update(float dt) {
		super.update(dt);
		stateMachine.update();
	}

	@Override
	public void attack() {
		weapon.attack(getAngleToPlayer());
	}

	/**
	 * @return the stateMachine
	 */
	public StateMachine<Archer, ArcherState> getStateMachine() {
		return stateMachine;
	}

	/**
	 * @param stateMachine the stateMachine to set
	 */
	public void setStateMachine(StateMachine<Archer, ArcherState> stateMachine) {
		this.stateMachine = stateMachine;
	}

	/**
	 * @return the weapon
	 */
	public BowAndArrow getWeapon() {
		return weapon;
	}

	/**
	 * @param weapon the weapon to set
	 */
	public void setWeapon(BowAndArrow weapon) {
		this.weapon = weapon;
	}

}
