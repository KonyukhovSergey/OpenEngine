package ru.engine.test;

import java.util.Random;

import ru.serjik.engine.ColorTools;

public class LampInfo
{
	public float x, y, color, size;
	public long stateTime = 0;
	public boolean isLighting = false;

	private final static Random rnd = new Random(System.currentTimeMillis());

	public LampInfo(String line)
	{
		String[] values = line.split(";");
		x = Integer.parseInt(values[0]);
		y = Integer.parseInt(values[1]);
		color = ColorTools.color(Integer.parseInt(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]),
				255);
		size = Integer.parseInt(values[5]);
		isLighting = rnd.nextBoolean();
		stateTime = stateTime();
	}

	private long stateTime()
	{
		return rnd.nextInt(5000);
	}

	public void tick(long dt)
	{
		stateTime -= dt;

		if (stateTime < 0)
		{
			isLighting = rnd.nextBoolean();
			stateTime = 5000 + stateTime();
		}
	}
}
