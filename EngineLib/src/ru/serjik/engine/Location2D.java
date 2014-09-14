package ru.serjik.engine;

import js.math.Vector2D;

public class Location2D
{
	protected Vector2D position = new Vector2D(0, 0);
	protected Vector2D forward = new Vector2D(1, 0);
	protected float angle = 0;

	public final Vector2D position()
	{
		return position;
	}

	public void position(Vector2D position)
	{
		this.position = position;
	}

	public final Vector2D forward()
	{
		return forward;
	}

	public void forward(Vector2D forward)
	{
		forward.normalize();
		angle = forward.atan2();
	}

	public final float angle()
	{
		return angle;
	}

	public void angle(float angle)
	{
		this.angle = angle;
		forward.set((float) Math.cos(angle), (float) Math.sin(angle));
	}

	public void move(float forward, float strafe)
	{
		position.plus(this.forward, forward);
		position.plus(this.forward.y, -this.forward.x, strafe);
	}

	public void rotate(float angle)
	{
		this.angle += angle;
		forward.x = (float) Math.cos(angle);
		forward.y = (float) Math.sin(angle);
	}
}
