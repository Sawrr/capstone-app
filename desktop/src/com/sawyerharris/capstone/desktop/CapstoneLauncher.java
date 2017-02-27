package com.sawyerharris.capstone.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sawyerharris.capstone.app.PendulumApplication;

public class CapstoneLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1200;
		config.height = 675;
		new LwjglApplication(new PendulumApplication(), config);
	}
}
