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
import com.sawyerharris.capstone.simulation.PendulumCartSimulation;
import com.sawyerharris.capstone.view.Cart;
import com.sawyerharris.capstone.view.Pendulum;

public class CartDemo extends Demo {	
	private static final float CART_MASS_SCALE = 10f;
	private static final float CART_MASS_BASE = 50f;
	
	private float defGravity = 9.8f;
	private float defLength = 3;
	private float defPsi1 = (float) Math.PI + .1f;
	private float defOmega1 = 0;
	private float defMass = 2;
	private float defCartMass = 10f;
	private float defCartX = (Demo.SIMULATION_WIDTH / LENGTH_SCALE) / 2;
	private float defCartV = 0;
	
	private Pendulum pendulum;
	private Cart cart;
	
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
	private TextButton resetButton;
	private Slider cartMassSlider;
	private Label cartMassLabel;
	private Label cartMassValue;
	
	public CartDemo(PendulumCartSimulation sim) {
		////////////////
		// SIMULATION //
		////////////////
		
		simulation = sim;
		simulation.setParameter("gravity", defGravity);
		simulation.setParameter("length1", defLength);
		simulation.setParameter("psi1", defPsi1);
		simulation.setParameter("omega1", defOmega1);
		simulation.setParameter("mass1", defMass);
		simulation.setParameter("cartMass", defCartMass);
		simulation.setParameter("cartX", defCartX);
		simulation.setParameter("cartV", defCartV);
		
		simulationWindow = new Group();
		simulationWindow.setBounds(50, 50, Demo.SIMULATION_WIDTH, Demo.SIMULATION_WIDTH);
		
		pendulum = new Pendulum(new Vector2(Demo.SIMULATION_WIDTH/2, Demo.SIMULATION_WIDTH/2), 0, defLength * LENGTH_SCALE, MASS_BASE + defMass * MASS_SCALE);
		cart = new Cart(CART_MASS_BASE + defCartMass * CART_MASS_SCALE);
		cart.addListener(new ActorGestureListener() {
			@Override
			public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
				System.out.println(x);
				float force = Math.max(Math.abs(x), 20);
				if (x < 0) force *= -1;
				simulation.setParameter("forceX", force);
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				simulation.setParameter("forceX", 0);
			}
		});
		
		simulationWindow.addActor(cart);
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

		interfaceTable.add(gravityLabel);
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
		
		interfaceTable.add(lengthLabel);
		interfaceTable.add(lengthSlider);
		interfaceTable.add(lengthValue);
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
		
		interfaceTable.add(massLabel);
		interfaceTable.add(massSlider);
		interfaceTable.add(massValue);
		interfaceTable.row();
		
		// Cart Mass parameter
		cartMassSlider = new Slider(1, 10, 1f, false, skin);
		cartMassSlider.setValue(defCartMass);
		cartMassSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				float value = ((Slider) actor).getValue();
				simulation.setParameter("cartMass", value);
				cartMassValue.setText(String.format("%.0f", value));
				cart.width = CART_MASS_BASE + value * CART_MASS_SCALE;
			}
		});
		cartMassLabel = new Label("Cart Mass", skin);
		cartMassValue = new Label(String.format("%.0f", defCartMass), skin);
		
		interfaceTable.add(cartMassLabel);
		interfaceTable.add(cartMassSlider);
		interfaceTable.add(cartMassValue);
		interfaceTable.row();
		
		resetButton = new TextButton("Reset", skin);
		resetButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				reset();
			}
		});

		interfaceTable.add(resetButton);
		
		interfaceWindow.setBounds(0, 0, Demo.INTERFACE_WIDTH, Demo.INTERFACE_WIDTH);
		interfaceWindow.addActor(interfaceTable);
		
		//////////////
		// PLOTTING //
		//////////////
		
		plotWindow = new Group();
		plotWindow.setBounds(0, 0, Demo.PLOT_WIDTH, Demo.PLOT_WIDTH);
		angularPlot = new LinePlot((float) Math.PI * 6, 0, true);
		
		plotTable = new Table();
		plotTable.setBounds(0, LinePlot.PLOT_SIZE, LinePlot.PLOT_SIZE, 50);
		
		angularButton = new TextButton("Angle + Angular velocity", skin);
		
		plotTable.add(angularButton);
		
		plotWindow.addActor(angularPlot);
		plotWindow.addActor(plotTable);
	}
	
	public void update() {
		if (PendulumApplication.getInstance().isSimulationRunning()) {
			PendulumCartSimulation sim = (PendulumCartSimulation) simulation;
			pendulum.angle = sim.getPsi1() - Math.PI / 2;
			cart.x = (float) sim.getCartX() * LENGTH_SCALE;
			pendulum.pivot.x = cart.x;
		}
		angularPlot.addData1((float) (simulation.getPsi1() % (2*Math.PI)));
		angularPlot.addData2((float) simulation.getOmega1());
	}
	
	public void reset() {
		simulation.setParameter("psi1", defPsi1);
		simulation.setParameter("omega1", defOmega1);
		simulation.setParameter("cartX", defCartX);
		simulation.setParameter("cartV", defCartV);
	}
}
