package ru.engine.test;

import javax.microedition.khronos.opengles.GL10;

import ru.serjik.engine.BatchDrawer;
import ru.serjik.engine.EngineView;
import ru.serjik.engine.Sprite;
import ru.serjik.engine.Texture;
import android.content.Context;

public class TestView extends EngineView
{
	Sprite bob;

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
		gl.glClearColor(1, 0, 0, 0.5f);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		

	}

}
