package ru.engine.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import js.gles11.tools.Light;
import js.gles11.tools.Lighting;
import js.math.Vector3D;

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

	private HexLocation hexLocation = new HexLocation();

	private List<Vector3D> positions;

	public TestMeshView(Context context)
	{
		super(context);

		setRenderer(this);
		setRenderMode(RENDERMODE_CONTINUOUSLY);

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

		positions = new ArrayList<Vector3D>();
		Random rnd = new Random();

		for (int i = 0; i < 256; i++)
		{
			positions.add(new Vector3D(rnd.nextFloat() * 800 - 40, 0, rnd.nextFloat() * 80 - 40));
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
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Texture.filter(GL10.GL_LINEAR, GL10.GL_LINEAR);
		gl.glShadeModel(GL10.GL_SMOOTH);

		Lighting.on(gl);
		hexLocation.setupView(gl);
		hexLocation.tick();

		floor.bind();
		floor.draw();
		wall.bind();

		for (Vector3D pos : positions)
		{
			gl.glPushMatrix();
			gl.glTranslatef(pos.x, pos.y, pos.z);
			wall.draw();
			gl.glPopMatrix();

		}

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
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 1.0f, 200.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);

		gl.glDepthFunc(GL10.GL_LESS);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_LIGHTING);

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
