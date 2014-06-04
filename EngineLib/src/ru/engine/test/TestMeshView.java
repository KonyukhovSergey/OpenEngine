package ru.engine.test;

import java.io.IOException;

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

public class TestMeshView extends GLSurfaceView implements Renderer
{
	private Texture decals;
	private Mesh wall, floor;
	private Light sun;

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
	}
	
	float a =0;

	@Override
	public void onDrawFrame(GL10 gl)
	{
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();

		GLU.gluLookAt(gl, 5, 1.5f, 0, 0, 0.5f, 0, 0, 1, 0);

		Texture.enable();
		decals.bind();
		gl.glClearColor(0.25f, 0, 0, 0);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Texture.filter(GL10.GL_LINEAR, GL10.GL_LINEAR);
		gl.glShadeModel(GL10.GL_SMOOTH);

		Lighting.on(gl);

		gl.glRotatef(a, 0, 1, 0);
		a+=0.5f;
		
		floor.bind();
		floor.draw();
		gl.glTranslatef(-0.9f, 0, 0);
		wall.bind();
		wall.draw();
		for(int i=0;i<512;i++)
		{
		gl.glTranslatef(-0.9f, 0, 0);
		wall.draw();
		}

	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 2.0f, 200.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);

		gl.glDepthFunc(GL10.GL_LESS);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_LIGHTING);

		sun.init(gl, 0);
		sun.setPosition(3, 3, 1);
		sun.setAmbient(0.1f, 0.1f, 0.1f, 1);
		sun.setDiffuse(.99f, 0.99f, 0.99f, 1);
		sun.setSpecular(gl, 0.95f, 0.5f, 0.5f, 1);
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
