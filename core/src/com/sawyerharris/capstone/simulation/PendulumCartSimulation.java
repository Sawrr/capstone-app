package com.sawyerharris.capstone.simulation;

public class PendulumCartSimulation extends Simulation {
	private double cartX;
	private double cartY;
	private double cartMass;
	private double forceX;
	
	@Override
	public void update(double dt) {
		super.update(dt);
	}
	
	@Override
	protected void updateParameter(String param, double value) {
		super.updateParameter(param, value);
		if (param.equals("cartX")) {
			cartX = value;
		} else if (param.equals("cartY")) {
			cartY = value;
		} else if (param.equals("cartMass")) {
			cartMass = value;
		} else if (param.equals("forceX")) {
			forceX = value;
		}
	}

	public double getCartX() {
		return cartX;
	}

	public double getCartY() {
		return cartY;
	}

	public double getCartMass() {
		return cartMass;
	}

	public double getForceX() {
		return forceX;
	}
	
}
