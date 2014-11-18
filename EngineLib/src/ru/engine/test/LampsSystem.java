package ru.engine.test;

import java.util.List;
import java.util.Random;

import ru.serjik.engine.BatchDrawer;
import ru.serjik.engine.Sprite;

public class LampsSystem
{
	private LampInfo[] infos;
	private static Random rnd = new Random(System.currentTimeMillis());
	private long lastTimeDraw = 0;

	public LampsSystem(List<String> lines)
	{
		infos = new LampInfo[lines.size()];

		for (int i = 0; i < infos.length; i++)
		{
			infos[i] = new LampInfo(lines.get(i));
		}
	}

	public void draw(BatchDrawer bd, Sprite back, Sprite sprite)
	{
		long dt = System.currentTimeMillis() - lastTimeDraw;
		
		if (dt > 50)
		{
			dt = 50;
		}
		
		lastTimeDraw = System.currentTimeMillis();

		for (LampInfo info : infos)
		{
			if (info.time > 5000)
			{
				sprite.position((info.x - back.tile().ox) * back.scale() + back.position().x, (info.y - back.tile().oy)
						* back.scale() + back.position().y);
				sprite.scale(0.3f + 0.12f * info.size);
				sprite.draw(bd, info.color);
			}
			else if (info.time < 0)
			{
				info.time = 10000 + rnd.nextInt(5000);
			}
			info.time -= dt;
		}
	}
}
