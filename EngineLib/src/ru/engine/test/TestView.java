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
	BatchDrawer bd;

	public TestView(Context context)
	{
		super(context);

		bd = new BatchDrawer(512);

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

		bd.draw(ColorTools.RED_XF00F, -1, 1, 0, -1, 1, 1);

		bd.draw(-.5f, .5f, ColorTools.BLUE_X00FF, 0, -0.5f, ColorTools.GREEN_X0F0F, 0.5f, 0.5f, ColorTools.RED_XF00F);

		bd.flush();

	}

}
