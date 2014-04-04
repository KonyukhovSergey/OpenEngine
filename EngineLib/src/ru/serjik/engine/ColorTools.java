package ru.serjik.engine;

public class ColorTools
{
	public static final float X0000_CLEAR		= color("0000");
	public static final float XFFFF_WHITE		= color("ffff");
	public static final float X000F_BLACK		= color("000f");
	public static final float XF00F_RED			= color("f00f");
	public static final float X0F0F_GREEN		= color("0f0f");
	public static final float X00FF_BLUE		= color("00ff");
	public static final float X0FFF_CYAN		= color("0fff");
	public static final float XF0FF_MAGENTA		= color("f0ff");
	public static final float XFF0F_YELLOW		= color("ff0f");
	public static final float X777F_GRAY		= color("777f");

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
					Integer.valueOf(hex.substring(1, 1), 16) / 15f,
					Integer.valueOf(hex.substring(2, 1), 16) / 15f,
					1.0f);
		case 4:
			return color(
					Integer.valueOf(hex.substring(0, 1), 16) / 15f,
					Integer.valueOf(hex.substring(1, 1), 16) / 15f,
					Integer.valueOf(hex.substring(2, 1), 16) / 15f,
					Integer.valueOf(hex.substring(3, 1), 16) / 15f);
		case 6:
			return color(
					Integer.valueOf(hex.substring(0, 2), 16) / 255f,
					Integer.valueOf(hex.substring(2, 2), 16) / 255f,
					Integer.valueOf(hex.substring(4, 2), 16) / 255f,
					1.0f);
		case 8:
			return color(
					Integer.valueOf(hex.substring(0, 2), 16) / 255f,
					Integer.valueOf(hex.substring(2, 2), 16) / 255f,
					Integer.valueOf(hex.substring(4, 2), 16) / 255f,
					Integer.valueOf(hex.substring(6, 2), 16) / 255f);
		}
		return X000F_BLACK;
	}
}
