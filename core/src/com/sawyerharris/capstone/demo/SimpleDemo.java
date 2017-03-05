package com.sawyerharris.capstone.demo;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sawyerharris.capstone.app.PendulumApplication;
import com.sawyerharris.capstone.plot.Plot;
import com.sawyerharris.capstone.simulation.SimplePendulumSimulation;
import com.sawyerharris.capstone.view.Pendulum;

public class SimpleDemo extends Demo {
	private Pendulum pendulum;
	private Slider gravitySlider;
	private Label gravityLabel;
	private Label gravityValue;
	private Slider lengthSlider;
	private Label lengthLabel;
	private Label lengthValue;
	private Slider massSlider;
	private Label massLabel;
	private Label massValue;
	private Plot angularPlot;
	private Table interfaceTable;
	private Table plotTable;
	private Plot energyPlot;
	private TextButton angularButton;
	private TextButton energyButton;
	
	public SimpleDemo() {
		simulation = new SimplePendulumSimulation();
		simulation.setParameter("gravity", 9.8);
		simulation.setParameter("length1", 1);
		simulation.setParameter("psi1", 1);
		simulation.setParameter("omega1", 0);
		simulation.setParameter("mass1", 1);
		
		simulationWindow = new Group();
		simulationWindow.setBounds(50, 50, Demo.SIMULATION_WIDTH, Demo.SIMULATION_WIDTH);
		
		pendulum = new Pendulum(new Vector2(Demo.SIMULATION_WIDTH/2, Demo.SIMULATION_WIDTH/2), 0, 100, 20);
		pendulum.addListener(new ActorGestureListener() {
			@Override
			public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
				PendulumApplication.getInstance().pauseSimulation();
				float dx, dy;
				dx = x - Pendulum.TOUCH_RADIUS + (float) pendulum.getLengthX();
				dy = y - Pendulum.TOUCH_RADIUS + (float) pendulum.getLengthY();
				double angle = Math.atan2(dx, -dy);
				simulation.setParameter("psi1", angle);
				simulation.setParameter("omega1", 0);
				pendulum.angle = angle - Math.PI/2;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				// Update simulation parameters only
				simulation.update(0);
				PendulumApplication.getInstance().resumeSimulation();
			}
		});
		simulationWindow.addActor(pendulum);
		
		interfaceWindow = new Group();
		interfaceTable = new Table();
		interfaceTable.setBounds(0, 0, Demo.INTERFACE_WIDTH, Demo.INTERFACE_WIDTH);
		
		// Gravity parameter
		gravitySlider = new Slider(0, 100, .1f, false, skin);
		gravitySlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//PendulumApplication.getInstance().pauseSimulation();
				float value = ((Slider) actor).getValue();
				simulation.setParameter("gravity", value);
				gravityValue.setText(String.format("%.1f", value));
			}
		});
		gravityLabel = new Label("Gravity", skin);
		gravityValue = new Label("", skin);
		interfaceTable.add(gravityLabel).spaceRight(10);
		interfaceTable.add(gravitySlider);
		interfaceTable.add(gravityValue).expandX();
		interfaceTable.row();
		
		// Length parameter
		lengthSlider = new Slider(50, 200, 1f, false, skin);
		lengthSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//PendulumApplication.getInstance().pauseSimulation();
				float value = ((Slider) actor).getValue();
				simulation.setParameter("length1", value/100);
				pendulum.length = value;
				lengthValue.setText(String.format("%.0f", value));
			}
		});
		lengthLabel = new Label("Length", skin);
		lengthValue = new Label("", skin);
		interfaceTable.add(lengthLabel).spaceRight(10);
		interfaceTable.add(lengthSlider);
		interfaceTable.add(lengthValue).expandX();
		interfaceTable.row();
		
		// Mass parameter
		massSlider = new Slider(1, 100, 1f, false, skin);
		massSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//PendulumApplication.getInstance().pauseSimulation();
				float value = ((Slider) actor).getValue();
				simulation.setParameter("mass1", value);
				massValue.setText(String.format("%.0f", value));
				pendulum.radius = 10 + value / 5;
			}
		});
		massLabel = new Label("Mass", skin);
		massValue = new Label("", skin);
		interfaceTable.add(massLabel).spaceRight(10);
		interfaceTable.add(massSlider);
		interfaceTable.add(massValue).expandX();
		interfaceTable.row();

		interfaceWindow.setBounds(0, 0, Demo.INTERFACE_WIDTH, Demo.INTERFACE_WIDTH);
		interfaceWindow.addActor(interfaceTable);
		
		plotWindow = new Group();
		plotWindow.setBounds(0, 0, Demo.PLOT_WIDTH, Demo.PLOT_WIDTH);
		angularPlot = new Plot((float) Math.PI * 6, 0, true);
		energyPlot = new Plot(10000, 0, false);
		
		plotTable = new Table();
		plotTable.setBounds(0, Plot.PLOT_SIZE, Plot.PLOT_SIZE, 50);
		
		angularButton = new TextButton("Angle + Angular velocity", skin);
		angularButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				plotWindow.removeActor(energyPlot);
				plotWindow.addActor(angularPlot);
			}
		});
		
		energyButton = new TextButton("Energy", skin);
		energyButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				plotWindow.removeActor(angularPlot);
				plotWindow.addActor(energyPlot);
			}
		});
		
		plotTable.add(angularButton);
		plotTable.add(energyButton);
		
		plotWindow.addActor(angularPlot);
		plotWindow.addActor(plotTable);
	}
	
	public void update() {
		if (PendulumApplication.getInstance().isSimulationRunning()) {
			pendulum.angle = simulation.getPsi1() - Math.PI / 2;
		}
		System.out.println(simulation.getEnergy1());
		angularPlot.addData1((float) (simulation.getPsi1() % (2*Math.PI)));
		angularPlot.addData2((float) simulation.getOmega1());
		energyPlot.addData1((float) simulation.getEnergy1());
	}
}
