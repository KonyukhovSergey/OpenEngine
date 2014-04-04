package ru.engine.test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import ru.serjik.engine.BatchDrawer;
import ru.serjik.engine.EngineView;
import ru.serjik.engine.Sprite;
import ru.serjik.engine.Texture;
import android.content.Context;

public class TestView extends EngineView
{
	

	public TestView(Context context)
	{
		super(context);



	}

	@Override
	public void onCreated()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onChanged()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onDrawFrame(GL10 gl)
	{
		gl.glClearColor(0, 0, 0, 0);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		
		gl.glDisable(GL10.GL_BLEND);
	    gl.glDisable(GL10.GL_DITHER);
	    gl.glDisable(GL10.GL_FOG);
	    gl.glDisable(GL10.GL_LIGHTING);
	    gl.glDisable(GL10.GL_TEXTURE_2D);
	    gl.glShadeModel(GL10.GL_SMOOTH);

	    float[] vertices = {
	        -.5f, -0.5f,
	        .5f, 0,
	        0, .5f,
	        .5f, 0.5f,
	    };
	    
	    FloatBuffer vertsBuffer = ByteBuffer.allocateDirect(vertices.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();

	    byte[] colors = {
	    (byte) 64, (byte) 128, (byte) 128, (byte) 255,
	    (byte) 255, (byte) 255, (byte) 255, (byte) 255,
	    (byte) 200, (byte) 200, (byte) 200, (byte) 255,
	    (byte) 200, (byte) 200, (byte) 200, (byte) 255,
	    };

//	    float[] colors = {
//	    	    1.0f, 1.0f, 1.0f, 1.0f,
//	    	    1.0f, 1.0f, 1.0f, 1.0f,
//	    	    0.5f, 0.5f, 0.5f, 1.0f,
//	    	    0.5f, 0.5f, 0.5f, 1.0f,
//	    	    };
	    
	    ByteBuffer colorBuffer = ByteBuffer.allocateDirect(colors.length).order(ByteOrder.nativeOrder());
	    
	    vertsBuffer.position(0);
	    vertsBuffer.put(vertices);
	    vertsBuffer.position(0);
	    
	    colorBuffer.position(0);
	    colorBuffer.put(colors);
	    colorBuffer.position(0);

	    gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertsBuffer);
	    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	    gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 0, colorBuffer);
	    gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
	    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	    // End Render		
		

	}

}
