package com.deadlast.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.deadlast.controller.KeyboardController;
import com.deadlast.game.MainGame;
import com.deadlast.world.B2dModel;

public class GameScreen extends DefaultScreen {
	
	B2dModel model;
	private OrthographicCamera cam;
	private Box2DDebugRenderer debugRenderer;
	private KeyboardController controller;

	public GameScreen(MainGame game) {
		super(game);
		cam = new OrthographicCamera(32, 24);
		controller = new KeyboardController();
		model = new B2dModel(controller);
		debugRenderer = new Box2DDebugRenderer();
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(controller);
	}

	@Override
	public void render(float delta) {
		model.logicStep(delta);
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		debugRenderer.render(model.world, cam.combined);
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
