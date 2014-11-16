package ru.serjik.engine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class BatchDrawer
{
	private float[] data;
	private FloatBuffer bb;
	private int size = 0;

	public static final int TEXTURED = 1;
	public static final int COLORED = 2;

	private int mode = 0;

	private static final byte[] modeVertexSizes = new byte[] { 2, 2 + 2, 3, 2 + 2 + 1 };

	public BatchDrawer(int bufferSize)
	{
		data = new float[bufferSize];
		bb = ByteBuffer.allocateDirect(bufferSize * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
	}

	public void flush()
	{
		if (size > 0)
		{
			bb.position(0);
			bb.put(data, 0, size);

			int vertexSize = modeVertexSizes[mode];

			bb.position(0);
			eng.gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			eng.gl.glVertexPointer(2, GL10.GL_FLOAT, vertexSize * 4, bb);

			int offset = 2;

			if ((mode & TEXTURED) > 0)
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

			if ((mode & COLORED) > 0)
			{
				bb.position(offset);
				eng.gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
				eng.gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, vertexSize * 4, bb);
			}
			else
			{
				eng.gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
				eng.gl.glColor4f(1, 1, 1, 0);
			}

			eng.gl.glDrawArrays(GL10.GL_TRIANGLES, 0, size / vertexSize);

			size = 0;
			bb.position(0);
		}
	}

	public void draw(float x1, float y1, float u1, float v1, float c1, float x2, float y2, float u2, float v2,
			float c2, float x3, float y3, float u3, float v3, float c3)
	{
		if ((mode != (TEXTURED | COLORED)) || ((size + 15) > data.length))
		{
			flush();
		}

		mode = TEXTURED | COLORED;

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

	public void draw(float x1, float y1, float u1, float v1, float x2, float y2, float u2, float v2, float x3,
			float y3, float u3, float v3)
	{
		if ((mode != TEXTURED) || ((size + 12) > data.length))
		{
			flush();
		}

		mode = TEXTURED;

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
		if ((mode != COLORED) || ((size + 9) > data.length))
		{
			flush();
		}

		mode = COLORED;

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

}
