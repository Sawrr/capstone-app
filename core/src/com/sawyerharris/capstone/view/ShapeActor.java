package com.sawyerharris.capstone.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class ShapeActor extends Actor {
	protected ShapeRenderer renderer;
	
	public ShapeActor() {
		super();
		renderer = new ShapeRenderer();
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		batch.end();
		renderer.setProjectionMatrix(batch.getProjectionMatrix());
		renderer.setTransformMatrix(batch.getTransformMatrix());
		drawShapes();
		batch.begin();
	}

	public abstract void drawShapes();
}
