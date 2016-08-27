package com.github.houkagoteatime.LD36;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.houkagoteatime.LD36.screens.MainMenuScreen;

public class LD36Game extends Game{
	
	private SpriteBatch batch;
	private BitmapFont font;
	public static void main(String args[]) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Harambe 2: Origin Stories";
		config.width = 600;
		config.height = 800;
		new LwjglApplication(new LD36Game(), config);
	}
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		setScreen(new MainMenuScreen(this));
	}
	
	/**
	 * @return the batch
	 */
	public SpriteBatch getBatch() {
		return batch;
	}
	/**
	 * @param batch the batch to set
	 */
	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}
	/**
	 * @return the font
	 */
	public BitmapFont getFont() {
		return font;
	}
	/**
	 * @param font the font to set
	 */
	public void setFont(BitmapFont font) {
		this.font = font;
	}


}
