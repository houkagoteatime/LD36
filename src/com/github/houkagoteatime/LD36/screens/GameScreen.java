package com.github.houkagoteatime.LD36.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.github.houkagoteatime.LD36.AudioManager;
import com.github.houkagoteatime.LD36.LD36Game;
import com.github.houkagoteatime.LD36.entities.Player;
import com.github.houkagoteatime.LD36.levels.Level;
import com.github.houkagoteatime.LD36.levels.Level1;

public class GameScreen implements Screen{

    private OrthographicCamera cam;
	private Level level;
	private LD36Game game;
	private AudioManager manager;
	public GameScreen(LD36Game game, AudioManager manager) {
		this.game = game;
		cam = new OrthographicCamera(500, 500);
		level = new Level1(this);
		
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		this.manager = manager;
		manager.playMusic("assets/music/America fuck yeah-team america.mp3");
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

	public void gameOver() {
		game.setScreen(new GameOverScreen(game));
		dispose();
	}
}
