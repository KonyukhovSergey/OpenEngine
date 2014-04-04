package ru.serjik.utils;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;

public class BitmapUtils
{
	public static Bitmap generate(int size, int tileSize, Bitmap[] tiles)
	{
		Bitmap texture = Bitmap.createBitmap(size, size, tiles[0].getConfig());

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
}
