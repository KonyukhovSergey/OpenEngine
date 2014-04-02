package ru.engine.test;

import javax.microedition.khronos.opengles.GL10;

import ru.serjik.engine.EngineView;
import android.content.Context;

public class TestView extends EngineView
{

	public TestView(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
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
