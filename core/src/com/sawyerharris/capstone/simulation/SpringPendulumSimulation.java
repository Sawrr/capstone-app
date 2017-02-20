package com.sawyerharris.capstone.simulation;

public class SpringPendulumSimulation extends TwoPendulumSimulation {
	private double springConstant;
	private double springRestLength;
	private double springSeparation;
	
	@Override
	public void update(double dt) {
		super.update(dt);
	}
	
	@Override
	protected void updateParameter(String param, double value) {
		super.updateParameter(param, value);
		if (param.equals("springConstant")) {
			springConstant = value;
		} else if (param.equals("springRestLength")) {
			springRestLength = value;
		} else if (param.equals("springSeparation")) {
			springSeparation = value;
		}
	}

	public double getSpringConstant() {
		return springConstant;
	}

	public double getSpringRestLength() {
		return springRestLength;
	}

	public double getSpringSeparation() {
		return springSeparation;
	}
	
}
