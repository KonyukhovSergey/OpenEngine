package ru.engine.test;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

public class HexLocation
{
	private static final float SPEED = 0.05f;

	public int q = 0, r = 0, dir = 0;

	private Interpolator x = new Interpolator(0, 0, Interpolator.CUBIC);
	private Interpolator y = new Interpolator(0, 0, Interpolator.CUBIC);
	private Interpolator a = new Interpolator(120, 0, Interpolator.CUBIC);

	private LocationState state = LocationState.NONE;
	private float actionCompletion = 0;

	public HexLocation(int i, int j)
	{
		q = i;
		r = j;
		x.setup(HexUtils.x(q, r) ,0);
		y.setup(HexUtils.y(r), 0);
	}

	public void setupView(GL10 gl)
	{
		float dx = (float) Math.cos(3.1415962f * a.value() / 180.0) * HexUtils.DELTA_WIDTH;
		float dy = (float) Math.sin(3.1415962f * a.value() / 180.0) * HexUtils.DELTA_WIDTH;

		GLU.gluLookAt(gl, x.value() + dx, 0.5f, y.value() + dy, x.value(), 0.5f, y.value(), 0, 1, 0);
	}

	public void tick()
	{
		if (state == LocationState.ROTATE)
		{
			actionCompletion += SPEED;

			if (actionCompletion < 1)
			{
				a.position(actionCompletion);
			}
			else
			{
				a.setup(a.end(), 0);
				state = LocationState.NONE;
			}
		}
		else if (state == LocationState.MOVE)
		{
			actionCompletion += SPEED;

			if (actionCompletion < 1)
			{
				x.position(actionCompletion);
				y.position(actionCompletion);
			}
			else
			{
				x.setup(x.end(), 0);
				y.setup(y.end(), 0);
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
			a.setup(HexUtils.angle[dir], HexUtils.angle[dir] + 60);
			dir = (dir + 1 + 6) % 6;
		}
	}

	public void rotateLeft()
	{
		if (state == LocationState.NONE)
		{
			state = LocationState.ROTATE;
			actionCompletion = 0;
			a.setup(HexUtils.angle[dir], HexUtils.angle[dir] - 60);
			dir = (dir - 1 + 6) % 6;
		}
	}

	public void moveForward()
	{
		if (state == LocationState.NONE)
		{
			state = LocationState.MOVE;
			actionCompletion = 0;

			x.setup(HexUtils.x(q, r), HexUtils.x(q + HexUtils.dq[dir], r + HexUtils.dr[dir]));
			y.setup(HexUtils.y(r), HexUtils.y(r + HexUtils.dr[dir]));

			q += HexUtils.dq[dir];
			r += HexUtils.dr[dir];
		}
	}

	public void moveBackward()
	{
		if (state == LocationState.NONE)
		{
			state = LocationState.MOVE;
			actionCompletion = 0;
			x.setup(HexUtils.x(q, r), HexUtils.x(q - HexUtils.dq[dir], r - HexUtils.dr[dir]));
			y.setup(HexUtils.y(r), HexUtils.y(r - HexUtils.dr[dir]));
			q -= HexUtils.dq[dir];
			r -= HexUtils.dr[dir];
		}
	}

	private enum LocationState
	{
		NONE, ROTATE, MOVE
	}
}
