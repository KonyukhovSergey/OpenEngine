package ru.serjik.engine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class BatchDrawer2
{
	private float[] data;
	private FloatBuffer bb;

	private Disposable texture = null;

	private boolean blendingEnabled = false;
	private int blendingSrc = 0;
	private int blendingDst = 0;

	private boolean colorEnabled = false;

	private int size = 0;

	public BatchDrawer2(int bufferSize)
	{
		data = new float[bufferSize];
		bb = ByteBuffer.allocateDirect(bufferSize * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
	}

	public void blending(boolean isBlendingEnabled)
	{
		if (this.blendingEnabled != isBlendingEnabled)
		{
			flush();

			this.blendingEnabled = isBlendingEnabled;

			if (blendingEnabled)
			{
				eng.gl.glEnable(GL10.GL_BLEND);
			}
			else
			{
				eng.gl.glDisable(GL10.GL_BLEND);
			}
		}
	}

	public void blending(int src, int dst)
	{
		if (blendingSrc != src || blendingDst != dst)
		{
			if (blendingEnabled)
			{
				flush();
				eng.gl.glBlendFunc(src, dst);
			}
			blendingDst = dst;
			blendingSrc = src;
		}
	}

	public void texture(Texture texture)
	{
		if (this.texture != texture)
		{
			flush();

			this.texture = texture;

			if (texture != null)
			{
				texture.bind();
			}
		}
	}

	public void color(boolean enabled)
	{
		if (colorEnabled != enabled)
		{
			flush();
			colorEnabled = enabled;
		}
	}

	public void flush()
	{
		if (size > 0)
		{
			bb.position(0);
			bb.put(data, 0, size);

			int vertexSize = 2 + (texture != null ? 2 : 0) + (colorEnabled ? 1 : 0);

			bb.position(0);
			eng.gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			eng.gl.glVertexPointer(2, GL10.GL_FLOAT, vertexSize * 4, bb);

			int offset = 2;

			if (texture != null)
			{
				bb.position(offset);
				eng.gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
				eng.gl.glTexCoordPointer(2, GL10.GL_FLOAT, vertexSize * 4, bb);
				offset += 2;
			}
			else
			{
				eng.gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			}

			if (colorEnabled)
			{
				bb.position(offset);
				eng.gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
				eng.gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, vertexSize * 4, bb);
			}
			else
			{
				eng.gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
			}

			eng.gl.glDrawArrays(GL10.GL_TRIANGLES, 0, size / vertexSize);

			size = 0;
			bb.position(0);
		}
	}

	public void draw(Texture texture, float x1, float y1, float u1, float v1, float c1, float x2, float y2, float u2,
			float v2, float c2, float x3, float y3, float u3, float v3, float c3)
	{
		if (size + 15 > data.length)
		{
			flush();
		}

		texture(texture);
		color(true);

		data[size++] = x1;
		data[size++] = y1;
		data[size++] = u1;
		data[size++] = v1;
		data[size++] = c1;

		data[size++] = x2;
		data[size++] = y2;
		data[size++] = u2;
		data[size++] = v2;
		data[size++] = c2;

		data[size++] = x3;
		data[size++] = y3;
		data[size++] = u3;
		data[size++] = v3;
		data[size++] = c3;
	}

	public void draw(Texture texture, float c, float x1, float y1, float u1, float v1, float x2, float y2, float u2,
			float v2, float x3, float y3, float u3, float v3)
	{
		if (size + 15 > data.length)
		{
			flush();
		}

		texture(texture);
		color(true);

		data[size++] = x1;
		data[size++] = y1;
		data[size++] = u1;
		data[size++] = v1;
		data[size++] = c;

		data[size++] = x2;
		data[size++] = y2;
		data[size++] = u2;
		data[size++] = v2;
		data[size++] = c;

		data[size++] = x3;
		data[size++] = y3;
		data[size++] = u3;
		data[size++] = v3;
		data[size++] = c;
	}

	public void draw(Texture texture, float x1, float y1, float u1, float v1, float x2, float y2, float u2, float v2,
			float x3, float y3, float u3, float v3)
	{
		if (size + 12 > data.length)
		{
			flush();
		}

		texture(texture);
		color(false);

		data[size++] = x1;
		data[size++] = y1;
		data[size++] = u1;
		data[size++] = v1;

		data[size++] = x2;
		data[size++] = y2;
		data[size++] = u2;
		data[size++] = v2;

		data[size++] = x3;
		data[size++] = y3;
		data[size++] = u3;
		data[size++] = v3;
	}

	public void draw(float x1, float y1, float c1, float x2, float y2, float c2, float x3, float y3, float c3)
	{
		if (size + 9 > data.length)
		{
			flush();
		}

		texture(null);
		color(true);

		data[size++] = x1;
		data[size++] = y1;
		data[size++] = c1;

		data[size++] = x2;
		data[size++] = y2;
		data[size++] = c2;

		data[size++] = x3;
		data[size++] = y3;
		data[size++] = c3;
	}

	public void draw(float c, float x1, float y1, float x2, float y2, float x3, float y3)
	{
		if (size + 9 > data.length)
		{
			flush();
		}

		texture(null);
		color(true);

		data[size++] = x1;
		data[size++] = y1;
		data[size++] = c;

		data[size++] = x2;
		data[size++] = y2;
		data[size++] = c;

		data[size++] = x3;
		data[size++] = y3;
		data[size++] = c;
	}
}
