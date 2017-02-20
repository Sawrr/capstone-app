package com.sawyerharris.capstone.simulation;

public class VerticalOscillationSimulation extends Simulation {
	private double omegaDrive;
	private double driveAmplitude;
	private double damping;
	
	@Override
	public void update(double dt) {
		super.update(dt);
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

}
