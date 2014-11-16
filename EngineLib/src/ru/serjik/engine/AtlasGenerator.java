package ru.serjik.engine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;

public class AtlasGenerator
{
	private Bitmap texture;
	private Canvas canvas;
	private int xpos = 0;
	private int ypos = 0;
	private int maxh = 0;
	private int size;

	public AtlasGenerator(int size)
	{
		this.size = size;
		texture = Bitmap.createBitmap(size, size, Config.ARGB_8888);
		canvas = new Canvas(texture);
	}

	public TileBase tile(Bitmap bitmap)
	{
		if (bitmap.getWidth() + xpos > size)
		{
			xpos = 0;
			ypos += maxh;
			maxh = 0;
		}

		canvas.drawBitmap(bitmap, xpos, ypos, null);

		TileBase tile = new TileBase(xpos, ypos, bitmap.getWidth(), bitmap.getHeight(), size);

		if (bitmap.getHeight() > maxh)
		{
			maxh = bitmap.getHeight();
		}

		xpos += bitmap.getWidth();

		return tile;
	}

	public Bitmap atlas()
	{
		return texture;
	}
}
