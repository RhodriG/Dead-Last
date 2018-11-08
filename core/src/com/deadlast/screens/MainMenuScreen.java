package com.deadlast.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deadlast.game.MainGame;

public class MainMenuScreen extends DefaultScreen {
	
	OrthographicCamera camera;
	TextureRegion title;
	SpriteBatch batch;
	
	Stage stage;
	
	public MainMenuScreen(MainGame game) {
		super(game);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
		
		stage = new Stage();
		stage.clear();
	}
	
	@Override
	public void show() {
		title = new TextureRegion(new Texture(Gdx.files.internal("title.jpg")), 0, 0, 800, 600);
		batch = new SpriteBatch();
		batch.getProjectionMatrix().setToOrtho2D(0, 0, 800, 600);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(title, 0, 0);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		Gdx.app.debug("DeadLast", "dispose main menu");
		batch.dispose();
		title.getTexture().dispose();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

}
