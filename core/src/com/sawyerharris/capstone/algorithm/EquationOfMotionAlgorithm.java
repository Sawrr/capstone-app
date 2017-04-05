package com.sawyerharris.capstone.algorithm;

import com.sawyerharris.capstone.simulation.PendulumCartSimulation;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.PI;

public class EquationOfMotionAlgorithm extends Algorithm {
	private static final double omega0 = 10;
	
	private static final double defPsi1 = PI - .1;
	private static final double defCartX = 10;
	private static final double defOmega1 = 1;
	private static final double defCartV = 0;
	
	public EquationOfMotionAlgorithm(PendulumCartSimulation sim) {
		super(sim);
				
		// Default simulation values
		simulation.setParameter("psi1", defPsi1);
		simulation.setParameter("omega1", defOmega1);
		simulation.setParameter("cartX", defCartX);
		simulation.setParameter("cartV", defCartV);
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
		double psiddot = dampedsho(omega0);
		double psiForce = (-psiddot * l * (M + m * sin(psi) * sin(psi)) + (M + m) * g * sin(psi) - m * l * omega * omega * cos(psi) * sin(psi) ) / (cos(psi));
		
		// Zero cart velocity
		double xddot = dampedCart(omega0);
		double cartForce = xddot * (M + m * sin(psi) * sin(psi)) - m * g * cos(psi) * sin(psi) - m * l * omega * omega * sin(psi);
				
		double force = psiForce;
		
		if (psiForce * cartForce > 0) {
			System.out.println("psif = " + psiForce);
			System.out.println("cartF = " + cartForce);
			System.out.println("    diff = " + (psiForce - cartForce));
			
			//force = (cartForce);
		}
		System.out.println("force = " + force + "\n");
		
		simulation.setParameter("forceX", force);
	}
	
	private double dampedCart(double omega0) {
		double cartV = simulation.getCartV();
		return -(2 * omega0 * cartV);
	}
	
	private double dampedsho(double omega0) {
		double psi = PI - (2*PI - (simulation.getPsi1() % (2*PI)));
		double omega = simulation.getOmega1();
		return -(omega0 * omega0 * psi + 2 * omega0 * omega);
	}

}
