package ru.engine.test;

import ru.serjik.engine.ColorTools;

public class LampInfo
{
	public float x, y, color, size;

	public LampInfo(String line)
	{
		String[] values = line.split(";");
		x = Integer.parseInt(values[0]);
		y = Integer.parseInt(values[1]);
		color = ColorTools.color(Integer.parseInt(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]),
				255);
		size = Integer.parseInt(values[5]);
	}
}
