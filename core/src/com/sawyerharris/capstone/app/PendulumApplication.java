package com.sawyerharris.capstone.app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.sawyerharris.capstone.algorithm.controller.AlgorithmController;
import com.sawyerharris.capstone.demo.CartDemo;
import com.sawyerharris.capstone.demo.Demo;
import com.sawyerharris.capstone.demo.DoubleDemo;
import com.sawyerharris.capstone.demo.VerticalDemo;
import com.sawyerharris.capstone.simulation.PendulumCartSimulation;
import com.sawyerharris.capstone.demo.SimpleDemo;
import com.sawyerharris.capstone.demo.SpringDemo;
import com.sawyerharris.capstone.simulator.Simulator;
import com.sawyerharris.capstone.util.SkinManager;

public class PendulumApplication extends ApplicationAdapter {
	private static final int NUM_DEMOS = 6;
	
	private static PendulumApplication instance;
	
	private Stage stage;
	private StretchViewport viewport;
	private Simulator simulator;
	private AlgorithmController controller;
	private Demo demo;
	private Demo[] demos;
	
	private Skin skin;
	private Table table;
	private VerticalGroup vGroup;
	private HorizontalGroup hGroup;
	private VerticalGroup hvGroup;
	
	public static PendulumApplication getInstance() {
		return instance;
	}
	
	@Override
	public void create() {
		instance = this;
		
		skin = SkinManager.getSkin();
		
		// Create cart sim for demo and algorithm
		PendulumCartSimulation pcs = new PendulumCartSimulation();
		
		// Create list of demos
		demos = new Demo[NUM_DEMOS];
		demos[0] = new SimpleDemo();
		demos[1] = new SpringDemo();
		demos[2] = new DoubleDemo();
		demos[3] = new VerticalDemo();
		demos[4] = new CartDemo(pcs);
		
		// Create algorithm controller
		controller = new AlgorithmController(pcs);
		
		// Create GUI
		viewport = new StretchViewport(900, 600);
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);
		
		vGroup = new VerticalGroup();
		vGroup.setBounds(0, 0, 900, 600);
		hGroup = new HorizontalGroup();
		hvGroup = new VerticalGroup();
		
		table = new Table();
		TextButton[] demoButtons = new TextButton[NUM_DEMOS];
		
		demoButtons[0] = new TextButton("Simple Pendulum", skin);
		demoButtons[0].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setDemo(demos[0]);
			}
		});
		
		demoButtons[1] = new TextButton("Spring-coupled Pendula", skin);
		demoButtons[1].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setDemo(demos[1]);
			}
		});
		
		demoButtons[2] = new TextButton("Double Pendulum", skin);
		demoButtons[2].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setDemo(demos[2]);
			}
		});
		
		demoButtons[3] = new TextButton("Vertical Oscillations", skin);
		demoButtons[3].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setDemo(demos[3]);
			}
		});
		
		demoButtons[4] = new TextButton("Pendulum on a Cart", skin);
		demoButtons[4].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setDemo(demos[4]);
				controller.setRunning(true);
			}
		});
			
		table.add(demoButtons);
		
		TextButton playButton = new TextButton("Play", skin);
		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				simulator.setRunning(true);
			}
		});
		TextButton pauseButton = new TextButton("Pause", skin);
		pauseButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				simulator.setRunning(false);
			}
		});
		
		table.row();
		table.add(playButton);
		table.add(pauseButton);
		
		vGroup.addActor(table);
				
		vGroup.addActor(hGroup);
		hGroup.addActor(hvGroup);
		stage.addActor(vGroup);
		
		simulator = new Simulator();
		
		// Default demo
		setDemo(demos[0]);
		
		new Thread(simulator).start();
		new Thread(controller).start();
		
		//stage.setDebugAll(true);
	}

	public void pauseSimulation() {
		simulator.setRunning(false);
		controller.setRunning(false);
	}
	
	public void resumeSimulation() {
		simulator.setRunning(true);
		controller.setRunning(false);
	}
	
	public void setDemo(Demo demo) {
		this.demo = demo;
		
		controller.setRunning(false);
		
		simulator.setSimulation(demo.getSimulation());
		simulator.setRunning(true);
		
		hGroup.clearChildren();
		hvGroup.clearChildren();
		hGroup.addActor(demo.getSimulationWindow());
		hGroup.addActor(hvGroup);
		hvGroup.addActor(demo.getInterfaceWindow());
		hvGroup.addActor(demo.getPlotWindow());
	}
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		demo.update();
		stage.draw();
	}

	public boolean isSimulationRunning() {
		return simulator.isRunning();
	}
	
	public boolean isAlgorithmRunning() {
		return controller.isRunning();
	}
}
