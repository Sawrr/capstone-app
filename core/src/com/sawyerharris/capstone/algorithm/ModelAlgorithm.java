package com.sawyerharris.capstone.algorithm;

import com.sawyerharris.capstone.simulation.PendulumCartSimulation;

public class ModelAlgorithm extends Algorithm {	
	double paramPsi;
	double paramOmega;
	double paramV;
	
	public ModelAlgorithm(PendulumCartSimulation sim) {
		super(sim);
		
		// Initial guess
		paramPsi = -176.9;
		paramOmega = -54.71;
		paramV = 3.14;
	}

	@Override
	public void update() {
		double psi = (simulation.getPsi1() % (2*Math.PI)) - Math.PI;
		double omega = simulation.getOmega1();
		double v = simulation.getCartV();

		
		double force = paramPsi * psi + paramOmega * omega + paramV * v;
		simulation.setParameter("forceX", force);
	}

}
