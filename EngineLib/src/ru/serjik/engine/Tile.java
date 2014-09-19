package ru.serjik.engine;

public class Tile
{
	public Texture texture;
	public float u1, v1, u2, v2;
	public float ox, oy;
	public int width, height;

	public float color;

	public Tile()
	{

	}

	public Tile(Tile tile)
	{
		set(tile);
	}

	public Tile(Texture texture)
	{
		this.texture = texture;
		set(0, 0, 1, 1);
	}

	public Tile(Texture texture, int x, int y, int width, int height)
	{
		this.texture = texture;
		set(x, y, width, height);
	}

	public Tile(Texture texture, float u1, float v1, float u2, float v2)
	{
		this.texture = texture;
		set(u1, v1, u2, v2);
	}

	public void draw(BatchDrawer bd, float x1, float y1)
	{
		x1 -= ox;
		y1 -= oy;

		final float x2 = x1 + width;
		final float y2 = y1 + height;

		bd.draw(texture, x1, y1, u1, v1, x2, y1, u2, v1, x1, y2, u1, v2);
		bd.draw(texture, x2, y2, u2, v2, x1, y2, u1, v2, x2, y1, u2, v1);
	}

	public void drawScaled(BatchDrawer bd, float scale, float x1, float y1)
	{
		x1 -= ox * scale;
		y1 -= oy * scale;

		final float x2 = x1 + width * scale;
		final float y2 = y1 + height * scale;

		bd.draw(texture, x1, y1, u1, v1, x2, y1, u2, v1, x1, y2, u1, v2);
		bd.draw(texture, x2, y2, u2, v2, x1, y2, u1, v2, x2, y1, u2, v1);
	}

	public void drawColored(BatchDrawer bd, float color, float x1, float y1)
	{
		x1 -= ox;
		y1 -= oy;

		final float x2 = x1 + width;
		final float y2 = y1 + height;

		bd.draw(texture, color, x1, y1, u1, v1, x2, y1, u2, v1, x1, y2, u1, v2);
		bd.draw(texture, color, x2, y2, u2, v2, x1, y2, u1, v2, x2, y1, u2, v1);
	}

	public void drawScaledColored(BatchDrawer bd, float scale, float color, float x1, float y1)
	{
		x1 -= ox * scale;
		y1 -= oy * scale;

		final float x2 = x1 + width * scale;
		final float y2 = y1 + height * scale;

		bd.draw(texture, color, x1, y1, u1, v1, x2, y1, u2, v1, x1, y2, u1, v2);
		bd.draw(texture, color, x2, y2, u2, v2, x1, y2, u1, v2, x2, y1, u2, v1);
	}

	public void draw(BatchDrawer bd, float left, float top, float right, float bottom)
	{
		bd.draw(texture, left, top, u1, v1, right, top, u2, v1, left, bottom, u1, v2);
		bd.draw(texture, right, bottom, u2, v2, left, bottom, u1, v2, right, top, u2, v1);
	}

	public void draw(BatchDrawer bd, float[] v)
	{
		bd.draw(texture, v[0], v[1], u1, v1, v[2], v[3], u2, v1, v[4], v[5], u2, v2);
		bd.draw(texture, v[0], v[1], u1, v1, v[4], v[5], u2, v2, v[6], v[7], u1, v2);
	}

	public void origin(float ox, float oy)
	{
		this.ox = ox;
		this.oy = oy;
	}

	public void align(int i, int j)
	{
		if (i < 0)
		{
			ox = 0;
		}
		else if (i > 0)
		{
			ox = width;
		}
		else
		{
			ox = (float) width * 0.5f;
		}

		if (j < 0)
		{
			oy = 0;
		}
		else if (j > 0)
		{
			oy = height;

		}
		else
		{
			oy = (float) height * 0.5f;
		}
	}

	public void set(Tile tile)
	{
		texture = tile.texture;
		u1 = tile.u1;
		v1 = tile.v1;
		u2 = tile.u2;
		v2 = tile.v2;
		width = tile.width;
		height = tile.height;
		ox = tile.ox;
		oy = tile.oy;
	}

	public void set(int x, int y, int width, int height)
	{
		float itw = 1.0f / texture.width;
		float ith = 1.0f / texture.height;

		set(x * itw, y * ith, (x + width) * itw, (y + height) * ith);

		width = Math.abs(width);
		height = Math.abs(height);
	}

	public void set(float u1, float v1, float u2, float v2)
	{
		width = Math.round(Math.abs(u2 - u1) * texture.width);
		height = Math.round(Math.abs(v2 - v1) * texture.height);

		if (width == 1)
		{
			float ax = 0.25f / texture.width;
			u1 += ax;
			u2 -= ax;
		}

		if (height == 1)
		{
			float ay = 0.25f / texture.height;
			v1 += ay;
			v2 -= ay;
		}

		this.u1 = u1;
		this.v1 = v1;
		this.u2 = u2;
		this.v2 = v2;
	}

	public void flip(boolean x, boolean y)
	{
		float t;

		if (x)
		{
			t = u1;
			u1 = u2;
			u2 = t;
		}

		if (y)
		{
			t = v1;
			v1 = v2;
			v2 = t;
		}
	}

	public final boolean isFlipX()
	{
		return u1 > u2;
	}

	public final boolean isFlipY()
	{
		return v1 > v2;
	}

	public void scroll(float dx, float dy)
	{
		if (dx != 0)
		{
			float width = (u2 - u1) * texture.width;
			u1 = (u1 + dx) % 1;
			u2 = u1 + width / texture.width;
		}

		if (dy != 0)
		{
			float height = (v2 - v1) * texture.height;
			v1 = (v1 + dy) % 1;
			v2 = v1 + height / texture.height;
		}
	}

	public Tile[] split(int tileWidth, int tileHeight)
	{
		int x = Math.round(u1 * texture.width);
		int y = Math.round(v1 * texture.height);

		int rows = texture.height / tileHeight;
		int cols = texture.width / tileWidth;

		Tile[] tiles = new Tile[rows * cols];

		for (int i = 0; i < tiles.length; i++)
		{
			tiles[i] = new Tile(texture, x + (i % rows) * tileWidth, y + (i / rows) * tileHeight, tileWidth, tileHeight);
			tiles[i].align(0, 0);
		}

		return tiles;
	}

	public static Tile[] split(Texture texture, int tileWidth, int tileHeight)
	{
		Tile tile = new Tile(texture);
		return tile.split(tileWidth, tileHeight);
	}
}
