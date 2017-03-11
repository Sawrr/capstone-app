package com.sawyerharris.capstone.demo;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sawyerharris.capstone.app.PendulumApplication;
import com.sawyerharris.capstone.plot.LinePlot;
import com.sawyerharris.capstone.simulation.VerticalOscillationSimulation;
import com.sawyerharris.capstone.view.Pendulum;

public class VerticalDemo extends Demo {
	private float defGravity = 9.8f;
	private float defLength = 3;
	private float defPsi1 = .02f;
	private float defOmega1 = 0;
	private float defMass = 1;
	private float defOmegaDrive = 3.615f;
	private float defDriveAmplitude = 0.15f;
	private float defDamping = 0;
	private float defOmegaDriveKapitza = 200;
	private float defPsi1Kapitza = (float) Math.PI + 0.3f;
	private float defDriveAmplitudeKapitza = 0.05f;
	
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
	private LinePlot energyPlot;
	private TextButton angularButton;
	private TextButton energyButton;
	private Slider omegaDriveSlider;
	private Label omegaDriveLabel;
	private Label omegaDriveValue;
	private Slider driveAmplitudeSlider;
	private Label driveAmplitudeLabel;
	private Label driveAmplitudeValue;
	private ProgressBar dampingSlider;
	private Label dampingValue;
	private Label dampingLabel;
	private TextButton kapitzaButton;
	private Actor parametricButton;
	
