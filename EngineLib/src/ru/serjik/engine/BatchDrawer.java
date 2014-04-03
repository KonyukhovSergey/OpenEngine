package ru.serjik.engine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class BatchDrawer
{
	private float[] data;
	private FloatBuffer bb;

	private int size;
	private int triangleSize = 3 * (2 + 2);
	private int vertexSize=2+2;

	public BatchDrawer(int bufferSize)
	{
		data = new float[bufferSize];
		bb = ByteBuffer.allocateDirect(bufferSize * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		size = 0;
	}

	void flush()
	{
		if (size > 0)
		{
			bb.put(data, 0, size);
			eng.gl.glColorPointer(vertexSize, type, stride, pointer)
			eng.gl.glDrawArrays(GL10.GL_TRIANGLES, 0, size / vertexSize);
			size = 0;
			bb.position(size);
		}
	}

	void draw(Sprite sprite, float x, float y)
	{
	}

	void draw(Sprite sprite, float x, float y, float rotation)
	{
	}

}
