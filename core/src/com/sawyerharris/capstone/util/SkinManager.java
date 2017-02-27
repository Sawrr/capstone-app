package com.sawyerharris.capstone.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SkinManager {
	private static final Skin skin = new Skin(Gdx.files.internal("cloud-form/skin/cloud-form-ui.json"));
	
	public static Skin getSkin() {
		return skin;
	}
}
