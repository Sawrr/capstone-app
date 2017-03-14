package com.sawyerharris.capstone.algorithm.controller;

import com.sawyerharris.capstone.algorithm.Algorithm;
import com.sawyerharris.capstone.simulation.PendulumCartSimulation;

public class TestAlgorithm extends Algorithm {
	int numTicks;
	double paramPsi;
	double paramOmega;
	double paramV;
	
	public TestAlgorithm(PendulumCartSimulation sim) {
		super(sim);
		numTicks = 0;
		
		paramPsi = -150;
		paramOmega = -50;
		paramV = 3.2;
	}

	@Override
	public void update() {
		double psi = simulation.getPsi1();
		double omega = simulation.getOmega1();
		double v = simulation.getCartV();
		double force = paramPsi * ((psi % (2*Math.PI)) - Math.PI) + paramOmega * omega + paramV * v;
		simulation.setParameter("forceX", force);
	}

}
