package com.sawyerharris.capstone.algorithm;

import com.sawyerharris.capstone.simulation.PendulumCartSimulation;

public abstract class Algorithm {
	protected PendulumCartSimulation simulation;
	
	public Algorithm(PendulumCartSimulation sim) {
		simulation = sim;
	}
	
	public abstract void update();
}
