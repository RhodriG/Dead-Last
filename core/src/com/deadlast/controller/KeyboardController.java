package com.deadlast.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class KeyboardController implements InputProcessor {
	
	public boolean left, right, up, down;

	@Override
	public boolean keyDown(int keycode) {
		boolean keyProcessed = false;
		switch (keycode)
		{
		case Keys.LEFT:
			left = true;
			keyProcessed = true;
			break;
		case Keys.RIGHT:
			right = true;
			keyProcessed = true;
			break;
		case Keys.UP:
			up = true;
			keyProcessed = true;
			break;
		case Keys.DOWN:
			down = true;
			keyProcessed = true;
			break;
		}
		return keyProcessed;
	}

	@Override
	public boolean keyUp(int keycode) {
		boolean keyProcessed = false;
		switch (keycode)
		{
		case Keys.LEFT:
			left = false;
			keyProcessed = true;
			break;
		case Keys.RIGHT:
			right = false;
			keyProcessed = true;
			break;
		case Keys.UP:
			up = false;
			keyProcessed = true;
			break;
		case Keys.DOWN:
			down = false;
			keyProcessed = true;
			break;
		}
		return keyProcessed;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
