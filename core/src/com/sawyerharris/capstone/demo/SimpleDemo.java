package com.sawyerharris.capstone.demo;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sawyerharris.capstone.app.PendulumApplication;
import com.sawyerharris.capstone.simulation.SimplePendulumSimulation;
import com.sawyerharris.capstone.view.Pendulum;

public class SimpleDemo extends Demo {
	private Pendulum pendulum;
	
	public SimpleDemo() {
		simulation = new SimplePendulumSimulation();
		simulation.setParameter("gravity", 9.8);
		simulation.setParameter("length1", 1);
		simulation.setParameter("psi1", 1);
		simulation.setParameter("omega1", 0);
		simulation.setParameter("mass1", 1);
		
		simulationWindow = new Group();
		float simWidth = 400;
		float simHeight = 300;
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
		Table table = new Table();
		Slider gravitySlider = new Slider(0, 100, 1, false, skin);
		gravitySlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				PendulumApplication.getInstance().pauseSimulation();
				simulation.setParameter("gravity", ((Slider) actor).getValue());
				System.out.println(((Slider) actor).getValue() +" g");
			}
		});
		
		Slider lengthSlider = new Slider(1, 200, 1f, false, skin);
		lengthSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				PendulumApplication.getInstance().pauseSimulation();
				float value =  ((Slider) actor).getValue();
				simulation.setParameter("length1", value/100);
				pendulum.length = value;
				System.out.println(value);
			}
		});
		
		TextButton button = new TextButton("hey lol", skin);
		TextButton button2 = new TextButton("hey lolzz", skin);
		button.setSize(400, 300);
		table.add(button);
		table.add(button2);
		table.row();
		table.add(gravitySlider);
		table.row();
		table.add(lengthSlider);
		table.setBounds(simWidth, simHeight/2, 100, 100);
		interfaceWindow.setBounds(0, 0, 2*simWidth, simHeight);
		interfaceWindow.addActor(table);
	}
	
	public void update() {
		if (PendulumApplication.getInstance().isSimulationRunning()) {
			pendulum.angle = simulation.getPsi1() - Math.PI / 2;
		}
	}
}
