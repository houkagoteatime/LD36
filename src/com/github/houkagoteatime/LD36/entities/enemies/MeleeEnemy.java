package com.github.houkagoteatime.LD36.entities.enemies;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.github.houkagoteatime.LD36.entities.Player;
import com.github.houkagoteatime.LD36.levels.Level;
import com.github.houkagoteatime.LD36.weapons.Melee;
import com.github.houkagoteatime.LD36.weapons.Weapon;

/**
 * Enemy that just melees
 */
public class MeleeEnemy extends Enemy{

	private StateMachine<MeleeEnemy, EnemyState> defaultStateMachine;
	public static final float AGGRO_RANGE = 100f;
	public static final int HEALTH = 100;
	public static final int DAMAGE = 80;
	public static final int SPEED = 40;
	public static final int RANGE = 15;
	public static final int I_FRAME = 30;
	private Weapon weapon;
	
	
	
	/**
	 *States that the enemy should be in
	 */
	private enum EnemyState implements State<MeleeEnemy> {
		
		/**
		 *Enemy will attempt to move towards the player until they are out of aggro range
		 */
		AGGRO() {

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.ai.fsm.State#update(java.lang.Object)
			 */
			@Override
			public void update(MeleeEnemy enemy) {
				if(!enemy.isPlayerNearby(AGGRO_RANGE)) {
					enemy.getStateMachine().changeState(SLEEP);
				} else {
					if(enemy.isPlayerNearby(50)) {
						enemy.attack();
					}
					enemy.move(enemy.getPlayer().getPosition().x - enemy.getPosition().x, enemy.getPlayer().getPosition().y - enemy.getPosition().y);
				}
			}
			
		},
		
		FALLINTOPLACE() {

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.ai.fsm.State#update(java.lang.Object)
			 */
			@Override
			public void update(MeleeEnemy enemy) {
				enemy.move(enemy.getPlayer().getxPosition() + 16, enemy.getPlayer().getyPosition());
				
			}
		},
		/**
		 *An enemy that is sleeping will only attack if the player is nearby
		 */
		SLEEP() {

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.ai.fsm.State#update(java.lang.Object)
			 */
			@Override
			public void update(MeleeEnemy enemy) {
				if(enemy.isPlayerNearby(AGGRO_RANGE)) {
					enemy.getStateMachine().changeState(AGGRO);
				} else {
					enemy.move(0,0);
				}
			}
			
		};
		
		/* (non-Javadoc)
		 * @see com.badlogic.gdx.ai.fsm.State#enter(java.lang.Object)
		 */
		@Override
		public void enter(MeleeEnemy arg0) {
			
		}

		/* (non-Javadoc)
		 * @see com.badlogic.gdx.ai.fsm.State#exit(java.lang.Object)
		 */
		@Override
		public void exit(MeleeEnemy arg0) {
			
		}

		/* (non-Javadoc)
		 * @see com.badlogic.gdx.ai.fsm.State#onMessage(java.lang.Object, com.badlogic.gdx.ai.msg.Telegram)
		 */
		@Override
		public boolean onMessage(MeleeEnemy arg0, Telegram arg1) {
			return false;
		}
		
	}


	/**
	 * @param health health of the enemy
	 * @param damage how much damage it does
	 * @param sprite the enemy sprite
	 * @param speed the speed
	 * @param player the player playing the game
	 */
	public MeleeEnemy(Level level, Sprite sprite, Sprite weaponSprite, Player player) {
		super(level, HEALTH, DAMAGE, SPEED, sprite, player);
		weapon = new Melee(weaponSprite, this, RANGE, 10);
	}
	
	/* (non-Javadoc)
	 * @see com.github.houkagoteatime.LD36.entities.enemies.Enemy#init()
	 */
	@Override
	public void init() {
		this.defaultStateMachine = new DefaultStateMachine<MeleeEnemy, EnemyState>(this, EnemyState.SLEEP);
	}
	
	/* (non-Javadoc)
	 * @see com.github.houkagoteatime.LD36.entities.enemies.Enemy#update(float)
	 */
	@Override
	public void update(float dt) {
		this.defaultStateMachine.update();
		super.update(dt);
	}
	
	
	/**
	 * @return the stateMachine
	 */
	public StateMachine<MeleeEnemy, EnemyState> getStateMachine() {
		return defaultStateMachine;
	}

	/**
	 * @param stateMachine the stateMachine to set
	 */
	public void setStateMachine(StateMachine<MeleeEnemy, EnemyState> stateMachine) {
		this.defaultStateMachine = stateMachine;
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

	/* (non-Javadoc)
	 * @see com.github.houkagoteatime.LD36.entities.Entity#attack()
	 */
	@Override
	public void attack() {
		weapon.attack(getAngleToPlayer());
		getLevel().handleMelee((Melee)weapon);
	}
}