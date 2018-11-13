package com.deadlast.game;

import com.badlogic.gdx.Game;
import com.deadlast.assets.Resources;
import com.deadlast.screens.*;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainGame extends Game {
	
	public Resources resources;
	
	private LoadingScreen loadingScreen;
	private ScoreboardScreen scoreboardScreen;
	private MenuScreen menuScreen;
	private GameScreen gameScreen;
	private HelpScreen helpScreen;
	
	public final static int MENU = 0;
	public final static int SCOREBOARD = 1;
	public final static int HELP = 2;
	public final static int GAME = 3;
	
	@Override
	public void create() {
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
	}
	
	public void changeScreen(int screen) {
		switch(screen) {
		case MENU:
			if (menuScreen == null) menuScreen = new MenuScreen(this);
			this.setScreen(menuScreen);
			break;
		case SCOREBOARD:
			if (scoreboardScreen == null) scoreboardScreen = new ScoreboardScreen(this);
			this.setScreen(scoreboardScreen);
			break;
		case HELP:
			if (helpScreen == null) helpScreen = new HelpScreen(this);
			this.setScreen(helpScreen);
			break;
		case GAME:
			if (gameScreen == null) gameScreen = new GameScreen(this);
			this.setScreen(gameScreen);
			break;
		}
	}
	
	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
	
	@Override
	public void pause() {
		super.pause();
	}
//	SpriteBatch batch;
//	Texture img;
//	
//	@Override
//	public void create () {
//		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");
//	}
//
//	@Override
//	public void render () {
//		Gdx.gl.glClearColor(1, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
//	}
//	
//	@Override
//	public void dispose () {
//		batch.dispose();
//		img.dispose();
//	}
}
