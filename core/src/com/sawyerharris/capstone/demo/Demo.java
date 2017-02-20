package com.sawyerharris.capstone.demo;

import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.sawyerharris.capstone.simulation.Simulation;

public abstract class Demo {
	protected Simulation simulation;
	protected WidgetGroup interfaceWindow;
	protected WidgetGroup simulationWindow;
	protected WidgetGroup plotWindow;
	
	public Simulation getSimulation() {
		return simulation;
	}
	public WidgetGroup getInterfaceWindow() {
		return interfaceWindow;
	}
	public WidgetGroup getSimulationWindow() {
		return simulationWindow;
	}
	public WidgetGroup getPlotWindow() {
		return plotWindow;
	}
}
