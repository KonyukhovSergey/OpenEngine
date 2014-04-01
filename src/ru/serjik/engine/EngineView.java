package ru.serjik.engine;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ru.serjik.engine.FrameRateCalculator.FrameRateUpdateInterface;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

public abstract class EngineView extends GLSurfaceView implements FrameRateUpdateInterface
{
	private static final String TAG = "ru.serjik.engine.EngineView";

	private FrameRateCalculator frameRateCalculator;

	public EngineView(Context context)
	{
		super(context);
		init();
	}

	public EngineView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	public abstract void onCreated();

	public abstract void onChanged();

	public abstract void onDrawFrame();

	private void init()
	{
		setRenderer(engineRenderer);
		setRenderMode(RENDERMODE_CONTINUOUSLY);
		frameRateCalculator = new FrameRateCalculator(this);
	}

	@Override
	public void onFrameRateUpdate(FrameRateCalculator frameRateCalculator)
	{
		Log.v(TAG, frameRateCalculator.frameString());
	}

	private Renderer engineRenderer = new Renderer()
	{
		@Override
		public void onDrawFrame(GL10 gl)
		{
			frameRateCalculator.frameBegin();
			EngineView.this.onDrawFrame();
			frameRateCalculator.frameDone();
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height)
		{
			onChanged();
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config)
		{
			eng.gl = gl;
			onCreated();
		}
	};
}
