package com.deadlast.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class KeyboardController implements InputProcessor {
	
	public boolean left, right, up, down;
	public boolean isMouse1Down, isMouse2Down, isMouse3Down;
	public boolean isDragged;
	public boolean isShiftDown;
	public Vector2 mouseLocation = new Vector2();

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
		if(button == 0) {
			isMouse1Down = true;
		}
		if(button == 1) {
			isMouse2Down = true;
		}
		if(button == 2) {
			isMouse3Down = true;
		}
		mouseLocation.x = screenX;
		mouseLocation.y = screenY;
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		isDragged = false;
		if(button == 0) {
			isMouse1Down = false;
		}
		if(button == 1) {
			isMouse2Down = false;
		}
		if(button == 2) {
			isMouse3Down = false;
		}
		mouseLocation.x = screenX;
		mouseLocation.y = screenY;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		isDragged = true;
		mouseLocation.x = screenX;
		mouseLocation.y = screenY;
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		mouseLocation.x = screenX;
		mouseLocation.y = screenY;
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
