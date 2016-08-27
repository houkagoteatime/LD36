package com.github.houkagoteatime.LD36.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.github.houkagoteatime.LD36.LD36Game;


public class MainMenuScreen implements Screen{
	
	private LD36Game game;
	private Skin skin;
	private Stage stage;
	private Table table;
	private TextButton start;
	private TextButton quit;
	private Sprite background;
	private TextureAtlas atlas;
	public MainMenuScreen(LD36Game game) {
		this.game = game;
		
	}
	
	@Override
	public void show() {
		background = new Sprite(new Texture(Gdx.files.internal("assets/pictures/mainscreen.jpg")));
		background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		atlas = new TextureAtlas(Gdx.files.internal("assets/data/uiskin.atlas"));
		skin = new Skin(Gdx.files.internal("assets/data/uiskin.json"),atlas);
		stage = new Stage();
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		start = new TextButton("New Game", skin);
		start.addListener(new ClickListener() {

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float)
			 */
			@Override
			public void clicked(InputEvent event, float x, float y) {
				start.setText("Done");
				dispose();
				Gdx.app.exit();
			}
			
		});
		quit = new TextButton("End Game", skin);
		table.padTop(30);
		table.add(start).padBottom(30);
		table.row();
		table.add(quit);
		stage.addActor(table);
		start.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		Gdx.input.setInputProcessor(stage);
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
