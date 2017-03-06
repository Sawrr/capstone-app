package com.sawyerharris.capstone.simulation;

public class DoublePendulumSimulation extends TwoPendulumSimulation {
	@Override
	public void update(double dt) {
		super.update(dt);
		
		double omega1a = omega1 + dt / 2 * omega1dot(psi1, psi2, omega1, omega2);
		double omega2a = omega2 + dt / 2 * omega2dot(psi1, psi2, omega1, omega2);
		double psi1a = psi1 + dt / 2 * omega1;
		double psi2a = psi2 + dt / 2 * omega2;
		
		double omega1b = omega1 + dt / 2 * omega1dot(psi1a, psi2a, omega1a, omega2a);
		double omega2b = omega2 + dt / 2 * omega2dot(psi1a, psi2a, omega1a, omega2a);
		double psi1b = psi1 + dt / 2 * omega1a;
		double psi2b = psi2 + dt / 2 * omega2a;
		
		double omega1c = omega1 + dt * omega1dot(psi1b, psi2b, omega1b, omega2b);
		double omega2c = omega2 + dt * omega2dot(psi1b, psi2b, omega1b, omega2b);
		double psi1c = psi1 + dt * omega1b;
		double psi2c = psi2 + dt * omega2b;
		
		double omega1d = omega1 + dt * omega1dot(psi1c, psi2c, omega1c, omega2c);
		double omega2d = omega2 + dt * omega2dot(psi1c, psi2c, omega1c, omega2c);
		double psi1d = psi1 + dt * omega1c;
		double psi2d = psi2 + dt * omega2c;
		
		omega1 = (omega1a + 2 * omega1b + omega1c + omega1d / 2) / 3 - omega1 / 2;
		omega2 = (omega2a + 2 * omega2b + omega2c + omega2d / 2) / 3 - omega2 / 2;
		psi1 = (psi1a + 2 * psi1b + psi1c + psi1d / 2) / 3 - psi1 / 2;
		psi2 = (psi2a + 2 * psi2b + psi2c + psi2d / 2) / 3 - psi2 / 2;
		
		//energy1 = -mass1 * gravity * length1 * Math.cos(psi1) + mass1 * length1 * length1 / 2 * omega1 * omega1;
	}
	
	private double omega1dot(double psi1, double psi2, double omega1, double omega2) {
		double mu = 1 + mass1/mass2;
		double num = gravity * (Math.sin(psi2) * Math.cos(psi1-psi2) - mu*Math.sin(psi1)) - (length2 * omega2*omega2 + length1 * omega1*omega1 * Math.cos(psi1 - psi2)) * Math.sin(psi1-psi2);
		double den = length1 * (mu - Math.cos(psi1-psi2)*Math.cos(psi1-psi2));
		return num / den;
	}
	
	private double omega2dot(double psi1, double psi2, double omega1, double omega2) {
		double mu = 1 + mass1/mass2;
		double num = gravity * mu * (Math.sin(psi1) * Math.cos(psi1-psi2) - Math.sin(psi2)) - (mu * length1 * omega1*omega1 + length2 * omega2*omega2 * Math.cos(psi1 - psi2)) * Math.sin(psi1-psi2);
		double den = length2 * (mu - Math.cos(psi1-psi2)*Math.cos(psi1-psi2));
		return num / den;
	}
}
