package com.sawyerharris.capstone.demo;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.sawyerharris.capstone.simulation.Simulation;
import com.sawyerharris.capstone.util.SkinManager;

public abstract class Demo {
	public static final int VALUE_WIDTH = 25;
	
	public static final float LENGTH_SCALE = 25f;
	public static final float MASS_SCALE = 1.5f;
	public static final float MASS_BASE = 5f;
	
	public static final float SIMULATION_WIDTH = 550;
	public static final float INTERFACE_WIDTH = 250;
	public static final float PLOT_WIDTH = 250;
	
	protected Simulation simulation;
	protected Group interfaceWindow;
	protected Group simulationWindow;
	protected Group plotWindow;
	
	protected Skin skin = SkinManager.getSkin();
	
	public Simulation getSimulation() {
		return simulation;
	}
	
	public Group getInterfaceWindow() {
		return interfaceWindow;
	}
	
	public Group getSimulationWindow() {
		return simulationWindow;
	}
	
	public Group getPlotWindow() {
		return plotWindow;
	}
	
	public abstract void update();
}
