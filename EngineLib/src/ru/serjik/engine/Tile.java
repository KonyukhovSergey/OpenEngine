package ru.serjik.engine;

public class Tile
{
	public Texture texture;
	public float u1, v1, u2, v2;
	public int width, height;

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
		this(texture);
		set(x, y, width, height);
	}

	public Tile(Texture texture, float u1, float v1, float u2, float v2)
	{
		this(texture);
		set(u1, v1, u2, v2);
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

	public boolean isFlipX()
	{
		return u1 > u2;
	}

	public boolean isFlipY()
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
		}

		return tiles;
	}

	public static Tile[] split(Texture texture, int tileWidth, int tileHeight)
	{
		Tile tile = new Tile(texture);
		return tile.split(tileWidth, tileHeight);
	}
}
