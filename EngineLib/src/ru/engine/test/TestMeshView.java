package ru.engine.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import js.gles11.tools.Light;
import js.gles11.tools.Lighting;

import ru.serjik.engine.Mesh;
import ru.serjik.engine.MeshFileLoader;
import ru.serjik.engine.Texture;
import ru.serjik.engine.eng;
import ru.serjik.utils.BitmapUtils;
import ru.serjik.utils.FileUtils;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.opengl.GLSurfaceView.Renderer;
import android.view.MotionEvent;

public class TestMeshView extends GLSurfaceView implements Renderer
{
	private Texture decals;
	private Mesh wall, floor;
	private Light sun;

	private int size = 50;
	private HexLocation hexLocation = new HexLocation(size / 2, size / 2);

	private byte[] field;

	public TestMeshView(Context context)
	{
		super(context);

		setRenderer(this);
		setRenderMode(RENDERMODE_CONTINUOUSLY);

		field = new byte[size * size];
		Random rnd = new Random(1);
		for (int i = 0; i < field.length; i++)
		{
			field[i] = (byte) (rnd.nextFloat() > 0.9f ? 1 : 0);
		}

		try
		{
			MeshFileLoader mfl = new MeshFileLoader(FileUtils.readAllLines(eng.am.open("decals.obj"), true));

			wall = new Mesh(mfl.data("wall"));
			floor = new Mesh(mfl.data("floor"));

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		setClickable(true);
	}

	@Override
	public void onDrawFrame(GL10 gl)
	{
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();

		// GLU.gluLookAt(gl, 5, 2.5f, 0, 0, 0.5f, 0, 0, 1, 0);

		Texture.enable();
		decals.bind();
		gl.glClearColor(0.1f, 0.5f, 0.7f, 0);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		Texture.filter(GL10.GL_LINEAR, GL10.GL_LINEAR);
		gl.glShadeModel(GL10.GL_SMOOTH);

		Lighting.on(gl);
		hexLocation.setupView(gl);
		hexLocation.tick();

		floor.bind();

		int dist = 8;

		for (int q = 0; q < dist * 2 + 1; q++)
		{
			for (int r = 0; r < dist * 2 + 1; r++)
			{
				if (HexUtils.distance(q, r, dist, dist) <= dist)
				{
					gl.glPushMatrix();

					gl.glTranslatef(HexUtils.x(q + hexLocation.q - dist, hexLocation.r + r - dist), 0,
							HexUtils.y(hexLocation.r + r - dist));

					floor.bind();
					floor.draw();

					int fq = q + hexLocation.q - dist;
					int fr = r + hexLocation.r - dist;

					if (fq >= 0 && fq < size && fr >= 0 && fr < size)
					{
						if (field[fq + fr * size] == 1)
						{
							wall.bind();
							wall.draw();
						}
					}
					else
					{
						// wall.bind();
						// wall.draw();
					}
					gl.glPopMatrix();
				}
			}
		}

		wall.bind();

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event)
	{
		if (event.getActionMasked() == MotionEvent.ACTION_UP)
		{
			float x = event.getX();
			float y = event.getY();

			if (x < getWidth() / 3)
			{
				hexLocation.rotateLeft();
			}
			else if (x > (2 * getWidth()) / 3)
			{
				hexLocation.rotateRight();
			}
			else
			{
				if (y < (3 * getHeight()) / 4)
				{
					hexLocation.moveForward();
				}
				else
				{
					hexLocation.moveBackward();
				}
			}

		}

		return super.dispatchTouchEvent(event);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 1.0f, 20.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);

		gl.glDepthFunc(GL10.GL_LESS);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_LIGHTING);

		gl.glEnable(GL10.GL_FOG);
		gl.glFogfv(GL10.GL_FOG_COLOR, new float[] { 0.1f, 0.5f, 0.7f, 0 }, 0);
		gl.glFogf(GL10.GL_FOG_START, 2);
		gl.glFogf(GL10.GL_FOG_END, 8);
		gl.glFogf(GL10.GL_FOG_DENSITY, 0.25f);
		gl.glFogx(GL10.GL_FOG_MODE, GL10.GL_EXP2);

		sun.init(gl, 0);
		sun.setPosition(0, 1, 0);
		sun.setAmbient(0.1f, 0.1f, 0.1f, 1);
		sun.setDiffuse(1.0f, 1.0f, 1.0f, 1);
		sun.setSpecular(1.0f, 1.0f, 1.0f, 1);
		sun.setProfile(1.0f, 0.01f, 0.0f);
		sun.on();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		eng.gl = gl;

		gl.glDisable(GL10.GL_BLEND);

		gl.glDisable(GL10.GL_DITHER);
		gl.glDisable(GL10.GL_FOG);
		// gl.glCullFace(GL10.GL_CW);
		// gl.glEnable(GL10.GL_CULL_FACE);

		decals = new Texture(BitmapUtils.loadBitmapFromAsset(eng.am, "decals.png"));

		// gl.glTranslatef(0, 0, 1);

		sun = new Light();

	}

}
