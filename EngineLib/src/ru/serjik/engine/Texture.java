package ru.serjik.engine;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

public class Texture implements Disposable
{
	private static int current = 0;

	private int id;
	public int width;
	public int height;

	public Texture(Bitmap bitmap)
	{
		id = create();

		bind();

		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		width = bitmap.getWidth();
		height = bitmap.getHeight();

		unbind();
	}

	public static void disable()
	{
		eng.gl.glDisable(GL10.GL_TEXTURE_2D);
	}

	public static void enable()
	{
		eng.gl.glEnable(GL10.GL_TEXTURE_2D);
	}

	/**
	 * @param min
	 * <br>
	 *            GL_NEAREST<br>
	 *            GL_LINEAR<br>
	 *            GL_NEAREST_MIPMAP_NEAREST<br>
	 *            GL_LINEAR_MIPMAP_NEAREST<br>
	 *            GL_NEAREST_MIPMAP_LINEAR<br>
	 *            GL_LINEAR_MIPMAP_LINEAR<br>
	 * <br>
	 * @param mag
	 * <br>
	 *            GL_NEAREST<br>
	 *            GL_LINEAR<br>
	 */
	public static void filter(int min, int mag)
	{
		eng.gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, min);
		eng.gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, mag);
	}

	public static void unbind()
	{
		if (current > 0)
		{
			eng.gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
		}

		current = 0;
	}

	/**
	 * values can be: GL_CLAMP_TO_EDGE, GL_MIRRORED_REPEAT, or GL_REPEAT.
	 * 
	 * @param s
	 * @param t
	 */
	public static void wrap(int s, int t)
	{
		eng.gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, s);
		eng.gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, t);
	}

	public void bind()
	{
		if (current != id)
		{
			unbind();
		}

		eng.gl.glBindTexture(GL10.GL_TEXTURE_2D, id);
		current = id;
	}

	@Override
	public void dispose()
	{
		unbind();

		if (id > 0)
		{
			int[] ids = new int[1];

			ids[0] = id;

			eng.gl.glDeleteTextures(1, ids, 0);

			id = 0;
		}
	}

	public int id()
	{
		return id;
	}

	private int create()
	{
		unbind();

		int[] ids = new int[1];

		eng.gl.glGenTextures(1, ids, 0);

		return ids[0];
	}

}
