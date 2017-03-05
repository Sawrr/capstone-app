package com.sawyerharris.capstone.demo;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.sawyerharris.capstone.simulation.Simulation;
import com.sawyerharris.capstone.util.SkinManager;

public abstract class Demo {
	public static final float SIMULATION_WIDTH = 600;
	public static final float INTERFACE_WIDTH = 300;
	public static final float PLOT_WIDTH = 600;
	
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
