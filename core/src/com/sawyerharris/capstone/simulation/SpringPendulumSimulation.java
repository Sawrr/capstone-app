package com.sawyerharris.capstone.simulation;

import com.badlogic.gdx.math.Vector2;

public class SpringPendulumSimulation extends TwoPendulumSimulation {
	private double springConstant;
	private double springRestLength;
	private double springSeparation;
	
	@Override
	public void update(double dt) {
		super.update(dt);
				
		double omega1a = omega1 + dt / 2 * omega1dot(psi1, psi2);
		double omega2a = omega2 + dt / 2 * omega2dot(psi1, psi2);
		double psi1a = psi1 + dt / 2 * omega1;
		double psi2a = psi2 + dt / 2 * omega2;
		
		double omega1b = omega1 + dt / 2 * omega1dot(psi1a, psi2a);
		double omega2b = omega2 + dt / 2 * omega2dot(psi1a, psi2a);
		double psi1b = psi1 + dt / 2 * omega1a;
		double psi2b = psi2 + dt / 2 * omega2a;
		
		double omega1c = omega1 + dt * omega1dot(psi1b, psi2b);
		double omega2c = omega2 + dt * omega2dot(psi1b, psi2b);
		double psi1c = psi1 + dt * omega1b;
		double psi2c = psi2 + dt * omega2b;
		
		double omega1d = omega1 + dt * omega1dot(psi1c, psi2c);
		double omega2d = omega2 + dt * omega2dot(psi1c, psi2c);
		double psi1d = psi1 + dt * omega1c;
		double psi2d = psi2 + dt * omega2c;
		
		omega1 = (omega1a + 2 * omega1b + omega1c + omega1d / 2) / 3 - omega1 / 2;
		omega2 = (omega2a + 2 * omega2b + omega2c + omega2d / 2) / 3 - omega2 / 2;
		psi1 = (psi1a + 2 * psi1b + psi1c + psi1d / 2) / 3 - psi1 / 2;
		psi2 = (psi2a + 2 * psi2b + psi2c + psi2d / 2) / 3 - psi2 / 2;
		
		energy1 = -mass1 * gravity * length1 * Math.cos(psi1) + mass1 * length1 * length1 / 2 * omega1 * omega1;
		energy2 = -mass2 * gravity * length2 * Math.cos(psi2) + mass2 * length2 * length2 / 2 * omega2 * omega2;
	}
	
	private double omega1dot(double psi1, double psi2) {	
		Vector2 p1 = new Vector2((float) (length1 * Math.sin(psi1)), (float) (length1 * Math.cos(psi1)));
		Vector2 p2 = new Vector2((float) (length2 * Math.sin(psi2) + springSeparation), (float) (length2 * Math.cos(psi2)));
		
		Vector2 springForce = p2.sub(p1);
		springForce.scl((float) (springConstant * (springForce.len() - springRestLength)));
		
		Vector2 netForce1 = new Vector2();
		Vector2 gravForce1 = new Vector2(0, (float) (-mass1*gravity));
		netForce1.add(springForce);
		netForce1.add(gravForce1);
		
		Vector2 psi1hat = new Vector2((float) Math.cos(psi1), (float) Math.sin(psi1));
		psi1hat.setAngleRad((float) (psi1));
		
		return netForce1.dot(psi1hat);
	}
	
	private double omega2dot(double psi1, double psi2) {	
		Vector2 p1 = new Vector2((float) (length1 * Math.sin(psi1)), (float) (length1 * Math.cos(psi1)));
		Vector2 p2 = new Vector2((float) (length2 * Math.sin(psi2) + springSeparation), (float) (length2 * Math.cos(psi2)));
		
		Vector2 springForce = p1.sub(p2);
		springForce.scl((float) (springConstant * (springForce.len() - springRestLength)));
		
		Vector2 netForce2 = new Vector2();
		Vector2 gravForce2 = new Vector2(0, (float) (-mass2*gravity));
		netForce2.add(springForce);
		netForce2.add(gravForce2);
		
		Vector2 psi2hat = new Vector2((float) Math.cos(psi2), (float) Math.sin(psi2));
		psi2hat.setAngleRad((float) (psi2));
		
		return netForce2.dot(psi2hat);
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
