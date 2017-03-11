package com.sawyerharris.capstone.demo;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sawyerharris.capstone.app.PendulumApplication;
import com.sawyerharris.capstone.plot.LinePlot;
import com.sawyerharris.capstone.plot.PhasePlot;
import com.sawyerharris.capstone.simulation.DoublePendulumSimulation;
import com.sawyerharris.capstone.view.Pendulum;
import com.sawyerharris.capstone.view.PendulumTrace;

public class DoubleDemo extends Demo {	
	private float defGravity = 9.8f;
	private float defLength = 2.5f;
	private float defPsi1 = 0.3f;
	private float defOmega1 = 0;
	private float defMass = 5;
	private float defPsi2 = 0;
	private float defOmega2 = 0;
	
	private Pendulum pendulum1;
	private Pendulum pendulum2;
	private PendulumTrace trace;
	
	private Slider gravitySlider;
	private Label gravityLabel;
	private Label gravityValue;
	private Slider length1Slider;
	private Label length1Label;
	private Label length1Value;
	private Slider length2Slider;
	private Label length2Label;
	private Label length2Value;
	private Slider mass1Slider;
	private Label mass1Label;
	private Label mass1Value;
	private Slider mass2Slider;
	private Label mass2Label;
	private Label mass2Value;
	private Table interfaceTable;
	private Table plotTable;
	private TextButton phaseButton;
	private PhasePlot phasePlot;
	private TextButton symModeButton;
	private TextButton antisymModeButton;
	private TextButton traceButton;
	
	public DoubleDemo() {
		////////////////
		// SIMULATION //
		////////////////
		
		simulation = new DoublePendulumSimulation();
		simulation.setParameter("gravity", defGravity);
		simulation.setParameter("length1", defLength);
		simulation.setParameter("psi1", defPsi1);
		simulation.setParameter("omega1", defOmega1);
		simulation.setParameter("mass1", defMass);
		simulation.setParameter("length2", defLength);
		simulation.setParameter("psi2", defPsi2);
		simulation.setParameter("omega2", defOmega2);
		simulation.setParameter("mass2", defMass);
		
		simulationWindow = new Group();
		simulationWindow.setBounds(0, 50, Demo.SIMULATION_WIDTH, Demo.SIMULATION_WIDTH);
		
		pendulum1 = new Pendulum(new Vector2(Demo.SIMULATION_WIDTH/2, Demo.SIMULATION_WIDTH/2), 0, defLength  * LENGTH_SCALE, MASS_BASE + defMass * MASS_SCALE);
		pendulum1.addListener(new ActorGestureListener() {
			@Override
			public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
				PendulumApplication.getInstance().pauseSimulation();
				float dx, dy;
				dx = x - Pendulum.TOUCH_RADIUS + (float) pendulum1.getLengthX();
				dy = y - Pendulum.TOUCH_RADIUS + (float) pendulum1.getLengthY();
				double angle = Math.atan2(dx, -dy);
				simulation.setParameter("psi1", angle);
				simulation.setParameter("omega1", 0);
				pendulum1.angle = angle - Math.PI/2;
				
				pendulum2.pivot.x = pendulum1.pivot.x + (float) pendulum1.getLengthX();
				pendulum2.pivot.y = pendulum1.pivot.y + (float) pendulum1.getLengthY();
				
				trace.reset();
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				// Update simulation parameters only
				simulation.update(0);
				PendulumApplication.getInstance().resumeSimulation();
			}
		});
		
