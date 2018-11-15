package com.deadlast.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deadlast.game.MainGame;

public class LoadingScreen extends DefaultScreen {
	
	SpriteBatch batch;
	private int currentLoadingStage = 0;
	private float countDown = 5f;
	
	final int IMAGE = 0;
	final int FONT = 1;
	final int SOUND = 2;
	final int MUSIC = 3;

	public LoadingScreen(MainGame game) {
		super(game);
		System.out.println("Loaded LoadingScreen");
		//game.resources.loadImages();
		//game.resources.manager.finishLoading();
	}

	@Override
	public void show() {
		// load assets for loading screen and pause
		// game.resources.loadStartAssets();
		// game.resources.manager.finishLoading();
		game.resources.loadImages();
		
		batch = new SpriteBatch();
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Render any loading screen
		// batch.begin();
		// batch.draw(do_something);
		// batch.end();
		
		if(game.resources.manager.update()) {
			currentLoadingStage += 1;
			switch(currentLoadingStage) {
			case FONT:
				System.out.println("Loading fonts");
				game.resources.loadFonts();
				break;
			case SOUND:
				System.out.println("Loading sounds");
				game.resources.loadSounds();
				break;
			case MUSIC:
				System.out.println("Loading music");
				game.resources.loadMusic();
				break;
			case 4:
				System.out.println("Finished loading");
			}
			if (currentLoadingStage > 4) {
				countDown -= delta;
				currentLoadingStage = 4;
				if (countDown <= 0) {
					game.changeScreen(MainGame.MENU);
				}
			}
		}
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
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
