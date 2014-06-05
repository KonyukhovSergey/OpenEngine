package ru.engine.test;

import javax.microedition.khronos.opengles.GL10;

public class HexLocation
{
	private float x = 0, y = 0, angle = 0;
	private int r = 0, q = 0, direction = 0;

	private float sx, sy;
	private float dx, dy;
	private int dr, dq;

	private static final float R = (float) (2 * Math.sqrt(3) / 2);

	private LocationState state = LocationState.NONE;

	private float actionCompletion = 0;
	private float actionStart = 0;
	private float actionDelta = 0;

	private float animate(float x)
	{
		return (3 - 2 * x) * x * x;
	}

	public void setupView(GL10 gl)
	{
		gl.glTranslatef(x, -0.5f, - 1);
		gl.glRotatef(angle+30, 0, 1, 0);
		gl.glTranslatef(x,0, y);
	}

	public void tick()
	{
		if (state == LocationState.ROTATE)
		{
			actionCompletion += 0.05;

			if (actionCompletion < 1)
			{
				angle = actionStart + animate(actionCompletion) * actionDelta;
			}
			else
			{
				angle = actionStart + actionDelta;
				state = LocationState.NONE;
			}
		}
		else if (state == LocationState.MOVE)
		{
			actionCompletion += 0.05;

			if (actionCompletion < 1)
			{
				float v = animate(actionCompletion);
				x = sx + v * dx;
				y = sy + v * dy;
			}
			else
			{
				x = sx + dx;
				y = sy + dy;
				state = LocationState.NONE;
			}

		}
	}

	public void rotateRight()
	{
		if (state == LocationState.NONE)
		{
			state = LocationState.ROTATE;
			actionCompletion = 0;
			actionStart = angle;
			actionDelta = 60;
			direction = (direction + 1 + 6) % 6;

		}
	}

	public void rotateLeft()
	{
		if (state == LocationState.NONE)
		{
			state = LocationState.ROTATE;
			actionCompletion = 0;
			actionStart = angle;
			actionDelta = -60;
			direction = (direction - 1 + 6) % 6;
		}
	}

	public void moveForward()
	{
		if (state == LocationState.NONE)
		{
			state = LocationState.MOVE;
			actionCompletion = 0;
			sx = x;
			sy = y;
			dx = -(float) Math.cos(3.1415962 * angle / 180.0) * R;
			dy = -(float) Math.sin(3.1415962 * angle / 180.0) * R;
		}
	}

	public void moveBackward()
	{
		if (state == LocationState.NONE)
		{
			state = LocationState.MOVE;
			actionCompletion = 0;
			sx = x;
			sy = y;
			dx = (float) Math.cos(3.1415962 * angle / 180.0) * R;
			dy = (float) Math.sin(3.1415962 * angle / 180.0) * R;
		}
	}

	private enum LocationState
	{
		NONE, ROTATE, MOVE
	}
}
