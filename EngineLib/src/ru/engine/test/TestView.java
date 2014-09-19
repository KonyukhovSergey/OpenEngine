package ru.engine.test;

import java.util.Random;

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

	private Sprite sprite;

	Random rnd = new Random(SystemClock.elapsedRealtime());

	public TestView(Context context)
	{
		super(context);

		bd = new BatchDrawer(4096);

	}

	@Override
	public void onCreated(GL10 gl)
	{
		atlas = new Texture(BitmapUtils.loadBitmapFromAsset(eng.am, "snows.png"));
		tiles = Tile.split(atlas, atlas.width / 4, atlas.height / 4);
		background = new Tile(atlas, 0, atlas.height / 2, atlas.width, atlas.height / 2);
		Texture.enable();
		sprite = new Sprite(tiles[0]);
		sprite.position(310, 100);

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

		Texture.filter(GL10.GL_LINEAR, GL10.GL_LINEAR);

		gl.glShadeModel(GL10.GL_SMOOTH);

		bd.blending(false);

		background.draw(bd, 0, 0, width(), height());

		bd.blending(true);
		bd.blending(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
		float color = ColorTools.color("fff2");

		sprite.draw(bd);
		sprite.move(4, 0);
		sprite.rotate(0.05f);

		// for (int i = 0; i < 1 * 1024; i++)
		// {
		// tiles[rnd.nextInt(8)].drawScaledColored(bd, 0.5f, color,
		// rnd.nextFloat() * width(), rnd.nextFloat()
		// * height());
		// }
		bd.flush();

	}

}
