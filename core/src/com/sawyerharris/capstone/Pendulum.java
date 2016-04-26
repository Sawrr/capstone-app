package com.sawyerharris.capstone;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Pendulum extends Actor {
	private static final float ROD_WIDTH = 10;
	private static final float PIVOT_RADIUS = 20;
	private static final float MASS_RADIUS = 20;
	private static final double DEGREES_TO_RADIANS = Math.PI / 180;
	
	private Vector2 pivotPoint;
	private float angle, length;
	private Color color;
	
	private ShapeRenderer shapeRenderer;
	
	public Pendulum(Vector2 pivotPoint, float angle, float length, Color color) {
		this.pivotPoint = pivotPoint;
		this.angle = angle;
		this.length = length;
		this.color = color;
		
		shapeRenderer = new ShapeRenderer();
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		float x1, y1, x2, y2;
		x1 = pivotPoint.x;
		y1 = pivotPoint.y;
		x2 = (float) (x1 + length * Math.cos((double) angle * DEGREES_TO_RADIANS));
		y2 = (float) (y1 + length * Math.sin((double) angle * DEGREES_TO_RADIANS));
		Vector2 endPoint = new Vector2(x2, y2);
		
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.rectLine(pivotPoint, endPoint, ROD_WIDTH);
		shapeRenderer.setColor(Color.NAVY);
		shapeRenderer.circle(x1, y1, PIVOT_RADIUS);
		shapeRenderer.setColor(color);
		shapeRenderer.circle(x2, y2, MASS_RADIUS);
		shapeRenderer.end();
		
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.circle(x1, y1, PIVOT_RADIUS);
		shapeRenderer.circle(x2, y2, MASS_RADIUS);
		shapeRenderer.end();
	}
}
