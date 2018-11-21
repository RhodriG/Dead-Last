package com.deadlast.screens;

import com.badlogic.gdx.Screen;
import com.deadlast.game.DeadLast;

public abstract class DefaultScreen implements Screen {

	public final DeadLast game;
	
	public DefaultScreen(DeadLast game) {
		this.game = game;
	}
	
}
