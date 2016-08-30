package com.github.houkagoteatime.LD36.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.github.houkagoteatime.LD36.AudioManager;
import com.github.houkagoteatime.LD36.LD36Game;
import com.github.houkagoteatime.LD36.entities.Player;
import com.github.houkagoteatime.LD36.entities.enemies.Gilgamesh;
import com.github.houkagoteatime.LD36.levels.Level;
import com.github.houkagoteatime.LD36.levels.Level1;
import com.github.houkagoteatime.LD36.levels.Level2;
import com.github.houkagoteatime.LD36.levels.Level3;
import com.github.houkagoteatime.LD36.levels.Level4;

public class GameScreen implements Screen{

	private OrthographicCamera cam;
	private Level level;
	private LD36Game game;
	private AudioManager manager;
	public GameScreen(LD36Game game, AudioManager manager) {
		this.game = game;
		cam = new OrthographicCamera(300, 300);
		level = new Level1(this);
		this.manager = manager;
		manager.playMusic("assets/music/America fuck yeah-team america.mp3");

		//level = new Level2(this);
		//level = new Level1(this);
		//level = new Level3(this);
		//level = new Level4(this);
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);


		level.spawnEnemies();
	}

	public GameScreen() {
		cam = new OrthographicCamera(500, 500);
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float dt) {
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		level.update(dt);
		updateCam(level.getPlayer());
		cam.update();
		level.getTiledMapRenderer().setView(cam);
		level.getTiledMapRenderer().render();
		game.getBatch().begin();
		game.getFont().draw(game.getBatch(), "Enemies Left: " + level.getEnemies().size(), 75, 25);
		game.getFont().draw(game.getBatch(), level.getPlayer().getHealth() + "/" + Player.MAX_HEALTH, 75, 40);
		if(level.getEnemies().isEmpty()) {
			game.getFont().draw(game.getBatch(), "Find the passage to the next level", 75, 55);
		}
		if(level instanceof Level3 && !level.getEnemies().isEmpty()) {
			game.getFont().draw(game.getBatch(), "Gilgamesh Health: " + level.getEnemies().get(0).getHealth() + "/" + Gilgamesh.MAX_HEALTH, 75, 55);
		}
		game.getBatch().end();
	}

	/**
	 * @param player center the camera around player
	 */
	public void updateCam(Player player) {
		float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
		float effectiveViewportHeight = cam.viewportHeight * cam.zoom;
		player.setCam(cam);
		cam.position.x = MathUtils.clamp(player.getPosition().x + player.getSprite().getWidth()/2, effectiveViewportWidth / 2f, level.mapPixelWidth - effectiveViewportWidth / 2f);
		cam.position.y = MathUtils.clamp(player.getPosition().y + player.getSprite().getHeight()/2, effectiveViewportHeight / 2f, level.mapPixelHeight - effectiveViewportWidth / 2f);
	}

	@Override
	public void dispose() {

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	public void switchLevel(int level) {
		if(level < 1 || level > 3) {
			throw new IllegalArgumentException("Invalid level");
		}
		this.level.dispose();
		switch(level) {
		case 1:
			this.level = new Level2(this);
			break;
		case 2:
			this.level = new Level3(this);
			break;
		case 3:
			this.level = new Level4(this);
			this.cam = new OrthographicCamera(this.level.mapPixelWidth, this.level.mapPixelHeight);
			cam.zoom = 0.9f;
			manager.playMusic("assets/music/Kuroko No Basket OST - 20.  Misdirection.mp3");
			break;
		}
		this.level.spawnEnemies();
	}

	public void gameOver() {
		game.setScreen(new GameOverScreen(game));
		dispose();
	}

	/**
	 * @return the level
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(Level level) {
		this.level = level;
	}

	/**
	 * @return the manager
	 */
	public AudioManager getManager() {
		return manager;
	}

	/**
	 * @param manager the manager to set
	 */
	public void setManager(AudioManager manager) {
		this.manager = manager;
	}
}
