package com.sawyerharris.capstone.plot;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.sawyerharris.capstone.view.ShapeActor;

public class Plot extends ShapeActor {
	private static final int NUM_POINTS = 300;
	private static final float PLOT_WIDTH = 500;
	private float[] vertices1;
	private float[] data1;
	private float[] vertices2;
	private float[] data2;

	private float offset;
	private float scale;
	private boolean twoDataSets;
	
	public Plot(float scale, float offset, boolean twoDataSets) {
		this.scale = scale;
		this.offset = offset;
		this.twoDataSets = twoDataSets;
		
		vertices1 = new float[2 * NUM_POINTS];
		data1 = new float[NUM_POINTS];
		vertices2 = new float[2 * NUM_POINTS];
		data2 = new float[NUM_POINTS];
		for (int i = 0; i < NUM_POINTS; i++) {
			vertices1[2 * i] = i * PLOT_WIDTH / NUM_POINTS;
			vertices2[2 * i] = i * PLOT_WIDTH / NUM_POINTS;
		}
		
		setBounds(0, 0, PLOT_WIDTH, PLOT_WIDTH);
		
		addListener(new ActorGestureListener() {
			@Override
			public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
				offsetBy(deltaY);
			}
		});
	}

	public void addData1(float value) {
		for (int i = 0; i < NUM_POINTS - 1; i++) {
			data1[i] = data1[i + 1];
		}
		data1[NUM_POINTS - 1] = value;
	}
	
	public void addData2(float value) {
		for (int i = 0; i < NUM_POINTS - 1; i++) {
			data2[i] = data2[i + 1];
		}
		data2[NUM_POINTS - 1] = value;
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
			vertices1[2*i + 1] = data1[i] / scale * PLOT_WIDTH + offset + PLOT_WIDTH/2;
			vertices2[2*i + 1] = data2[i] / scale * PLOT_WIDTH + offset + PLOT_WIDTH/2;
		}
		
		clipBegin();
		renderer.begin(ShapeType.Line);
		
		renderer.setColor(Color.BLACK);
		float[] borders = {1, 1, PLOT_WIDTH, 1, PLOT_WIDTH, PLOT_WIDTH, 1, PLOT_WIDTH};
		renderer.polygon(borders);
		
		renderer.setColor(Color.RED);
		renderer.polyline(vertices1);
		
		if (twoDataSets) {
			renderer.setColor(Color.BLUE);
			renderer.polyline(vertices2);
		}
		
		renderer.end();
		clipEnd();
	}

}
