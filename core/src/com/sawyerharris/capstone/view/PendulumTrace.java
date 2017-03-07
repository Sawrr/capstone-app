package com.sawyerharris.capstone.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class PendulumTrace extends ShapeActor {
	private float[] vertices;
	private static final int CAPACITY = 4096;
	private int size;
	
	public PendulumTrace() {
		vertices = new float[CAPACITY];
		size = 0;
	}
	
	public void addVertex(float x, float y) {
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
		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.PURPLE);
		if (size > 3) renderer.polyline(vertices, 0, size);
		renderer.end();
	}

	public void reset() {
		size = 0;
	}

}
