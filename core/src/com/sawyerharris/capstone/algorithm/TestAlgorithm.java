package com.sawyerharris.capstone.algorithm;

import com.sawyerharris.capstone.simulation.PendulumCartSimulation;

public class TestAlgorithm extends Algorithm {
	double paramPsi;
	double paramOmega;
	double paramV;
	
	public TestAlgorithm(PendulumCartSimulation sim) {
		super(sim);
		
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
