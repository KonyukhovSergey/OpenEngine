package ru.engine.test;

public class Interpolator
{
	private float start;
	private float scale;
	private float value;
	private Interpolable interpolator = LINEAR;

	public Interpolator(float start, float end, Interpolable interpolator)
	{
		setup(start, end, interpolator);
	}

	public void setup(float start, float end)
	{
		this.start = start;
		this.value = start;
		scale = end - start;
	}

	public void setup(float start, float end, Interpolable interpolator)
	{
		setup(start, end);
		this.interpolator = interpolator;
	}

	public final float start()
	{
		return start;
	}

	public final float end()
	{
		return start + scale;
	}

	public final float length()
	{
		return scale;
	}

	public final float position(float x)
	{
		value = interpolator.v(x) * scale + start;
		return value;
	}

	public final static Interpolable LINEAR = new Interpolable()
	{
		@Override
		public float v(float x)
		{
			return x;
		}
	};

	public final static Interpolable CUBIC = new Interpolable()
	{
		@Override
		public float v(float x)
		{
			return (3 - 2 * x) * x * x;
		}
	};

	public interface Interpolable
	{
		float v(float x);
	}

	public float value()
	{
		return value;
	}
}
