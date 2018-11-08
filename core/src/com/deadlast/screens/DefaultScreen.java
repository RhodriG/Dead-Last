package com.deadlast.screens;

import com.badlogic.gdx.Screen;
import com.deadlast.game.MainGame;

public abstract class DefaultScreen implements Screen {

	public final MainGame game;
	
	public DefaultScreen(MainGame game) {
		this.game = game;
	}
	
}
