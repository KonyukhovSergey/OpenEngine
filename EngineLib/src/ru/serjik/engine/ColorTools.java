package ru.serjik.engine;

public class ColorTools
{
	public static final float CLEAR_X0000		= color("0000");
	public static final float WHITE_XFFFF		= color("ffff");
	public static final float BLACK_X000F		= color("000f");
	public static final float RED_XF00F			= color("f00f");
	public static final float GREEN_X0F0F		= color("0f0f");
	public static final float BLUE_X00FF		= color("00ff");
	public static final float CYAN_X0FFF		= color("0fff");
	public static final float MAGENTA_X0FFF		= color("f0ff");
	public static final float YELLOW_XFF0F		= color("ff0f");
	public static final float GRAY_X777F		= color("777f");

	public static final float clamp(float value)
	{
		if (value < 0)
		{
			return 0;
		}
		else if (value > 1)
		{
			return 1;
		}
		
		return value;
	}

	public static final float color(int r, int g, int b, int a)
	{
		return Float.intBitsToFloat((a << 24) | (b << 16) | (g << 8) | r);
	}

	public static final float color(float r, float g, float b, float a)
	{
		return color((int) (clamp(r) * 255), (int) (clamp(g) * 255), (int) (clamp(b) * 255), (int) (clamp(a) * 255));
	}

	public static final float color(String hex)
	{
		switch (hex.length())
		{
		case 3:
			return color(
					Integer.valueOf(hex.substring(0, 1), 16) / 15f,
					Integer.valueOf(hex.substring(1, 2), 16) / 15f,
					Integer.valueOf(hex.substring(2, 3), 16) / 15f,
					1.0f);
		case 4:
			return color(
					Integer.valueOf(hex.substring(0, 1), 16) / 15f,
					Integer.valueOf(hex.substring(1, 2), 16) / 15f,
					Integer.valueOf(hex.substring(2, 3), 16) / 15f,
					Integer.valueOf(hex.substring(3, 4), 16) / 15f);
		case 6:
			return color(
					Integer.valueOf(hex.substring(0, 2), 16) / 255f,
					Integer.valueOf(hex.substring(2, 4), 16) / 255f,
					Integer.valueOf(hex.substring(4, 6), 16) / 255f,
					1.0f);
		case 8:
			return color(
					Integer.valueOf(hex.substring(0, 2), 16) / 255f,
					Integer.valueOf(hex.substring(2, 4), 16) / 255f,
					Integer.valueOf(hex.substring(4, 6), 16) / 255f,
					Integer.valueOf(hex.substring(6, 8), 16) / 255f);
		}
		return BLACK_X000F;
	}
}
