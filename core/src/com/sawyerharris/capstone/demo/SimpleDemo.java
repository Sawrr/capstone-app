package com.sawyerharris.capstone.demo;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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
		simulationWindow.addActor(pendulum);
		
		interfaceWindow = new Group();
		Table table = new Table();
		TextButton button = new TextButton("hey lol", skin);
		TextButton button2 = new TextButton("hey lolzz", skin);
		button.setSize(400, 300);
		table.add(button);
		table.add(button2);
		table.setBounds(simWidth, simHeight/2, 100, 100);
		interfaceWindow.setBounds(0, 0, 2*simWidth, simHeight);
		interfaceWindow.addActor(table);
	}
	
	public void update() {
		pendulum.angle = simulation.getPsi1() - Math.PI / 2;
	}
}
