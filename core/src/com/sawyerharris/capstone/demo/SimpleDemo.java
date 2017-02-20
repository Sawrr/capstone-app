package com.sawyerharris.capstone.demo;

import com.sawyerharris.capstone.simulation.SimplePendulumSimulation;

public class SimpleDemo extends Demo {
	public SimpleDemo() {
		simulation = new SimplePendulumSimulation();
		simulation.setParameter("gravity", 9.8);
		simulation.setParameter("length1", 1);
		simulation.setParameter("psi1", 0);
		simulation.setParameter("omega1", 1);
		simulation.setParameter("mass1", 1);
	}
}
