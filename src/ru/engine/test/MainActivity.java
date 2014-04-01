package ru.engine.test;

import android.os.Bundle;
import android.app.Activity;

public class MainActivity extends Activity
{
	private TestView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		tv = new TestView(this);
		setContentView(tv);
	}

	@Override
	protected void onResume()
	{

		super.onResume();
		tv.onResume();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		tv.onPause();
	}

}
