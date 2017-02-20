package com.sawyerharris.capstone.simulation;

public class SimplePendulumSimulation extends Simulation {
	@Override
	public void update(double dt) {
		super.update(dt);
		
		double omegaa = omega1 + dt / 2 * omegadot(psi1);
		double psia = psi1 + dt / 2 * omega1;
		
		double omegab = omega1 + dt / 2 * omegadot(psia);
		double psib = psi1 + dt / 2 * omegaa;
		
		double omegac = omega1 + dt * omegadot(psib);
		double psic = psi1 + dt * omegab;
		
		double omegad = omega1 + dt * omegadot(psic);
		double psid = psi1 + dt * omegac;
		
		omega1 = (omegaa + 2 * omegab + omegac + omegad / 2) / 3 - omega1 / 2;
		psi1 = (psia + 2 * psib + psic + psid / 2) / 3 - psi1 / 2;
		
		energy1 = -mass1 * gravity * Math.cos(psi1) + mass1 * length1 * length1 / 2 * omega1 * omega1;
	}
	
	private double omegadot(double psi) {
		return -gravity/length1 * Math.sin(psi);
	}

}
