package com.sawyerharris.capstone.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.sawyerharris.capstone.demo.Demo;

public class Cart extends ShapeActor {
	private static final float RATIO = 0.3f;
	public float width;
	public float x;
	
	public Cart(float width) {
		super();
		this.width = width;
	}
	
	@Override
	public void drawShapes() {
		setBounds(x - width / 2, (Demo.SIMULATION_WIDTH - width * RATIO) / 2, width, width * RATIO);
		
		renderer.begin(ShapeType.Filled);
		renderer.setColor(Color.BLACK);
		renderer.rectLine(0, Demo.SIMULATION_WIDTH / 2, Demo.SIMULATION_WIDTH, Demo.SIMULATION_WIDTH / 2, 2);
		
		renderer.setColor(Color.RED);
		renderer.rect(x - width / 2, (Demo.SIMULATION_WIDTH - width * RATIO) / 2, width, width * RATIO);
		renderer.end();
	}

}
