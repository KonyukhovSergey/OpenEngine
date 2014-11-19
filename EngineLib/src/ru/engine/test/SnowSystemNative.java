package ru.engine.test;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import js.jni.code.NativeCalls;
import ru.serjik.engine.BufferAllocator;
import ru.serjik.engine.Sprite;

public class SnowSystemNative
{
	private static final int maxShowCount = 4096;

	private float width;

	public FloatBuffer vertexCoords = BufferAllocator.createFloatBuffer(maxShowCount * 12);
	public FloatBuffer textureCoords = BufferAllocator.createFloatBuffer(maxShowCount * 12);
	public ByteBuffer vertexColors = BufferAllocator.createByteBuffer(maxShowCount * 4 * 6);
	public FloatBuffer texBase = BufferAllocator.createFloatBuffer(12);

	public SnowSystemNative(float width, float height, Sprite snow)
	{
		this.width = (int) width;

		// this.vertexCoords = app.vertexCoords;
		// this.textureCoords = app.textureCoords;
		// this.vertexColors = app.vertexColors;
		// this.texBase = app.texBase;

		init(width, height);

		texBase.position(0);
		texBase.put(snow.tile().u1);
		texBase.put(snow.tile().v1);
		texBase.put(snow.tile().u2);
		texBase.put(snow.tile().v1);
		texBase.put(snow.tile().u2);
		texBase.put(snow.tile().v2);

		texBase.put(snow.tile().u1);
		texBase.put(snow.tile().v1);
		texBase.put(snow.tile().u2);
		texBase.put(snow.tile().v2);
		texBase.put(snow.tile().u1);
		texBase.put(snow.tile().v2);
		texBase.position(0);
	}

	private synchronized void init(float w, float h)
	{
		NativeCalls.ssFree();
		NativeCalls.ssInit(w, h);
	}

	public int getWidth()
	{
		return (int) width;
	}

	public void draw(GL10 gl)
	{
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexCoords);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureCoords);
		gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 0, vertexColors);

		gl.glDrawArrays(GL10.GL_TRIANGLES, 0,
				3 * NativeCalls.ssDraw(vertexCoords, textureCoords, vertexColors, texBase));

		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}
}
