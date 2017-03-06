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
import com.sawyerharris.capstone.demo.Demo;
import com.sawyerharris.capstone.demo.SimpleDemo;
import com.sawyerharris.capstone.demo.SpringDemo;
import com.sawyerharris.capstone.simulator.Simulator;
import com.sawyerharris.capstone.util.SkinManager;

public class PendulumApplication extends ApplicationAdapter {
	private static final int NUM_DEMOS = 6;
	
	private static PendulumApplication instance;
	
	private Stage stage;
	private Simulator simulator;
	private Demo demo;
	private Demo[] demos;
	
	private Skin skin;
	private Table table;
	private VerticalGroup vGroup;
	private HorizontalGroup hGroup;
	
	public static PendulumApplication getInstance() {
		return instance;
	}
	
	@Override
	public void create() {
		instance = this;
		
		skin = SkinManager.getSkin();
		
		// Create list of demos
		demos = new Demo[NUM_DEMOS];
		demos[0] = new SimpleDemo();
		demos[1] = new SpringDemo();
		
		// Create GUI
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		vGroup = new VerticalGroup();
		vGroup.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		hGroup = new HorizontalGroup();
		
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
				System.out.println("clicked hi");
			}
		});
		
		demoButtons[3] = new TextButton("Parametric Resonance", skin);
		demoButtons[3].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("clicked hi");
			}
		});
		
		demoButtons[4] = new TextButton("Kapitza Pendulum", skin);
		demoButtons[4].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("clicked hi");
			}
		});
		
		demoButtons[5] = new TextButton("Pendulum on a Cart", skin);
		demoButtons[5].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("clicked hi");
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
		stage.addActor(vGroup);
		
		simulator = new Simulator();
		
		// Default demo
		setDemo(demos[0]);
		
		new Thread(simulator).start();
		
		
		//stage.setDebugAll(true);
	}

	public void pauseSimulation() {
		simulator.setRunning(false);
	}
	
	public void resumeSimulation() {
		simulator.setRunning(true);
	}
	
	public void setDemo(Demo demo) {
		this.demo = demo;
		
		
		simulator.setSimulation(demo.getSimulation());
		simulator.setRunning(true);
		
		hGroup.clearChildren();
		hGroup.addActor(demo.getPlotWindow());
		hGroup.addActor(demo.getSimulationWindow());
		hGroup.addActor(demo.getInterfaceWindow());	
	}
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		demo.update();
		stage.draw();
		//System.out.println(demo.getSimulation().getPsi1() + " " + demo.getSimulation().getOmega1() + " " + demo.getSimulation().getEnergy1());
	}

	public boolean isSimulationRunning() {
		return simulator.isRunning();
	}
}
