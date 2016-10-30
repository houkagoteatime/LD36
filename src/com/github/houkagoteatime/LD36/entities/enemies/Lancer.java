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
 * Enemy with a lance
 */
public class Lancer extends Enemy{

	public static final float RANGE = 25;
	public static final float AGGRO_RANGE = 175;
	public static final float TIME_PATROL = 20;
	private int tick = 0;
	
	private StateMachine<Lancer, LancerState> stateMachine;
	private Weapon wep;
	
	/**
	 * @param level current level
	 * @param sprite entity sprite
	 * @param wepSprite weapon sprite
	 * @param player player entity
	 */
	public Lancer(Level level, Sprite sprite, Sprite wepSprite, Player player) {
		super(level, 50, 200, 60, sprite, player);
		//it rides a pegasus and pegasi can fly over terrain
		setCollidable(false);
		wep = new Melee(wepSprite, this, 50, 80);
	}
	
	/**
	 * State enumeration
	 */
	private enum LancerState implements State<Lancer> {
		/**
		 *Enemy will attempt to move towards the player until they are out of aggro range
		 */
		AGGRO() {

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.ai.fsm.State#update(java.lang.Object)
			 */
			@Override
			public void update(Lancer enemy) {
				if(!enemy.isPlayerNearby(AGGRO_RANGE)) {
					enemy.getStateMachine().changeState(PATROL_FORWARD);
				} else {
					if(enemy.isPlayerNearby(RANGE)) {
						enemy.attack();
					}
					enemy.move(enemy.player.getPosition().x - enemy.getPosition().x, enemy.player.getPosition().y - enemy.getPosition().y);
				}
			}

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.ai.fsm.State#enter(java.lang.Object)
			 */
			@Override
			public void enter(Lancer arg0) {
				
			}

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.ai.fsm.State#exit(java.lang.Object)
			 */
			@Override
			public void exit(Lancer arg0) {
				
			}

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.ai.fsm.State#onMessage(java.lang.Object, com.badlogic.gdx.ai.msg.Telegram)
			 */
			@Override
			public boolean onMessage(Lancer arg0, Telegram arg1) {
				return false;
			}
			
		},
		PATROL_BACK() {

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.ai.fsm.State#enter(java.lang.Object)
			 */
			@Override
			public void enter(Lancer arg0) {
				
			}

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.ai.fsm.State#exit(java.lang.Object)
			 */
			@Override
			public void exit(Lancer arg0) {
				
			}

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.ai.fsm.State#onMessage(java.lang.Object, com.badlogic.gdx.ai.msg.Telegram)
			 */
			@Override
			public boolean onMessage(Lancer arg0, Telegram arg1) {
				return false;
			}

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.ai.fsm.State#update(java.lang.Object)
			 */
			@Override
			public void update(Lancer enemy) {
				if(enemy.isPlayerNearby(AGGRO_RANGE)) {
					enemy.getStateMachine().changeState(AGGRO);
				} else {
					if(enemy.isPlayerNearby(RANGE)) {
						enemy.attack();
					}
					enemy.move(0,-1);
					enemy.setTick(enemy.getTick() + 1);
					if(enemy.getTick() > Lancer.TIME_PATROL) {
						enemy.setTick(0);
						enemy.getStateMachine().changeState(PATROL_FORWARD);
					}
				}				
			}
			
		},
		
		/**
		 *An enemy that is sleeping will only attack if the player is nearby
		 */
		PATROL_FORWARD() {

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.ai.fsm.State#update(java.lang.Object)
			 */
			@Override
			public void update(Lancer enemy) {
				if(enemy.isPlayerNearby(AGGRO_RANGE)) {
					enemy.getStateMachine().changeState(AGGRO);
				} else {
					if(enemy.isPlayerNearby(RANGE)) {
						enemy.attack();
					}
					enemy.move(0,1);
					enemy.setTick(enemy.getTick() + 1);
					if(enemy.getTick() > Lancer.TIME_PATROL) {
						enemy.setTick(0);
						enemy.getStateMachine().changeState(LancerState.PATROL_BACK);
					}
				}
			}

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.ai.fsm.State#enter(java.lang.Object)
			 */
			@Override
			public void enter(Lancer arg0) {
				
			}

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.ai.fsm.State#exit(java.lang.Object)
			 */
			@Override
			public void exit(Lancer arg0) {
				
			}

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.ai.fsm.State#onMessage(java.lang.Object, com.badlogic.gdx.ai.msg.Telegram)
			 */
			@Override
			public boolean onMessage(Lancer arg0, Telegram arg1) {
				return false;
			}
			
		};
	}

	/* (non-Javadoc)
	 * @see com.github.houkagoteatime.LD36.entities.enemies.Enemy#init()
	 */
	@Override
	public void init() {
		stateMachine = new DefaultStateMachine<Lancer, Lancer.LancerState>(this, LancerState.PATROL_FORWARD);
	}

	/* (non-Javadoc)
	 * @see com.github.houkagoteatime.LD36.entities.enemies.Enemy#update(float)
	 */
	@Override
	public void update(float dt) {
		super.update(dt);
		stateMachine.update();
		if(stateMachine.isInState(LancerState.AGGRO)) {
			setRotation(getAngleToPlayer());
		}
	}

	/* (non-Javadoc)
	 * @see com.github.houkagoteatime.LD36.entities.Entity#attack()
	 */
	@Override
	public void attack() {
		wep.attack(getAngleToPlayer());
		getLevel().handleMelee((Melee)wep);
	}

	/**
	 * @return the stateMachine
	 */
	public StateMachine<Lancer, LancerState> getStateMachine() {
		return stateMachine;
	}

	/**
	 * @param stateMachine the stateMachine to set
	 */
	public void setStateMachine(StateMachine<Lancer, LancerState> stateMachine) {
		this.stateMachine = stateMachine;
	}

	/**
	 * @return the tick
	 */
	public int getTick() {
		return tick;
	}

	/**
	 * @param tick the tick to set
	 */
	public void setTick(int tick) {
		this.tick = tick;
	}

}