		pendulum2 = new Pendulum(new Vector2(Demo.SIMULATION_WIDTH/2, Demo.SIMULATION_WIDTH/2), 0, defLength  * LENGTH_SCALE, MASS_BASE + defMass * MASS_SCALE);
		pendulum2.addListener(new ActorGestureListener() {
			@Override
			public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
				PendulumApplication.getInstance().pauseSimulation();
				float dx, dy;
				dx = x - Pendulum.TOUCH_RADIUS + (float) pendulum2.getLengthX();
				dy = y - Pendulum.TOUCH_RADIUS + (float) pendulum2.getLengthY();
				double angle = Math.atan2(dx, -dy);
				simulation.setParameter("psi2", angle);
				simulation.setParameter("omega2", 0);
				pendulum2.angle = angle - Math.PI/2;
				
				trace.reset();
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				// Update simulation parameters only
				simulation.update(0);
				PendulumApplication.getInstance().resumeSimulation();
			}
		});
		
		trace = new PendulumTrace();
		
		simulationWindow.addActor(pendulum1);
		simulationWindow.addActor(pendulum2);
		simulationWindow.addActor(trace);

		///////////////
		// INTERFACE //
		///////////////
		
		interfaceWindow = new Group();
		interfaceTable = new Table();
		interfaceTable.setBounds(0, 0, Demo.INTERFACE_WIDTH, Demo.INTERFACE_WIDTH);
		
		// Gravity parameter
		gravitySlider = new Slider(0, 50, .1f, false, skin);
		gravitySlider.setValue(defGravity);
		gravitySlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				float value = ((Slider) actor).getValue();
				simulation.setParameter("gravity", value);
				gravityValue.setText(String.format("%.1f", value));
			}
		});
		gravityLabel = new Label("Gravity", skin);
		gravityValue = new Label(String.format("%.1f", defGravity), skin);

		interfaceTable.add(gravityLabel).spaceRight(10);
		interfaceTable.add(gravitySlider);
		interfaceTable.add(gravityValue).expandX();
		interfaceTable.row();
		
		// Length 1 parameter
		length1Slider = new Slider(1f, 5, 0.1f, false, skin);
		length1Slider.setValue(defLength);
		length1Slider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				float value = ((Slider) actor).getValue();
				simulation.setParameter("length1", value);
				pendulum1.length = value * LENGTH_SCALE;
				length1Value.setText(String.format("%.1f", value));
			}
		});
		length1Label = new Label("Length 1", skin);
		length1Value = new Label(String.format("%.1f", defLength), skin);
		
		// Length 2 parameter
		length2Slider = new Slider(1f, 5, 0.1f, false, skin);
		length2Slider.setValue(defLength);
		length2Slider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				float value = ((Slider) actor).getValue();
				simulation.setParameter("length2", value);
				pendulum2.length = value * LENGTH_SCALE;
				length2Value.setText(String.format("%.1f", value));
			}
		});
		length2Label = new Label("Length 2", skin);
		length2Value = new Label(String.format("%.1f", defLength), skin);
		
		interfaceTable.add(length1Label).spaceRight(10);
		interfaceTable.add(length1Slider);
		interfaceTable.add(length1Value).expandX();
		interfaceTable.row();
		interfaceTable.add(length2Label).spaceRight(10);
		interfaceTable.add(length2Slider);
		interfaceTable.add(length2Value).expandX();
		interfaceTable.row();
		
		// Mass parameter
		mass1Slider = new Slider(1, 10, 1f, false, skin);
		mass1Slider.setValue(defMass);
		mass1Slider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				float value = ((Slider) actor).getValue();
				simulation.setParameter("mass1", value);
				mass1Value.setText(String.format("%.0f", value));
				pendulum1.radius = MASS_BASE + value * MASS_SCALE;
			}
		});
		mass1Label = new Label("Mass 1", skin);
		mass1Value = new Label(String.format("%.0f", defMass), skin);
		
		mass2Slider = new Slider(1, 10, 1f, false, skin);
		mass2Slider.setValue(defMass);
		mass2Slider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				float value = ((Slider) actor).getValue();
				simulation.setParameter("mass2", value);
				mass2Value.setText(String.format("%.0f", value));
				pendulum2.radius = MASS_BASE + value * MASS_SCALE;
			}
		});
		mass2Label = new Label("Mass 2", skin);
		mass2Value = new Label(String.format("%.0f", defMass), skin);
		
		interfaceTable.add(mass1Label).spaceRight(10);
		interfaceTable.add(mass1Slider);
		interfaceTable.add(mass1Value).expandX();
		interfaceTable.row();
		interfaceTable.add(mass2Label).spaceRight(10);
		interfaceTable.add(mass2Slider);
		interfaceTable.add(mass2Value).expandX();
		interfaceTable.row();
		
		symModeButton = new TextButton("Symmetric Mode", skin);
		symModeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				double ratio = 2 * (1 - (2*defMass*defLength) / (4*defMass*defLength + 2*Math.sqrt(2*defMass*defMass*defLength*defLength)));
								
				simulation.setParameter("psi1", defPsi1);
				simulation.setParameter("psi2", defPsi1 * ratio);
				simulation.setParameter("omega1", 0);
				simulation.setParameter("omega2", 0);
				simulation.setParameter("length1", defLength);
				length1Slider.setValue(defLength);
				simulation.setParameter("length2", defLength);
				length2Slider.setValue(defLength);
				simulation.setParameter("mass1", defMass);
				mass1Slider.setValue(defMass);
				simulation.setParameter("mass2", defMass);
				mass2Slider.setValue(defMass);
				simulation.setParameter("gravity", defGravity);
				gravitySlider.setValue(defGravity);
				
				trace.reset();
			}
		});
		
		antisymModeButton = new TextButton("Anti-Symmetric Mode", skin);
		antisymModeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				double ratio = 2 * (1 - (2*defMass*defLength) / (4*defMass*defLength - 2*Math.sqrt(2*defMass*defMass*defLength*defLength)));
								
				simulation.setParameter("psi1", defPsi1);
				simulation.setParameter("psi2", defPsi1 * ratio);
				simulation.setParameter("omega1", 0);
				simulation.setParameter("omega2", 0);
				simulation.setParameter("length1", defLength);
				length1Slider.setValue(defLength);
				simulation.setParameter("length2", defLength);
				length2Slider.setValue(defLength);
				simulation.setParameter("mass1", defMass);
				mass1Slider.setValue(defMass);
				simulation.setParameter("mass2", defMass);
				mass2Slider.setValue(defMass);
				simulation.setParameter("gravity", defGravity);
				gravitySlider.setValue(defGravity);
				
				trace.reset();
			}
		});
		
		interfaceTable.add(symModeButton);
		interfaceTable.add(antisymModeButton);
		
		traceButton = new TextButton("Toggle trace", skin);
		traceButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (simulationWindow.getChildren().contains(trace, true)) {
					simulationWindow.removeActor(trace);
				} else {
					trace.reset();
					simulationWindow.addActor(trace);
				}
			}
		});
		
		interfaceTable.row();
		interfaceTable.add(traceButton);
		
		interfaceWindow.setBounds(0, 0, Demo.INTERFACE_WIDTH, Demo.INTERFACE_WIDTH);
		interfaceWindow.addActor(interfaceTable);
		
		//////////////
		// PLOTTING //
		//////////////
		
		plotWindow = new Group();
		plotWindow.setBounds(0, 0, Demo.PLOT_WIDTH, Demo.PLOT_WIDTH);
		
		plotTable = new Table();
		plotTable.setBounds(0, LinePlot.PLOT_SIZE, LinePlot.PLOT_SIZE, 50);
		
		phaseButton = new TextButton("Phase Plot", skin);
		phaseButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("aerherh");
				phasePlot.reset();
			}
		});
		
		plotTable.add(phaseButton);
		
		phasePlot = new PhasePlot();
		
		plotWindow.addActor(phasePlot);
		plotWindow.addActor(plotTable);
	}
	
	public void update() {
		DoublePendulumSimulation sim = (DoublePendulumSimulation) simulation;
		
		if (PendulumApplication.getInstance().isSimulationRunning()) {
			pendulum1.angle = sim.getPsi1() - Math.PI / 2;
			pendulum2.angle = sim.getPsi2() - Math.PI / 2;
			
			pendulum2.pivot.x = pendulum1.pivot.x + (float) pendulum1.getLengthX();
			pendulum2.pivot.y = pendulum1.pivot.y + (float) pendulum1.getLengthY();
			
			float x = pendulum2.pivot.x + (float) pendulum2.getLengthX();
			float y = pendulum2.pivot.y + (float) pendulum2.getLengthY();
			trace.addVertex(x, y);
		}
		phasePlot.addData((float) (sim.getPsi2() % (2 * Math.PI)), (float) sim.getOmega2());
	}
}
