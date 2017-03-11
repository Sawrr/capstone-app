package com.sawyerharris.capstone.plot;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.sawyerharris.capstone.view.ShapeActor;

public class PhasePlot extends ShapeActor {
	public static final float PLOT_SIZE = 250;
	private static final int CAPACITY = 4096;
	private static final float XSCALE = PLOT_SIZE / (4 * (float) Math.PI);
	private static final float YSCALE = PLOT_SIZE / (8 * (float) Math.PI);
	
	private float[] vertices;
	private int size;	
	
	public PhasePlot() {	
		vertices = new float[CAPACITY];
		size = 0;
		
		setBounds(0, 0, PLOT_SIZE, PLOT_SIZE);
	}

	public void addData(float x, float y) {
		x *= XSCALE;
		y *= YSCALE;
		x += PLOT_SIZE / 2;
		y += PLOT_SIZE / 2;
		if (size + 2 >= CAPACITY) {
			for (int i = 0; i < size - 3; i += 2) {
				vertices[i] = vertices[i + 2];
				vertices[i + 1] = vertices[i + 3];
			}
			vertices[size - 2] = x;
			vertices[size - 1] = y;
		} else {
			vertices[size] = x;
			vertices[size + 1] = y;
			size += 2;	
		}
	}

	@Override
	public void drawShapes() {	
		//clipBegin();
		renderer.begin(ShapeType.Line);
		
		renderer.setColor(Color.BLACK);
		float[] borders = {1, 1, PLOT_SIZE, 1, PLOT_SIZE, PLOT_SIZE, 1, PLOT_SIZE};
		renderer.polygon(borders);
		
		renderer.setColor(Color.RED);
		if (size > 3) {
			//renderer.polyline(vertices, 0, size);
			for (int i = 0; i < size; i += 2) {
				renderer.point(vertices[i], vertices[i+1], 0);
			}
		}
		
		renderer.end();
		//clipEnd();
	}
}
