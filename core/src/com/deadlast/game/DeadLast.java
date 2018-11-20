package com.deadlast.game;

import com.badlogic.gdx.Game;
import com.deadlast.assets.Resources;
import com.deadlast.screens.*;

public class DeadLast extends Game {
	
	public Resources resources = new Resources();
	
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

}
