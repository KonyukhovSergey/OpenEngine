package ru.serjik.utils;

import android.graphics.Bitmap;

public class AtlasGenerator
{
	public static Bitmap generate(int size, int tileSize, Bitmap[] tiles)
	{
		Bitmap texture = Bitmap.createBitmap(size, size, tiles[0].getConfig());
		
		return texture;
	}

}
