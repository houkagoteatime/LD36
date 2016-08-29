package com.github.houkagoteatime.LD36.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.github.houkagoteatime.LD36.entities.Entity;
import com.github.houkagoteatime.LD36.entities.Player;
import com.github.houkagoteatime.LD36.levels.Level;

public class Rock extends ProjectileBasedWeapon{
	public int defaultDamage = 30;
	public static final int DELAY = 30;
	
	public Rock(Entity owner, Level level) {
		super(owner, level);
		projectileSprite = new Sprite(new Texture(Gdx.files.internal("assets/pictures/rockproj.png")));
		if(owner instanceof Player)
			friendly = true;
		range = 200;
		delay = DELAY;
	}
	
	@Override
	public void fireProjectile(float angle) {
		new RockProjectile(projectileSprite, owner.getDamage(), owner.getxPosition(), owner.getyPosition(), angle, range, friendly, level).spawn(level);

	}
	
	public class RockProjectile extends Projectile {
		public RockProjectile(Sprite sprite, int damage, float xPosition, float yPosition, float angle, float range, boolean isFriendly, Level level) {
			super(sprite, damage, xPosition, yPosition, angle, range, isFriendly);
		}
		
	}

	
}
