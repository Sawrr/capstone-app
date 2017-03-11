package com.sawyerharris.capstone.simulation;

public class VerticalOscillationSimulation extends Simulation {
	private double omegaDrive;
	private double driveAmplitude;
	private double damping;
	
	private double t = 0;
	
	@Override
	public void update(double dt) {
		super.update(dt);
		t += dt;
		
		double omegaa = omega1 + dt / 2 * omegadot(psi1, omega1, t);
		double psia = psi1 + dt / 2 * omega1;
		
		double omegab = omega1 + dt / 2 * omegadot(psia, omegaa, t + dt/2);
		double psib = psi1 + dt / 2 * omegaa;
		
		double omegac = omega1 + dt * omegadot(psib, omegab, t + dt/2);
		double psic = psi1 + dt * omegab;
		
		double omegad = omega1 + dt * omegadot(psic, omegac, t + dt);
		double psid = psi1 + dt * omegac;
		
		omega1 = (omegaa + 2 * omegab + omegac + omegad / 2) / 3 - omega1 / 2;
		psi1 = (psia + 2 * psib + psic + psid / 2) / 3 - psi1 / 2;
	}
	
	private double omegadot(double psi, double omega, double t) {
		double omega0sq = gravity/length1;
		double alpha = driveAmplitude / (2 * length1) * (omegaDrive * omegaDrive) / (omega0sq);
		return -2 * damping * omega - omega0sq * Math.sin(psi) * (1 + 2 * alpha * Math.cos(omegaDrive * t));
	}
	
	@Override
	protected void updateParameter(String param, double value) {
		super.updateParameter(param, value);
		if (param.equals("omegaDrive")) {
			omegaDrive = value;
		} else if (param.equals("driveAmplitude")) {
			driveAmplitude = value;
		} else if (param.equals("damping")) {
			damping = value;
		}
	}

	public double getOmegaDrive() {
		return omegaDrive;
	}

	public double getDriveAmplitude() {
		return driveAmplitude;
	}

	public double getDamping() {
		return damping;
	}
	
	public double getT() {
		return t;
	}

}
