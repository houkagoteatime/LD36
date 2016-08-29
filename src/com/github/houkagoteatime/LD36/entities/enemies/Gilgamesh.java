package com.github.houkagoteatime.LD36.entities.enemies;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.github.houkagoteatime.LD36.entities.Player;
import com.github.houkagoteatime.LD36.levels.Level;
import com.github.houkagoteatime.LD36.weapons.GateOfBabylon;
import com.github.houkagoteatime.LD36.weapons.Weapon;

public class Gilgamesh extends Enemy{
	public static final int MAX_HEALTH = 5000;
	public static final int DAMAGE = 30;
	public static final int SPEED = 50;
	private Weapon gateOfBabylon;
	private StateMachine<Gilgamesh, State<Gilgamesh>> machine;
	public static final float RANGE = 500f;
	private long startTime;
	private long timer;
	
	public Gilgamesh(Level level, Sprite sprite, Sprite wep, Player player) {
		super(level, MAX_HEALTH, DAMAGE, SPEED, sprite, player);
		gateOfBabylon = new GateOfBabylon(this, getLevel(), wep);
		startTime = System.currentTimeMillis();
		timer = startTime + 60000000;
	}
	
	private enum GilgameshState implements State<Gilgamesh> {
		PISSED_OFF() {

			@Override
			public void enter(Gilgamesh arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void exit(Gilgamesh arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean onMessage(Gilgamesh arg0, Telegram arg1) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void update(Gilgamesh arg0) {
				// TODO Auto-generated method stub
				
			}
			
		},
		STATIONARY() {

			@Override
			public void enter(Gilgamesh arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void exit(Gilgamesh arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean onMessage(Gilgamesh arg0, Telegram arg1) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void update(Gilgamesh enemy) {
				enemy.setStartTime(System.currentTimeMillis());
				if(enemy.getStartTime() < enemy.getTimer()) {
					enemy.getMachine().changeState(GilgameshState.PISSED_OFF);
				}
				
				enemy.attack();
			}
			
		};
		
	}

	/* (non-Javadoc)
	 * @see com.github.houkagoteatime.LD36.entities.enemies.Enemy#update(float)
	 */
	@Override
	public void update(float dt) {
		super.update(dt);
		machine.update();
	}
	@Override
	public void init() {
		machine = new DefaultStateMachine<Gilgamesh, State<Gilgamesh>>(this, GilgameshState.STATIONARY);
	}
	@Override
	public void attack() {
		System.out.println("attack");
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

}
