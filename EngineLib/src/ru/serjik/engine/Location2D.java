package ru.serjik.engine;

import js.math.Vector2D;

public class Location2D
{
	private Vector2D position = new Vector2D(0, 0);
	private Vector2D forward = new Vector2D(1, 0);
	private float angle = 0;

	public final Vector2D position()
	{
		return position;
	}

	public final void position(Vector2D position)
	{
		this.position = position;
	}

	public final Vector2D forward()
	{
		return forward;
	}

	public final void forward(Vector2D forward)
	{
		forward.normalize();
		angle = forward.atan2();
	}

	public final float angle()
	{
		return angle;
	}

	public final void angle(float angle)
	{
		this.angle = angle;
		forward.x = (float) Math.cos(angle);
		forward.y = (float) Math.sin(angle);
	}

	public final void move(float forward, float strafe)
	{
		position.plus(this.forward, forward);
		position.plus(this.forward.y, -this.forward.x, strafe);
	}

	public final void rotate(float angle)
	{
		this.angle += angle;

		forward.x = (float) Math.cos(angle);
		forward.y = (float) Math.sin(angle);
	}
}
