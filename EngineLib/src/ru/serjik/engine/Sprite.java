package ru.serjik.engine;

public class Sprite
{
	private float sx, sy, fx, fy, ox, oy;
	public float x1, y1, x2, y2, x3, y3, x4, y4;
	public Tile tile;

	public Sprite(Tile tile, float width, float height, float originX, float originY, float rotation)
	{
		this.tile = tile;
		updateMatrix(width, height, originX, originY, rotation);
	}

	public Sprite(Tile tile, float width, float height)
	{
		this(tile, width, height, width * 0.5f, height * 0.5f, 0);
	}
	
	private void updateMatrix(float width, float height, float originX, float originY, float rotation)
	{
		this.fx = (float) Math.cos(rotation);
		this.fy = (float) Math.sin(rotation);
		this.sx = width;
		this.sy = height;
		this.ox = originX;
		this.oy = originY;

		updateCornerCoordinates();
	}

	private void updateCornerCoordinates()
	{
		x1 = tx(-ox, -oy);
		y1 = ty(-ox, -oy);

		x2 = tx(sx - ox, -oy);
		y2 = ty(sx - ox, -oy);

		x3 = tx(sx - ox, sy - oy);
		y3 = ty(sx - ox, sy - oy);

		x4 = tx(-ox, sy - oy);
		y4 = ty(-ox, sy - oy);
	}

	private final float tx(float x, float y)
	{
		return fx * x - fy * y;
	}

	private final float ty(float x, float y)
	{
		return fy * x + fx * y;
	}

	public static Sprite[] wrap(Tile[] tiles, float width, float height)
	{
		Sprite[] sprites = new Sprite[tiles.length];

		for (int i = 0; i < tiles.length; i++)
		{
			sprites[i] = new Sprite(tiles[i], width, height);
		}

		return sprites;
	}

}
