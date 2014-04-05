package ru.serjik.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class BitmapUtils
{
	public static Bitmap generate(int size, int tileSize, Bitmap[] tiles, boolean recycleTiles)
	{
		Bitmap texture = Bitmap.createBitmap(size, size, Config.ARGB_8888);
		Canvas canvas = new Canvas(texture);

		int x = 0;
		int y = 0;

		for (Bitmap tile : tiles)
		{
			Rect rect = new Rect(x, y, x + tileSize, y + tileSize);
			canvas.drawBitmap(tile, null, rect, null);

			if (recycleTiles)
			{
				tile.recycle();
			}

			x += tileSize;

			if (x >= size)
			{
				y += tileSize;
				x = 0;
			}
		}

		return texture;
	}

	public static Bitmap loadBitmapFromAsset(AssetManager am, String fileName)
	{
		try
		{
			InputStream is = am.open(fileName);
			Bitmap bmp = BitmapFactory.decodeStream(is);
			is.close();
			return bmp;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			int c = 0x77777777;
			int[] colors = { c, 0, c, 0, 0, c, 0, c, c, 0, c, 0, 0, c, 0, c };
			return Bitmap.createBitmap(colors, 4, 4, Config.ARGB_8888);
		}
	}

	public static Bitmap[] loadBitmapsFromAsset(AssetManager am)
	{
		List<Bitmap> bitmaps = new ArrayList<Bitmap>();

		try
		{
			for (String fileName : am.list(""))
			{
				if (fileName.endsWith(".png"))
				{
					bitmaps.add(loadBitmapFromAsset(am, fileName));
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		Bitmap[] array = new Bitmap[bitmaps.size()];
		bitmaps.toArray(array);
		return array;
	}
}
