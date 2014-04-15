package ru.serjik.engine;

public class Sprite extends Tile
{
	public float originX, originY;

	public Sprite(Tile tile)
	{
		super(tile);
		
		originX = tile.width / 2.0f;
		originY = tile.height / 2.0f;
	}
	
	public static Sprite[] wrap(Tile[] tiles)
	{
		Sprite[] sprites = new Sprite[tiles.length];
		
		for(int i = 0;i<tiles.length;i++)
		{
			sprites[i] = new Sprite(tiles[i]);
		}
		
		return sprites;
	}

}
