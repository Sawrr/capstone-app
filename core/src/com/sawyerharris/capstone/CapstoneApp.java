package com.sawyerharris.capstone;

import com.badlogic.gdx.Game;

public class CapstoneApp extends Game {

	@Override
	public void create () {
		setScreen(new TestScreen());
	}

	@Override
	public void render () {
		super.render();
	}
}
