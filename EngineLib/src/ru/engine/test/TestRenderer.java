package ru.engine.test;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import ru.serjik.engine.AtlasGenerator;
import ru.serjik.engine.BatchDrawer;
import ru.serjik.engine.ColorTools;
import ru.serjik.engine.EngineRenderer2D;
import ru.serjik.engine.Sprite;
import ru.serjik.engine.Texture;
import ru.serjik.utils.BitmapUtils;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.SystemClock;

public class TestRenderer extends EngineRenderer2D
{
	public TestRenderer(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
	}

	private BatchDrawer bd;
	private Texture atlas;
	private Sprite background, sparkle, star;

	Random rnd = new Random(SystemClock.elapsedRealtime());

	@Override
	public void created(GL10 gl)
	{
		AtlasGenerator ag = new AtlasGenerator(1024);

		AssetManager am = context.getAssets();

		background = new Sprite(ag.tile(BitmapUtils.loadBitmapFromAsset(am, "background.jpg")));
		sparkle = new Sprite(ag.tile(BitmapUtils.loadBitmapFromAsset(am, "sparkle.png")));
		star = new Sprite(ag.tile(BitmapUtils.loadBitmapFromAsset(am, "star.png")));

		atlas = new Texture(ag.atlas(), gl);
		ag.atlas().recycle();

		bd = new BatchDrawer(4096, gl);
	}

	@Override
	public void changed(GL10 gl, int width, int height)
	{
		Texture.enable(gl);
		atlas.bind();

		Texture.filter(gl, GL10.GL_LINEAR, GL10.GL_LINEAR);
		gl.glShadeModel(GL10.GL_SMOOTH);

		background.position(width() / 2.0f, height() / 2.0f);
		background.scale(height() / background.height());
	}

	@Override
	public void draw(GL10 gl)
	{
		// gl.glColor4f(1, 1, 1, 0);
		gl.glDisable(GL10.GL_BLEND);
		background.draw(bd);
		bd.flush();

		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);

		sparkle.position(200, 220);
		sparkle.draw(bd, ColorTools.color(0.1f * 1.0f, 0.1f, 0, 1));
		sparkle.position(300, 220);
		sparkle.draw(bd, ColorTools.GREEN_X0F0F);

		bd.flush();
	}
}