	public VerticalDemo() {
		////////////////
		// SIMULATION //
		////////////////
		
		simulation = new VerticalOscillationSimulation();
		simulation.setParameter("gravity", defGravity);
		simulation.setParameter("length1", defLength);
		simulation.setParameter("psi1", defPsi1);
		simulation.setParameter("omega1", defOmega1);
		simulation.setParameter("mass1", defMass);
		simulation.setParameter("omegaDrive", defOmegaDrive);
		simulation.setParameter("driveAmplitude", defDriveAmplitude);
		simulation.setParameter("damping", defDamping);
		
		simulationWindow = new Group();
		simulationWindow.setBounds(50, 50, Demo.SIMULATION_WIDTH, Demo.SIMULATION_WIDTH);
		
		pendulum = new Pendulum(new Vector2(Demo.SIMULATION_WIDTH/2, Demo.SIMULATION_WIDTH/2), 0, defLength * LENGTH_SCALE, MASS_BASE + defMass * MASS_SCALE);
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
		lengthSlider.setValue(defLength);
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
		lengthValue = new Label(String.format("%.1f", defLength), skin);
		
		interfaceTable.add(lengthLabel).spaceRight(10);
		interfaceTable.add(lengthSlider);
		interfaceTable.add(lengthValue).expandX();
		interfaceTable.row();
		
		// Mass parameter
		massSlider = new Slider(1, 10, 1f, false, skin);
		massSlider.setValue(defMass);
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
		massValue = new Label(String.format("%.0f", defMass), skin);	
		
		interfaceTable.add(massLabel).spaceRight(10);
		interfaceTable.add(massSlider);
		interfaceTable.add(massValue).expandX();
		interfaceTable.row();

		// Damping parameter
		dampingSlider = new Slider(0, 1, .1f, false, skin);
		dampingSlider.setValue(defDamping);
		dampingSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				float value = ((Slider) actor).getValue();
				simulation.setParameter("damping", value);
				dampingValue.setText(String.format("%.1f", value));
			}
		});
		dampingLabel = new Label("Damping", skin);
		dampingValue = new Label(String.format("%.1f", defDamping), skin);
		
		interfaceTable.add(dampingLabel).spaceRight(10);
		interfaceTable.add(dampingSlider);
		interfaceTable.add(dampingValue).expandX();
		interfaceTable.row();
		
		// Drive frequency parameter
		omegaDriveSlider = new Slider(1, 300, 1f, false, skin);
		omegaDriveSlider.setValue(defOmegaDrive);
		omegaDriveSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				float value = ((Slider) actor).getValue();
				simulation.setParameter("omegaDrive", value);
				omegaDriveValue.setText(String.format("%.0f", value));
			}
		});
		omegaDriveLabel = new Label("Drive Frequency", skin);
		omegaDriveValue = new Label(String.format("%.0f", defOmegaDrive), skin);
		
		interfaceTable.add(omegaDriveLabel).spaceRight(10);
		interfaceTable.add(omegaDriveSlider);
		interfaceTable.add(omegaDriveValue).expandX();
		interfaceTable.row();
		
		// Drive amplitude parameter
		driveAmplitudeSlider = new Slider(0.01f, 0.2f, 0.01f, false, skin);
		driveAmplitudeSlider.setValue(defDriveAmplitude);
		driveAmplitudeSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				float value = ((Slider) actor).getValue();
				simulation.setParameter("driveAmplitude", value);
				driveAmplitudeValue.setText(String.format("%.2f", value));
			}
		});
		driveAmplitudeLabel = new Label("Drive Amplitude", skin);
		driveAmplitudeValue = new Label(String.format("%.2f", defDriveAmplitude), skin);	
		
		interfaceTable.add(driveAmplitudeLabel).spaceRight(10);
		interfaceTable.add(driveAmplitudeSlider);
		interfaceTable.add(driveAmplitudeValue).expandX();
		interfaceTable.row();
		
		parametricButton = new TextButton("Parametric Drive", skin);
		parametricButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				simulation.setParameter("psi1", defPsi1);
				simulation.setParameter("omega1", defOmega1);
				simulation.setParameter("driveAmplitude", defDriveAmplitude);
				driveAmplitudeSlider.setValue(defDriveAmplitude);
				simulation.setParameter("length1", defLength);
				lengthSlider.setValue(defLength);
				simulation.setParameter("mass1", defMass);
				massSlider.setValue(defMass);
				simulation.setParameter("omegaDrive", defOmegaDrive);
				omegaDriveSlider.setValue(defOmegaDrive);
				simulation.setParameter("damping", defDamping);
				dampingSlider.setValue(defDamping);
			}
		});
		
		kapitzaButton = new TextButton("Kapitza's Pendulum", skin);
		kapitzaButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				simulation.setParameter("psi1", defPsi1Kapitza);
				simulation.setParameter("omega1", defOmega1);
				simulation.setParameter("length1", defLength);
				lengthSlider.setValue(defLength);
				simulation.setParameter("mass1", defMass);
				massSlider.setValue(defMass);
				simulation.setParameter("gravity", defGravity);
				gravitySlider.setValue(defGravity);
				simulation.setParameter("omegaDrive", defOmegaDriveKapitza);
				omegaDriveSlider.setValue(defOmegaDriveKapitza);
				simulation.setParameter("driveAmplitude", defDriveAmplitudeKapitza);
				driveAmplitudeSlider.setValue(defDriveAmplitudeKapitza);
			}
		});
		
		interfaceTable.add(parametricButton);
		interfaceTable.add(kapitzaButton);
		
		interfaceWindow.setBounds(0, 0, Demo.INTERFACE_WIDTH, Demo.INTERFACE_WIDTH);
		interfaceWindow.addActor(interfaceTable);
		
		//////////////
		// PLOTTING //
		//////////////
		
		plotWindow = new Group();
		plotWindow.setBounds(0, 0, Demo.PLOT_WIDTH, Demo.PLOT_WIDTH);
		angularPlot = new LinePlot((float) Math.PI * 6, 0, true);
		energyPlot = new LinePlot(3000, 0, false);
		
		plotTable = new Table();
		plotTable.setBounds(0, LinePlot.PLOT_SIZE, LinePlot.PLOT_SIZE, 50);
		
		angularButton = new TextButton("Angle + Angular velocity", skin);
		angularButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				plotWindow.removeActor(energyPlot);
				plotWindow.addActor(angularPlot);
			}
		});
		
		energyButton = new TextButton("Energy", skin);
		energyButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				plotWindow.removeActor(angularPlot);
				plotWindow.addActor(energyPlot);
			}
		});
		
		plotTable.add(angularButton);
		plotTable.add(energyButton);
		
		plotWindow.addActor(angularPlot);
		plotWindow.addActor(plotTable);
	}
	
	public void update() {
		if (PendulumApplication.getInstance().isSimulationRunning()) {
			pendulum.angle = simulation.getPsi1() - Math.PI / 2;
			VerticalOscillationSimulation sim = (VerticalOscillationSimulation) simulation;
			float dy = (float) (LENGTH_SCALE * sim.getDriveAmplitude() * -Math.cos(sim.getOmegaDrive() * sim.getT()));
			pendulum.pivot.y = (Demo.SIMULATION_WIDTH/2 + dy);
		}
		angularPlot.addData1((float) (simulation.getPsi1() % (2*Math.PI)));
		angularPlot.addData2((float) simulation.getOmega1());
		energyPlot.addData1((float) simulation.getEnergy1());
	}
}
