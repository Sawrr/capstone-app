package com.sawyerharris.capstone.algorithm;

import com.sawyerharris.capstone.simulation.PendulumCartSimulation;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.PI;

public class EquationOfMotionAlgorithm extends Algorithm {
	private static final double omega0 = 10;
	
	public EquationOfMotionAlgorithm(PendulumCartSimulation sim) {
		super(sim);
	}

	@Override
	public void update() {
		double M = simulation.getCartMass();
		double m = simulation.getMass1();
		double l = simulation.getLength1();
		double g = simulation.getGravity();
		double psi = simulation.getPsi1();
		double omega = simulation.getOmega1();
		
		// Angular stability
		double psiddot = dampedsho(omega0, 1);
		double psiForce = (-psiddot * l * (M + m * sin(psi) * sin(psi)) + (M + m) * g * sin(psi) - m * l * omega * omega * cos(psi) * sin(psi) ) / (cos(psi));

		simulation.setParameter("forceX", psiForce);
	}
	
	private double dampedsho(double omega0, double damping) {
		double psi = PI - (2*PI - (simulation.getPsi1() % (2*PI)));
		double omega = simulation.getOmega1();
		return -(omega0 * omega0 * psi + 2 * omega0 * damping * omega);
	}

}
