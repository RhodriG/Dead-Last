package com.deadlast.screens;

import com.badlogic.gdx.Screen;
import com.deadlast.game.DeadLast;

/**
 * 
 * @author Xzytl
 *
 */
public abstract class DefaultScreen implements Screen {

	public final DeadLast game;
	
	/**
	 * Class constructor.
	 * Creates a screen with a reference to the {@link DeadLast} orchestrator class.
	 * @param game
	 */
	public DefaultScreen(DeadLast game) {
		this.game = game;
	}
	
}
