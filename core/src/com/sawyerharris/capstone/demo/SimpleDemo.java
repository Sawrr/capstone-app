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
import com.sawyerharris.capstone.app.PendulumApplication;
import com.sawyerharris.capstone.plot.LinePlot;
import com.sawyerharris.capstone.simulation.SimplePendulumSimulation;
import com.sawyerharris.capstone.view.Pendulum;

public class SimpleDemo extends Demo {	
	private float defGravity = 9.8f;
	private float defLength1 = 3;
	private float defPsi1 = 1;
	private float defOmega1 = 0;
	private float defMass1 = 5;
	
	private Pendulum pendulum;
	private Slider gravitySlider;
	private Label gravityLabel;
	private Label gravityValue;
	private Slider lengthSlider;
	private Label lengthLabel;
	private Label lengthValue;
	private Slider massSlider;
	private Label massLabel;
	private Label massValue;
	private LinePlot angularPlot;
	private Table interfaceTable;
	private Table plotTable;
	private TextButton angularButton;
	
	public SimpleDemo() {
		////////////////
		// SIMULATION //
		////////////////
		
		simulation = new SimplePendulumSimulation();
		simulation.setParameter("gravity", defGravity);
		simulation.setParameter("length1", defLength1);
		simulation.setParameter("psi1", defPsi1);
		simulation.setParameter("omega1", defOmega1);
		simulation.setParameter("mass1", defMass1);
		
		simulationWindow = new Group();
		simulationWindow.setBounds(-50, 0, Demo.SIMULATION_WIDTH, Demo.SIMULATION_WIDTH);
		
		pendulum = new Pendulum(new Vector2(Demo.SIMULATION_WIDTH/2, Demo.SIMULATION_WIDTH/2), 0, defLength1 * LENGTH_SCALE, MASS_BASE + defMass1 * MASS_SCALE);
		pendulum.addListener(new ActorGestureListener() {
			@Override
			public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
				PendulumApplication.getInstance().pauseSimulation();
				float dx, dy;
				dx = x - Pendulum.TOUCH_RADIUS + (float) pendulum.getLengthX();
				dy = y - Pendulum.TOUCH_RADIUS + (float) pendulum.getLengthY();
				double angle = Math.atan2(dx, -dy);
				simulation.setParameter("psi1", angle);
				simulation.setParameter("omega1", 0);
				pendulum.angle = angle - Math.PI/2;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				// Update simulation parameters only
				simulation.update(0);
				PendulumApplication.getInstance().resumeSimulation();
			}
		});
		simulationWindow.addActor(pendulum);

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
		
		// Length parameter
		lengthSlider = new Slider(1f, 5, 0.1f, false, skin);
		lengthSlider.setValue(defLength1);
		lengthSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				float value = ((Slider) actor).getValue();
				simulation.setParameter("length1", value);
				pendulum.length = value * LENGTH_SCALE;
				lengthValue.setText(String.format("%.1f", value));
			}
		});
		lengthLabel = new Label("Length", skin);
		lengthValue = new Label(String.format("%.1f", defLength1), skin);
		
		interfaceTable.add(lengthLabel).spaceRight(10);
		interfaceTable.add(lengthSlider);
		interfaceTable.add(lengthValue).expandX();
		interfaceTable.row();
		
		// Mass parameter
		massSlider = new Slider(1, 10, 1f, false, skin);
		massSlider.setValue(defMass1);
		massSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				float value = ((Slider) actor).getValue();
				simulation.setParameter("mass1", value);
				massValue.setText(String.format("%.0f", value));
				pendulum.radius = MASS_BASE + value * MASS_SCALE;
			}
		});
		massLabel = new Label("Mass", skin);
		massValue = new Label(String.format("%.0f", defMass1), skin);	
		
		interfaceTable.add(massLabel).spaceRight(10);
		interfaceTable.add(massSlider);
		interfaceTable.add(massValue).expandX();
		interfaceTable.row();

		interfaceWindow.setBounds(0, 0, Demo.INTERFACE_WIDTH, Demo.INTERFACE_WIDTH);
		interfaceWindow.addActor(interfaceTable);
		
		//////////////
		// PLOTTING //
		//////////////
		
		plotWindow = new Group();
		plotWindow.setBounds(0, 0, Demo.PLOT_WIDTH, Demo.PLOT_WIDTH);
		angularPlot = new LinePlot((float) Math.PI * 6, 0, true);
		
		plotTable = new Table();
		plotTable.setBounds(0, LinePlot.PLOT_SIZE, LinePlot.PLOT_SIZE, 25);
		
		angularButton = new TextButton("Angle + Angular velocity", skin);
		
		plotTable.add(angularButton);
		
		plotWindow.addActor(angularPlot);
		plotWindow.addActor(plotTable);
	}
	
	public void update() {
		if (PendulumApplication.getInstance().isSimulationRunning()) {
			pendulum.angle = simulation.getPsi1() - Math.PI / 2;
		}
		angularPlot.addData1((float) (simulation.getPsi1() % (2*Math.PI)));
		angularPlot.addData2((float) simulation.getOmega1());
	}
}
