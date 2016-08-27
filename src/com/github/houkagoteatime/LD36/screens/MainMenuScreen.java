package com.github.houkagoteatime.LD36.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL30;
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


public class MainMenuScreen implements Screen{
	
	private LD36Game game;
	private Skin skin;
	private Stage stage;
	private Table table;
	private TextButton start;
	private TextButton quit;
	private TextButton refresh;
	private TextButton actualRefresh;
	private Sprite background;
	private TextureAtlas atlas;
	private AudioManager manager;
	public MainMenuScreen(LD36Game game) {
		this.game = game;
		manager = new AudioManager();
	}
	
	@Override
	public void show() {
		Assets.load();
		Assets.manager.finishLoading();
		background = new Sprite(new Texture(Gdx.files.internal("assets/pictures/mainscreen.jpg")));
		background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		atlas = new TextureAtlas(Gdx.files.internal("assets/data/uiskin.atlas"));
		skin = new Skin(Gdx.files.internal("assets/data/uiskin.json"),atlas);
		stage = new Stage();
		table = new Table(skin);
		table.setWidth(Gdx.graphics.getWidth());
		table.setPosition(0, Gdx.graphics.getHeight());
		start = new TextButton("New Game", skin);
		quit = new TextButton("End Game", skin);
		refresh = new TextButton("Refresh", skin);
		actualRefresh = new TextButton("Random button", skin);

		table.padTop(500);
		table.add(start).padBottom(30);
		table.row();
		table.add(quit).padBottom(30);
		table.row();
		table.add(refresh).padBottom(30);
		table.row();
		table.add(actualRefresh);
		stage.addActor(table);
		start.addListener(new ClickListener() {

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float)
			 */
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game));
				dispose();
			}
			
		});
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
		actualRefresh.addListener(new ClickListener() {

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float)
			 */
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MainMenuScreen(game));
				dispose();
			}
			
		});
		//manager.playMusic("assets/music/Ho-kago Tea Time - U&I.mp3");
		manager.playMusic("assets/music/Yume Miru Kusuri - Girls Are Made of Frosting Cake.mp3");
	}
	
	@Override
	public void render(float dt) {
		stage.act(Gdx.graphics.getDeltaTime());
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		game.getBatch().begin();
		background.draw(game.getBatch());
		game.getBatch().end();
		stage.draw();
		
	}
	
	@Override
	public void dispose() {
		stage.dispose();
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
