package com.sawyerharris.capstone;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Spring extends Actor {
	private static final int NUM_INNER_POINTS = 15;
	private static final int NUM_ANGLE_POINTS = NUM_INNER_POINTS - 1;
	private static final float END_LENGTH = 10;
	private static final float ZIG_WIDTH = 30;
	private static final float WIDTH = 2;
	
	private Vector2 endPoint1, endPoint2;
	private float k;
	private Vector2[] innerPoints;
	private Vector2[] anglePoints;
	
	private ShapeRenderer shapeRenderer;
	
	public Spring(Vector2 endPoint1, Vector2 endPoint2, float k) {
		this.endPoint1 = endPoint1;
		this.endPoint2 = endPoint2;
		this.k = k;
		
		innerPoints = new Vector2[NUM_INNER_POINTS];
		anglePoints = new Vector2[NUM_ANGLE_POINTS];
		
		shapeRenderer = new ShapeRenderer();
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		Vector2 endToEnd = new Vector2(endPoint2).sub(endPoint1);
		Vector2 buffer = new Vector2(endToEnd).nor().scl(END_LENGTH);
		Vector2 inner = new Vector2(endToEnd).sub(buffer).sub(buffer).scl(1 / (float) NUM_ANGLE_POINTS);
		
		Vector2 perpZig = new Vector2(endToEnd).nor().rotate90(1).scl(ZIG_WIDTH);
		Vector2 perpZag = new Vector2(endToEnd).nor().rotate90(-1).scl(ZIG_WIDTH);
		
		innerPoints[0] = new Vector2(endPoint1).add(buffer);
		for (int i = 1; i < NUM_INNER_POINTS; i++) {
			innerPoints[i] = new Vector2(innerPoints[i-1]).add(inner);
		}
		
		for (int i = 0; i < NUM_ANGLE_POINTS; i++) {
			Vector2 point = new Vector2(innerPoints[i+1]).add(innerPoints[i]).scl(0.5f);
			if (i % 2 == 0) {
				point.add(perpZig);
			} else {
				point.add(perpZag);
			}
			anglePoints[i] = point;
		}
		
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.rectLine(endPoint1, innerPoints[0], WIDTH);
		for (int i = 0; i < NUM_ANGLE_POINTS; i++) {
			shapeRenderer.rectLine(innerPoints[i], anglePoints[i], WIDTH);
			shapeRenderer.rectLine(innerPoints[i+1], anglePoints[i], WIDTH);
		}
		shapeRenderer.rectLine(innerPoints[NUM_INNER_POINTS - 1], endPoint2, WIDTH);
		shapeRenderer.end();
	}
}
