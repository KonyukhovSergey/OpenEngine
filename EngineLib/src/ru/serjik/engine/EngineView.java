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
	private static final float desiredWidth = 720;

	private FrameRateCalculator frameRateCalculator;

	private int width;
	private int height;

	public int width()
	{
		return width;
	}

	public int height()
	{
		return height;
	}

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

	public abstract void onCreated(GL10 gl);

	public abstract void onChanged(GL10 gl);

	public abstract void onDrawFrame(GL10 gl);

	private void init()
	{
		setRenderer(engineRenderer);
		setRenderMode(RENDERMODE_CONTINUOUSLY);
		frameRateCalculator = new FrameRateCalculator(this);
		eng.ev = this;
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
			EngineView.this.onDrawFrame(gl);
			frameRateCalculator.frameDone();
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height)
		{
			float ratio = (float) height / (float) width;
			EngineView.this.width = (int) desiredWidth;
			EngineView.this.height = (int) (desiredWidth * ratio);

			gl.glViewport(0, 0, width, height);
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrthof(0, width(), height(), 0, 1, -1);

			onChanged(gl);
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config)
		{
			eng.gl = gl;

			gl.glDisable(GL10.GL_BLEND);

			gl.glDisable(GL10.GL_DITHER);
			gl.glDisable(GL10.GL_FOG);
			gl.glDisable(GL10.GL_LIGHTING);
			gl.glDisable(GL10.GL_DEPTH_TEST);
			
			onCreated(gl);
		}
	};
}
