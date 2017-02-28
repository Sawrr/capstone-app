package com.sawyerharris.capstone.plot;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.sawyerharris.capstone.view.ShapeActor;

public class Plot extends ShapeActor {
	private static final int NUM_POINTS = 100;
	private static final float PLOT_WIDTH = 400;
	private float[] vertices;

	public Plot() {
		vertices = new float[2 * NUM_POINTS];
		for (int i = 0; i < NUM_POINTS; i++) {
			vertices[2 * i] = i * PLOT_WIDTH / NUM_POINTS;
		}
	}

	public void enqueue(int value) {
		for (int i = 1; i < NUM_POINTS; i++) {
			vertices[2 * i - 1] = vertices[2 * i + 1];
		}
		vertices[2 * NUM_POINTS - 1] = value + PLOT_WIDTH / 2;
	}

	@Override
	public void drawShapes() {
		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.BLACK);
		
		float[] borders = {0, 0, PLOT_WIDTH, 0, PLOT_WIDTH, PLOT_WIDTH, 0, PLOT_WIDTH};
		renderer.polygon(borders);
		
		renderer.polyline(vertices);
		renderer.end();
	}

}
