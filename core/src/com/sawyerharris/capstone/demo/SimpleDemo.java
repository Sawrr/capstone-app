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
	private Plot plot;
	
	public SimpleDemo() {
		simulation = new SimplePendulumSimulation();
		simulation.setParameter("gravity", 9.8);
		simulation.setParameter("length1", 1);
		simulation.setParameter("psi1", 1);
		simulation.setParameter("omega1", 0);
		simulation.setParameter("mass1", 1);
		
		simulationWindow = new Group();
		float simWidth = 600;
		float simHeight = 600;
		simulationWindow.setBounds(50, 50, simWidth, simHeight);
		
		pendulum = new Pendulum(new Vector2(simWidth/2, simHeight/2), 0, 100, 20);
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
		float interfaceWidth = 250;
		float interfaceHeight = 300;
		Table table = new Table();
		table.setBounds(0, 0, interfaceWidth, interfaceHeight);
		
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
		table.add(gravityLabel).spaceRight(10);
		table.add(gravitySlider);
		table.add(gravityValue).expandX();
		table.row();
		
		// Length parameter
		lengthSlider = new Slider(1, 200, 1f, false, skin);
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
		table.add(lengthLabel).spaceRight(10);
		table.add(lengthSlider);
		table.add(lengthValue).expandX();
		table.row();
		
		// Mass parameter
		massSlider = new Slider(1, 100, 1f, false, skin);
		massSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//PendulumApplication.getInstance().pauseSimulation();
				float value = ((Slider) actor).getValue();
				massValue.setText(String.format("%.0f", value));
				pendulum.radius = 10 + value / 5;
			}
		});
		massLabel = new Label("Mass", skin);
		massValue = new Label("", skin);
		table.add(massLabel).spaceRight(10);
		table.add(massSlider);
		table.add(massValue).expandX();
		table.row();

		interfaceWindow.setBounds(0, 0, interfaceWidth, interfaceHeight);
		interfaceWindow.addActor(table);
		
		plotWindow = new Group();
		plotWindow.setBounds(50, 50, 400, 600);
		plot = new Plot();
		plotWindow.addActor(plot);
	}
	
	public void update() {
		if (PendulumApplication.getInstance().isSimulationRunning()) {
			pendulum.angle = simulation.getPsi1() - Math.PI / 2;
		}
		System.out.println(simulation.getEnergy1());
		plot.enqueue((int) (100 * (simulation.getPsi1() % (2*Math.PI) )));
	}
}
