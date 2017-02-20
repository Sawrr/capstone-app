package com.sawyerharris.capstone.simulation;

public abstract class TwoPendulumSimulation extends Simulation {
	protected double length2;
	protected double mass2;
	protected double psi2;
	protected double omega2;
	protected double energy2;
	
	@Override
	protected void updateParameter(String param, double value) {
		super.updateParameter(param, value);
		if (param.equals("length2")) {
			length2 = value;
		} else if (param.equals("mass2")) {
			mass2 = value;
		} else if (param.equals("psi2")) {
			psi2 = value;
		} else if (param.equals("omega2")) {
			omega2 = value;
		} else if (param.equals("energy2")) {
			energy2 = value;
		}
	}

	public double getLength2() {
		return length2;
	}

	public double getMass2() {
		return mass2;
	}

	public double getPsi2() {
		return psi2;
	}

	public double getOmega2() {
		return omega2;
	}

	public double getEnergy2() {
		return energy2;
	}

}
