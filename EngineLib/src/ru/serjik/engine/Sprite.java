package ru.serjik.engine;

import js.math.Vector2D;

public class Sprite extends Location2D
{
	private float[] v = new float[12];
	private Tile tile;
	
	public Sprite(Tile tile)
	{
		this.tile=tile;
	}
	
	private void update()
	{	
		float w = tile.width;
		float h = tile.height;
		
		// x` = x * sin + y * cos;
		// y` = x * cos + y * sin;
		
		[0] = position.x -tile.ox + 
		
		
		
	}


}
