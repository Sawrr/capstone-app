package com.sawyerharris.capstone.app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.sawyerharris.capstone.demo.Demo;
import com.sawyerharris.capstone.demo.SimpleDemo;
import com.sawyerharris.capstone.simulator.Simulator;

public class PendulumApplication extends ApplicationAdapter {
	private Stage stage;
	private Simulator simulator;
	private Demo demo;
	private Demo[] demos;
	
	@Override
	public void create() {
		// Create list of demos
		demos = new Demo[1];
		demos[0] = new SimpleDemo();
		
		stage = new Stage();
		simulator = new Simulator();
		
		demo = demos[0];
		
		simulator.setSimulation(demo.getSimulation());
		
		new Thread(simulator).start();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		System.out.println(demo.getSimulation().getPsi1() + " " + demo.getSimulation().getOmega1() + " " + demo.getSimulation().getEnergy1());
	}
}
