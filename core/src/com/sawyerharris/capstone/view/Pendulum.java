package com.sawyerharris.capstone.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Pendulum extends ShapeActor {
	public static final float TOUCH_RADIUS = 50;
	
	private static final float ROD_WIDTH = 6;
	private static final float PIVOT_RADIUS = 8;
	
	public Vector2 pivot;
	public double angle;
	public double length;
	public float radius;
	
	public Pendulum(Vector2 pivot, double angle, double length, float radius) {
		super();
		this.pivot = pivot;
		this.angle = angle;
		this.length = length;
		this.radius = radius;
	}
	
	public double getLengthX() {
		return length * Math.cos(angle);
	}
	
	public double getLengthY() {
		return length * Math.sin(angle);
	}
	
	@Override
	public void drawShapes() {	
		float x1, y1, x2, y2;
		x1 = pivot.x;
		y1 = pivot.y;
		x2 = (float) (x1 + length * Math.cos(angle));
		y2 = (float) (y1 + length * Math.sin(angle));
		Vector2 endPoint = new Vector2(x2, y2);
		
		setBounds(endPoint.x - TOUCH_RADIUS, endPoint.y - TOUCH_RADIUS, 2 * TOUCH_RADIUS, 2 * TOUCH_RADIUS);
		
		// Circles and rod
		renderer.begin(ShapeType.Filled);
		renderer.setColor(Color.BLACK);
		renderer.rectLine(pivot, endPoint, ROD_WIDTH);
		renderer.setColor(Color.BLACK);
		renderer.circle(x1, y1, PIVOT_RADIUS);
		renderer.setColor(Color.NAVY);
		renderer.circle(x2, y2, radius);
		renderer.end();

		// Outline
		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.BLACK);
		renderer.circle(x1, y1, PIVOT_RADIUS);
		renderer.circle(x2, y2, radius);
		renderer.end();
	}

}
