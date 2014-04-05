package ru.engine.test;

import java.util.Random;
import java.util.TooManyListenersException;

import javax.microedition.khronos.opengles.GL10;

import ru.serjik.engine.BatchDrawer;
import ru.serjik.engine.ColorTools;
import ru.serjik.engine.EngineView;
import ru.serjik.engine.Sprite;
import ru.serjik.engine.Texture;
import ru.serjik.engine.Tile;
import ru.serjik.engine.eng;
import ru.serjik.utils.BitmapUtils;
import android.content.Context;
import android.os.SystemClock;

public class TestView extends EngineView
{
	private BatchDrawer bd;
	private Texture atlas;
	private Tile[] tiles;
	private Tile background;

	Random rnd = new Random(SystemClock.elapsedRealtime());

	public TestView(Context context)
	{
		super(context);

		bd = new BatchDrawer(1024 * 16);

	}

	@Override
	public void onCreated(GL10 gl)
	{
		atlas = new Texture(BitmapUtils.loadBitmapFromAsset(eng.am, "snows.png"));
		tiles = Tile.split(atlas, atlas.width / 4, atlas.height / 4);
		background = new Tile(atlas, 0, atlas.height / 2, atlas.width, atlas.height / 2);

	}

	@Override
	public void onChanged(GL10 gl)
	{
	}

	@Override
	public void onDrawFrame(GL10 gl)
	{
		gl.glClearColor(0, 0, 0, 0);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		Texture.enable();
		Texture.filter(GL10.GL_LINEAR, GL10.GL_LINEAR);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
		gl.glDisable(GL10.GL_DITHER);
		gl.glDisable(GL10.GL_FOG);
		gl.glDisable(GL10.GL_LIGHTING);
		gl.glDisable(GL10.GL_DEPTH_TEST);
		
		bd.draw(background, 0, 0, width(), height());
		
		float color = ColorTools.color("fff2");

		for (int i = 0; i < 512; i++)
		{
			bd.draw(tiles[rnd.nextInt(8)],color, rnd.nextFloat() * width(), rnd.nextFloat() * height());
		}
		bd.flush();

	}

}
