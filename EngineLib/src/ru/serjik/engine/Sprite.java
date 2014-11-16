package ru.serjik.engine;

import js.math.Vector2D;

public class Sprite extends Location2D
{
	private float[] v = new float[8];
	private TileBase tile;

	public Sprite(TileBase tile)
	{
		this.tile = tile;
		update();
	}

	private final void setup(float x, float y, int i)
	{
		// x` = x * cos - y * sin;
		// y` = x * sin + y * cos;
		// cos == fwd.x
		// sin == fwd.y

		x *= scale;
		y *= scale;

		v[i + 0] = x * forward.x - y * forward.y + position.x;
		v[i + 1] = x * forward.y + y * forward.x + position.y;
	}

	private void update()
	{
		setup(-tile.ox, -tile.oy, 0);
		setup(tile.width - tile.ox, -tile.oy, 2);
		setup(tile.width - tile.ox, tile.height - tile.oy, 4);
		setup(-tile.ox, tile.height - tile.oy, 6);
	}

	public void draw(BatchDrawer bd)
	{
		tile.draw(bd, v);
	}

	public void draw(BatchDrawer bd, float color)
	{
		tile.draw(bd, v, color);
	}

	@Override
	public void angle(float angle)
	{
		super.angle(angle);
		update();
	}

	public void scale(float scale)
	{
		super.scale(scale);
		update();
	}

	@Override
	public void rotate(float angle)
	{
		super.rotate(angle);
		update();
	}

	@Override
	public void forward(Vector2D forward)
	{
		super.forward(forward);
		update();
	}

	public void position(float x, float y)
	{
		translate(x - position.x, y - position.y);
		position.set(x, y);
	}

	public float height()
	{
		return tile.height;
	}

	@Override
	public void move(float forward, float strafe)
	{
		float dx = this.forward.x * forward + this.forward.y * strafe;
		float dy = this.forward.y * forward - this.forward.x * strafe;

		translate(dx, dy);
		position.plus(dx, dy);
	}

	private void translate(float dx, float dy)
	{
		v[0] += dx;
		v[1] += dy;
		v[2] += dx;
		v[3] += dy;
		v[4] += dx;
		v[5] += dy;
		v[6] += dx;
		v[7] += dy;
	}
}
