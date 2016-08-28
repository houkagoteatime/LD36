package com.github.houkagoteatime.LD36.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.github.houkagoteatime.LD36.entities.Entity;
import com.github.houkagoteatime.LD36.entities.Player;
import com.github.houkagoteatime.LD36.levels.Level;
import com.github.houkagoteatime.LD36.weapons.Rock.RockProjectile;

public class BowAndArrow extends ProjectileBasedWeapon{

	

	public BowAndArrow(Entity owner, Level level) {
		super(owner, level);
		if(owner instanceof Player)
			friendly = true;
		projectileSprite = new Sprite(new Texture(Gdx.files.internal("assets/pictures/arrow.png")));
		range = 500;
		delay = 60;
	}

	@Override
	public void fireProjectile(float angle) {
		projectileSprite.setRotation(-angle);
		new Arrow(projectileSprite, owner.getDamage(), owner.getxPosition(), owner.getyPosition(), angle, range, friendly).spawn(level);

	}
	
	public class Arrow extends Projectile {

		public Arrow(Sprite sprite, int damage, float xPosition, float yPosition, float angle, float range,
				boolean isFriendly) {
			super(sprite, damage, xPosition, yPosition, angle, range, isFriendly, level.getWallLayer());
		}
		
	}

}
