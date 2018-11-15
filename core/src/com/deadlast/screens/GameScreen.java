package com.deadlast.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.deadlast.controller.KeyboardController;
import com.deadlast.game.MainGame;
import com.deadlast.world.B2dModel;

public class GameScreen extends DefaultScreen {
	
	B2dModel model;
	private OrthographicCamera camera;
	private Box2DDebugRenderer debugRenderer;
	private KeyboardController controller;
	
	private Texture playerTex;
	private Texture enemyTex;
	private SpriteBatch batch;
	
	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;

	public GameScreen(MainGame game) {
		super(game);
		System.out.println("Loaded GameScreen");
		camera = new OrthographicCamera(32, 24);
		camera.position.set(10,10,0);
		camera.update();
		controller = new KeyboardController();
		
		model = new B2dModel(controller, camera);
		debugRenderer = new Box2DDebugRenderer();
		
		tiledMap = new TmxMapLoader().load("world/test.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/32f);
		
		playerTex = game.resources.manager.get(game.resources.playerImage);
		enemyTex = game.resources.manager.get(game.resources.enemyImage);
		batch = new SpriteBatch();
		//batch.setProjectionMatrix(camera.combined);
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
		debugRenderer.render(model.world, camera.combined);
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		batch.begin();
		batch.draw(playerTex, model.player.getPosition().x - 1, model.player.getPosition().y - 1, 2, 2);
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
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
