package com.github.houkagoteatime.LD36.entities.enemies;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.houkagoteatime.LD36.entities.Player;
import com.github.houkagoteatime.LD36.levels.Level;
import com.github.houkagoteatime.LD36.weapons.GateOfBabylon;
import com.github.houkagoteatime.LD36.weapons.Melee;
import com.github.houkagoteatime.LD36.weapons.Weapon;

/**
 * An enemy that gets pissed
 */
public class Gilgamesh extends Enemy{
	public static final int MAX_HEALTH = 1500;
	public static final int DAMAGE = 15;
	public static final int SPEED = 50;
	public static final int TP = 6;
	public static int tpCounter;
	private Weapon gateOfBabylon;
	private StateMachine<Gilgamesh, State<Gilgamesh>> machine;
	public static final float RANGE = 500f;
	private Melee ea;
	private long startTime;
	private long timer;
	private ArrayList<Vector2> tpPositions;
	
	/**
	 * @param level current level
	 * @param sprite entity sprite
	 * @param wep weapon sprite
	 * @param player player entity
	 */
	public Gilgamesh(Level level, Sprite sprite, Sprite wep, Player player) {
		super(level, MAX_HEALTH, DAMAGE, SPEED, sprite, player);
		gateOfBabylon = new GateOfBabylon(this, getLevel(), wep);
		startTime = System.currentTimeMillis();
		timer = startTime + 120000;
		ea = new Melee(new Sprite(new Texture(Gdx.files.internal("assets/pictures/ea.png"))), this, 30, 50);
		getBounds().set(new Rectangle(getxPosition(), getyPosition(), 32, 32));
	}
	
	/**
	 * 
	 */
	private enum GilgameshState implements State<Gilgamesh> {
		PISSED_OFF() {

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.ai.fsm.State#enter(java.lang.Object)
			 */
			@Override
			public void enter(Gilgamesh arg0) {
				
			}

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.ai.fsm.State#exit(java.lang.Object)
			 */
			@Override
			public void exit(Gilgamesh arg0) {
				
			}

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.ai.fsm.State#onMessage(java.lang.Object, com.badlogic.gdx.ai.msg.Telegram)
			 */
			@Override
			public boolean onMessage(Gilgamesh arg0, Telegram arg1) {
				return false;
			}

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.ai.fsm.State#update(java.lang.Object)
			 */
			@Override
			public void update(Gilgamesh enemy) {
				enemy.attack();
				enemy.setStartTime(System.currentTimeMillis());
				if(!enemy.isPlayerNearby(500)) {
					enemy.teleportToPlayer();
				} 
				if(enemy.isPlayerNearby(50) && enemy.getStartTime() > enemy.getTimer() + 10000) {
					enemy.meleeAttack();
				}
			}
			
		},
		BORED() {

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.ai.fsm.State#enter(java.lang.Object)
			 */
			@Override
			public void enter(Gilgamesh arg0) {
				
			}

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.ai.fsm.State#exit(java.lang.Object)
			 */
			@Override
			public void exit(Gilgamesh arg0) {
				
			}

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.ai.fsm.State#onMessage(java.lang.Object, com.badlogic.gdx.ai.msg.Telegram)
			 */
			@Override
			public boolean onMessage(Gilgamesh arg0, Telegram arg1) {
				return false;
			}

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.ai.fsm.State#update(java.lang.Object)
			 */
			@Override
			public void update(Gilgamesh enemy) {
				tpCounter++;
				if(tpCounter % (TP * 60) == 0) { 
					enemy.randomTeleport();
				}
				enemy.setStartTime(System.currentTimeMillis());
				if(enemy.getStartTime() > enemy.getTimer()) {
					enemy.teleportToPlayer();
					((GateOfBabylon)enemy.getGateOfBabylon()).setSwordsFired(2);
					enemy.getMachine().changeState(GilgameshState.PISSED_OFF);
				} else if(enemy.isPlayerNearby(75)) {
					enemy.randomTeleport();
				}
				
				enemy.attack();
			}
			
		};
		
	}

	/**
	 * Teleport to a random location
	 */
	public void randomTeleport() {
		int random = new Random().nextInt(tpPositions.size());
		if(getxPosition() == tpPositions.get(random).x && getyPosition() == tpPositions.get(random).x) {
			randomTeleport();
		} else {
			setxPosition(tpPositions.get(random).x);
			setyPosition(tpPositions.get(random).y);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.github.houkagoteatime.LD36.entities.enemies.Enemy#update(float)
	 */
	@Override
	public void update(float dt) {
		super.update(dt);
		machine.update();
	}
	
	/* (non-Javadoc)
	 * @see com.github.houkagoteatime.LD36.entities.enemies.Enemy#init()
	 */
	@Override
	public void init() {
		machine = new DefaultStateMachine<Gilgamesh, State<Gilgamesh>>(this, GilgameshState.BORED);
		tpPositions = new ArrayList<>();
		tpPositions.add(new Vector2(getxPosition(), getyPosition()));
		tpPositions.add(new Vector2(getxPosition() + 100, getyPosition() + 100));
		tpPositions.add(new Vector2(getxPosition() - 100, getyPosition() + 100));
		tpPositions.add(new Vector2(getxPosition() + 100, getyPosition() - 100));
		tpPositions.add(new Vector2(getxPosition() -100, getyPosition() - 100));
	}
	/* (non-Javadoc)
	 * @see com.github.houkagoteatime.LD36.entities.Entity#attack()
	 */
	@Override
	public void attack() {
		gateOfBabylon.attack(getAngleToPlayer());
	}
	/**
	 * @return the startTime
	 */
	public long getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the timer
	 */
	public long getTimer() {
		return timer;
	}
	/**
	 * @param timer the timer to set
	 */
	public void setTimer(long timer) {
		this.timer = timer;
	}
	/**
	 * @return the gateOfBabylon
	 */
	public Weapon getGateOfBabylon() {
		return gateOfBabylon;
	}
	/**
	 * @param gateOfBabylon the gateOfBabylon to set
	 */
	public void setGateOfBabylon(Weapon gateOfBabylon) {
		this.gateOfBabylon = gateOfBabylon;
	}
	/**
	 * @return the machine
	 */
	public StateMachine<Gilgamesh, State<Gilgamesh>> getMachine() {
		return machine;
	}
	/**
	 * @param machine the machine to set
	 */
	public void setMachine(StateMachine<Gilgamesh, State<Gilgamesh>> machine) {
		this.machine = machine;
	}
	
	/**
	 * Attack with no range
	 */
	public void meleeAttack() {
		ea.attack(getAngleToPlayer());
		getLevel().handleMelee(ea);
	}
	
	/**
	 * Instantly move to player's position
	 */
	public void teleportToPlayer() {
		setxPosition(player.getxPosition() + 25 - new Random().nextInt(26));
		setyPosition(player.getyPosition() + 25 - new Random().nextInt(26));
	}

}
