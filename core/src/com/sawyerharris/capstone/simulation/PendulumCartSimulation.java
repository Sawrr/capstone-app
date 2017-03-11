package com.sawyerharris.capstone.simulation;

import com.sawyerharris.capstone.demo.Demo;

public class PendulumCartSimulation extends Simulation {
	private double cartX;
	private double cartV;
	private double cartMass;
	private double forceX;
	
	@Override
	public void update(double dt) {
		super.update(dt);
		
		double omegaa = omega1 + dt / 2 * omegadot(psi1, omega1);
		double psia = psi1 + dt / 2 * omega1;
		double va = cartV + dt / 2 * vdot(psi1, omega1);
		double xa = cartX + dt / 2 * cartV;
		
		double omegab = omega1 + dt / 2 * omegadot(psia, omegaa);
		double psib = psi1 + dt / 2 * omegaa;
		double vb = cartV + dt / 2 * vdot(psia, omegaa);
		double xb = cartX + dt / 2 * va;
		
		double omegac = omega1 + dt * omegadot(psib, omegab);
		double psic = psi1 + dt * omegab;
		double vc = cartV + dt * vdot(psib, omegab);
		double xc = cartX + dt * vb;
		
		double omegad = omega1 + dt * omegadot(psic, omegac);
		double psid = psi1 + dt * omegac;
		double vd = cartV + dt * vdot(psic, omegac);
		double xd = cartX + dt * vc;
		
		omega1 = (omegaa + 2 * omegab + omegac + omegad / 2) / 3 - omega1 / 2;
		psi1 = (psia + 2 * psib + psic + psid / 2) / 3 - psi1 / 2;
		cartV = (va + 2 * vb + vc + vd / 2) / 3 - cartV / 2;
		cartX = (xa + 2 * xb + xc + xd / 2) / 3 - cartX / 2;
		
		float maxCartX = Demo.SIMULATION_WIDTH / Demo.LENGTH_SCALE;
		if (cartX > maxCartX) cartX %= maxCartX;
		if (cartX < 0) cartX += maxCartX;
	}
	
	private double omegadot(double psi, double omega) {
		return -(forceX*Math.cos(psi) + (cartMass + mass1)*gravity*Math.sin(psi) + mass1*length1*omega*omega*Math.cos(psi)*Math.sin(psi))/(length1*(cartMass + mass1*Math.sin(psi)*Math.sin(psi)));
	}
	
	private double vdot(double psi, double omega) {
		return (forceX + mass1*gravity*Math.cos(psi)*Math.sin(psi) + mass1*length1*omega*omega*Math.sin(psi))/(cartMass + mass1*Math.sin(psi)*Math.sin(psi));
	}
	
	@Override
	protected void updateParameter(String param, double value) {
		super.updateParameter(param, value);
		if (param.equals("cartX")) {
			cartX = value;
		} else if (param.equals("cartV")) {
			cartV = value;
		} else if (param.equals("cartMass")) {
			cartMass = value;
		} else if (param.equals("forceX")) {
			forceX = value;
		}
	}

	public double getCartX() {
		return cartX;
	}

	public double getCartMass() {
		return cartMass;
	}

	public double getForceX() {
		return forceX;
	}
	
}
