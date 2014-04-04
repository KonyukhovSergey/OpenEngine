package ru.engine.test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import ru.serjik.engine.BatchDrawer;
import ru.serjik.engine.ColorTools;
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
	    Texture.enable();
	    gl.glShadeModel(GL10.GL_SMOOTH);

	    float[] vertices = {
	        -1, -1,ColorTools.XF00F_RED,
	        -1, 1,ColorTools.X0F0F_GREEN,
	        1, -1,ColorTools.X00FF_BLUE,
	        1,1, ColorTools.XFFFF_WHITE,
	    };
	    
	    FloatBuffer vertsBuffer = ByteBuffer.allocateDirect(vertices.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
	
	    vertsBuffer.position(0);
	    vertsBuffer.put(vertices);

	    vertsBuffer.position(0);
	    gl.glVertexPointer(2, GL10.GL_FLOAT, 12, vertsBuffer);
	    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	    
	    vertsBuffer.position(2);
	    gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 12, vertsBuffer);
	    gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
	    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	    // End Render		
		

	}

}
