package com.sawyerharris.capstone.algorithm.controller;

import java.util.concurrent.atomic.AtomicBoolean;

import com.badlogic.gdx.utils.TimeUtils;
import com.sawyerharris.capstone.algorithm.Algorithm;
import com.sawyerharris.capstone.simulation.PendulumCartSimulation;

public class AlgorithmController implements Runnable {
	private static final double TICKRATE = 1;
	
	private AtomicBoolean running;
	private Algorithm algorithm;
	
	public AlgorithmController(PendulumCartSimulation sim) {
		running = new AtomicBoolean();
		
		// Create algorithms
		algorithm = new TestAlgorithm(sim);
	}
	
	@Override
	public void run() {		
		long prevTime = TimeUtils.millis();
		while (true) {
			// If paused, wait until unpaused
			while (!running.get()) {
				synchronized(this) {
					try {
						wait();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
			
			// Update times
			long currTime = TimeUtils.millis();
			prevTime = currTime;
			
			// Run algorithm
			algorithm.update();
			
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
	
	public synchronized void setRunning(boolean run) {
		if (run) {
			running.set(true);
			notifyAll();
		} else if (!run) {
			running.set(false);
		}
	}
	
	public void setAlgorithm(Algorithm alg) {
		running.set(false);
		this.algorithm = alg;
	}
}
