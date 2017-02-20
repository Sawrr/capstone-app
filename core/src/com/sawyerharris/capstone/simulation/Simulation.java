package com.sawyerharris.capstone.simulation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Simulation {
	protected double gravity;
	protected double length1;
	protected double mass1;
	protected double psi1;
	protected double omega1;
	protected double energy1;
	
	private ConcurrentHashMap<String, Double> paramChanges;
	
	public Simulation() {
		paramChanges = new ConcurrentHashMap<String, Double>();
	}
	
	public void update(double dt) {
		updateParams();
	}
	
	public void setParameter(String param, double value) {
		paramChanges.put(param, value);
	}
	
	protected void updateParameter(String param, double value) {
		if (param.equals("gravity")) {
			gravity = value;
		} else if (param.equals("length1")) {
			length1 = value;
		} else if (param.equals("mass1")) {
			mass1 = value;
		} else if (param.equals("psi1")) {
			psi1 = value;
		} else if (param.equals("omega1")) {
			omega1 = value;
		} else if (param.equals("energy1")) {
			energy1 = value;
		}
	}
	
	private void updateParams() {
		for (Map.Entry<String, Double> entry : paramChanges.entrySet()) {
			updateParameter(entry.getKey(), entry.getValue());
			paramChanges.remove(entry.getKey());
		}
	}

	public double getGravity() {
		return gravity;
	}

	public double getLength1() {
		return length1;
	}

	public double getMass1() {
		return mass1;
	}

	public double getPsi1() {
		return psi1;
	}

	public double getOmega1() {
		return omega1;
	}

	public double getEnergy1() {
		return energy1;
	}
	
}
