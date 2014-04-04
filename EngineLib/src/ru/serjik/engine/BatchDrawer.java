package ru.serjik.engine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class BatchDrawer
{
	private float[] data;
	private FloatBuffer bb;

	private Texture texture = null;

	private boolean blendingEnabled = false;
	private int blendingSrc = 0;
	private int blendingDst = 0;

	private boolean colorEnabled = false;

	private int size = 0;

	public BatchDrawer(int bufferSize)
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
		}
	}

	public void blending(int src, int dst)
	{
		if (blendingSrc != src || blendingDst != dst)
		{
			if (blendingEnabled)
			{
				flush();
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
			bb.position(size);
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

		data[size] = x1;
		size++;
		data[size] = y1;
		size++;
		data[size] = u1;
		size++;
		data[size] = v1;
		size++;
		data[size] = c1;
		size++;

		data[size] = x2;
		size++;
		data[size] = y2;
		size++;
		data[size] = u2;
		size++;
		data[size] = v2;
		size++;
		data[size] = c2;
		size++;

		data[size] = x3;
		size++;
		data[size] = y3;
		size++;
		data[size] = u3;
		size++;
		data[size] = v3;
		size++;
		data[size] = c3;
		size++;
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

		data[size] = x1;
		size++;
		data[size] = y1;
		size++;
		data[size] = u1;
		size++;
		data[size] = v1;
		size++;
		data[size] = c;
		size++;

		data[size] = x2;
		size++;
		data[size] = y2;
		size++;
		data[size] = u2;
		size++;
		data[size] = v2;
		size++;
		data[size] = c;
		size++;

		data[size] = x3;
		size++;
		data[size] = y3;
		size++;
		data[size] = u3;
		size++;
		data[size] = v3;
		size++;
		data[size] = c;
		size++;
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

		data[size] = x1;
		size++;
		data[size] = y1;
		size++;
		data[size] = u1;
		size++;
		data[size] = v1;
		size++;

		data[size] = x2;
		size++;
		data[size] = y2;
		size++;
		data[size] = u2;
		size++;
		data[size] = v2;
		size++;

		data[size] = x3;
		size++;
		data[size] = y3;
		size++;
		data[size] = u3;
		size++;
		data[size] = v3;
		size++;
	}

	public void draw(float x1, float y1, float c1, float x2, float y2, float c2, float x3, float y3, float c3)
	{
		if (size + 9 > data.length)
		{
			flush();
		}

		texture(null);
		color(true);

		data[size] = x1;
		size++;
		data[size] = y1;
		size++;
		data[size] = c1;
		size++;

		data[size] = x2;
		size++;
		data[size] = y2;
		size++;
		data[size] = c2;
		size++;

		data[size] = x3;
		size++;
		data[size] = y3;
		size++;
		data[size] = c3;
		size++;
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

	public void draw(Texture texture, float[] vertices, float color)
	{
		if (vertices.length > size)
		{
			return;
		}

		if (size + vertices.length > data.length)
		{
			flush();
		}

		texture(texture);

		if (colorEnabled == false)
		{
			System.arraycopy(vertices, 0, data, size, vertices.length);
			size += vertices.length;
		}
		else
		{
			for (int i = 0; i < vertices.length; i += 2)
			{
				if (texture != null)
				{
					System.arraycopy(vertices, i, data, size, 4);
					i += 2;
					size += 4;
				}
				else
				{
					System.arraycopy(vertices, i, data, size, 2);
					size += 2;
				}

				data[size] = color;
				size++;
			}

		}

	}

	void draw(Sprite sprite, float x, float y)
	{

	}

	void draw(Sprite sprite, float x, float y, float rotation)
	{
	}

}
