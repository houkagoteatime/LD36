package com.github.houkagoteatime.LD36.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.houkagoteatime.LD36.Assets;
import com.github.houkagoteatime.LD36.AudioManager;
import com.github.houkagoteatime.LD36.LD36Game;

/**
 *This class violates DRY on so many levels
 *Fix after ld plz
 */
public class GameOverScreen implements Screen{
	private LD36Game game;
	private Skin skin;
	private Stage stage;
	private Table table;
	private TextButton quit;
	private TextButton playAgain;
	private Sprite background;
	private TextureAtlas atlas;
	private AudioManager manager;
	
	public GameOverScreen(LD36Game game) {
		this.game = game;
		manager = new AudioManager();
	}
	
	@Override
	public void show() {
		Assets.load();
		Assets.manager.finishLoading();
		background = new Sprite(new Texture(Gdx.files.internal("assets/pictures/defeat.jpg")));
		background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		atlas = new TextureAtlas(Gdx.files.internal("assets/data/uiskin.atlas"));
		skin = new Skin(Gdx.files.internal("assets/data/uiskin.json"),atlas);
		stage = new Stage();
		table = new Table(skin);
		table.setWidth(Gdx.graphics.getWidth());
		table.setPosition(0, Gdx.graphics.getHeight());
		quit = new TextButton("End Game", skin);
		playAgain = new TextButton("Return to Main menu", skin);

		table.padTop(500);
		table.add(quit).padBottom(30);
		table.row();
		table.add(playAgain).padBottom(30);
		table.row();
		stage.addActor(table);
		quit.addListener(new ClickListener() {

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float)
			 */
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dispose();
				Gdx.app.exit();
			}
			
		});
		Gdx.input.setInputProcessor(stage);
		
		//this is the most ghetto fix ever, the listener for this button is where the refresh button is which does nothing
		playAgain.addListener(new ClickListener() {

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float)
			 */
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MainMenuScreen(game));
				dispose();
			}
			
		});
	}
	
	@Override
	public void render(float dt) {
		stage.act(dt);
		game.getBatch().begin();
		background.draw(game.getBatch());
		game.getBatch().end();
		stage.draw();
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
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

}
