package com.sawyerharris.capstone.algorithm;

import java.util.ArrayList;
import java.util.Comparator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.TimeUtils;
import com.sawyerharris.capstone.simulation.PendulumCartSimulation;

public class RandomClimbAlgorithm extends Algorithm {
	private static final int MAX_TICKS = 500;
	
	private static final double defPsi1 = Math.PI - .1;
	private static final double defCartX = 10;
	private static final double defOmega1 = 0;
	private static final double defCartV = 0;
	
	double paramPsi;
	double paramOmega;
	double paramV;
	
	int numTicks;
	double fitnessScore;

	private ArrayList<Params> list;

	private FileHandle output;
	
	public RandomClimbAlgorithm(PendulumCartSimulation sim) {
		super(sim);
		
		list = new ArrayList<Params>();
		
		numTicks = 0;
		fitnessScore = 0;
		
		// Output file
		output = Gdx.files.local("output/randomClimb_" + TimeUtils.millis() + ".txt");
		
		// Initial guess
		paramPsi = -176.9;
		paramOmega = -54.71;
		paramV = 3.14;
		
		// Default simulation values
		simulation.setParameter("psi1", defPsi1);
		simulation.setParameter("omega1", defOmega1);
		simulation.setParameter("cartX", defCartX);
		simulation.setParameter("cartV", defCartV);
	}

	@Override
	public void update() {
		if (numTicks < MAX_TICKS) {
			double psi = (simulation.getPsi1() % (2*Math.PI)) - Math.PI;
			double omega = simulation.getOmega1();
			double v = simulation.getCartV();

			fitnessScore += Math.abs(psi) + Math.abs(omega) + Math.abs(v);
			
			double force = paramPsi * psi + paramOmega * omega + paramV * v;
			simulation.setParameter("forceX", force);
			numTicks++;
		} else {
			// Record stats
			System.out.println("Score: " + fitnessScore);
			System.out.println("Psi: " + paramPsi);
			System.out.println("Omega: " + paramOmega);
			System.out.println("V: " + paramV);
			
			list.add(new Params(paramPsi, paramOmega, paramV, fitnessScore));
			list.sort(new Comparator<Params>(){
				@Override
				public int compare(Params p1, Params p2) {
					return (int) (p1.fitnessScore - p2.fitnessScore);
				}
			});
			
			output.writeString(list.toString(), false);
			
			// Update params
			int changeIndex = (int) Math.floor(3 * Math.random());
			double change = 20 * (Math.random() - 0.5);
			System.out.println("changeIndex: " + changeIndex);
			System.out.println("change: " + change);
			System.out.println();
			
			System.out.println("leading score: " + list.get(0).fitnessScore);
			
			switch (changeIndex) {
			case 0:
				paramPsi += change;
				break;
			case 1:
				paramOmega += change;
				break;
			case 2:
				paramV += change / 10;
				break;
			}
			
			// Reset
			simulation.setParameter("cartX", defCartX);
			simulation.setParameter("psi1", defPsi1);
			simulation.setParameter("omega1", defOmega1);
			simulation.setParameter("cartV", defCartV);
			
			numTicks = 0;
			fitnessScore = 0;
		}
	}
	
	private class Params {
		public double paramPsi;
		public double paramOmega;
		public double paramV;
		public double fitnessScore;
		
		public Params(double paramPsi, double paramOmega, double paramV, double fitnessScore) {
			this.paramPsi = paramPsi;
			this.paramOmega = paramOmega;
			this.paramV = paramV;
			this.fitnessScore = fitnessScore;
		}

		@Override
		public String toString() {
			return "Params [paramPsi=" + paramPsi + ", paramOmega=" + paramOmega + ", paramV=" + paramV
					+ ", fitnessScore=" + fitnessScore + "]\n";
		}
	}

}
