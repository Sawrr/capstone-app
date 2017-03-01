package com.sawyerharris.capstone.plot;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.sawyerharris.capstone.view.ShapeActor;

public class Plot extends ShapeActor {
	private static final int NUM_POINTS = 100;
	private static final float PLOT_WIDTH = 400;
	private float[] vertices;
	private float[] data;

	private float offset;
	private float scale;
	
	public Plot() {
		vertices = new float[2 * NUM_POINTS];
		data = new float[NUM_POINTS];
		for (int i = 0; i < NUM_POINTS; i++) {
			vertices[2 * i] = i * PLOT_WIDTH / NUM_POINTS;
		}
		
		scale = 1;
		
		setBounds(0, 0, PLOT_WIDTH, PLOT_WIDTH);
		
		addListener(new ActorGestureListener() {
			@Override
			public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
				offsetBy(deltaY);
			}
		});
	}

	public void enqueue(int value) {
		for (int i = 0; i < NUM_POINTS - 1; i++) {
			data[i] = data[i + 1];
		}
		data[NUM_POINTS - 1] = value;
	}
	
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public void offsetBy(float amount) {
		offset += amount;
	}

	@Override
	public void drawShapes() {
		for (int i = 0; i < NUM_POINTS; i++) {
			vertices[2*i + 1] = data[i]*scale + offset;
		}
		
		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.BLACK);
		
		float[] borders = {0, 0, PLOT_WIDTH, 0, PLOT_WIDTH, PLOT_WIDTH, 0, PLOT_WIDTH};
		renderer.polygon(borders);
		
		renderer.polyline(vertices);
		renderer.end();
	}

}
