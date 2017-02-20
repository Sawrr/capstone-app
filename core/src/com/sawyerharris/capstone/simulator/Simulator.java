package com.sawyerharris.capstone.simulator;

import java.util.concurrent.atomic.AtomicBoolean;

import com.badlogic.gdx.utils.TimeUtils;
import com.sawyerharris.capstone.simulation.Simulation;

public class Simulator implements Runnable {
	private static final double TICKRATE = 10;
	private static final double DELTA_TIME = 0.01;
	
	private AtomicBoolean running;
	private Simulation simulation;
	
	public Simulator() {
		running = new AtomicBoolean();
	}
	
	@Override
	public void run() {
		running.set(true);
		
		long prevTime = TimeUtils.millis();
		while (running.get()) {
			// Update times
			long currTime = TimeUtils.millis();
			long elapsedTime = TimeUtils.timeSinceMillis(prevTime);
			prevTime = currTime;
			
			simulation.update(DELTA_TIME);
			
			long timeTaken = TimeUtils.millis() - prevTime;
			long tickTimeRemaining = (long) (TICKRATE) - timeTaken;
			try {
				Thread.sleep(tickTimeRemaining);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean isRunning() {
		return running.get();
	}
	
	public void setRunning(boolean run) {
		if (run) {
			run();
		} else {
			running.set(false);
		}
	}
	
	public void setSimulation(Simulation sim) {
		this.simulation = sim;
	}
}
