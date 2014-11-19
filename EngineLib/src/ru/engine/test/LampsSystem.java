package ru.engine.test;

import java.util.List;
import java.util.Random;

import android.net.NetworkInfo.State;

import ru.serjik.engine.BatchDrawer;
import ru.serjik.engine.Sprite;

public class LampsSystem
{
	private LampInfo[] infos;
	private long lastDrawTime = 0;

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
		long currentTime = System.currentTimeMillis();
		long dt = currentTime - lastDrawTime;
		dt = dt > 50 ? 50 : dt;
		lastDrawTime = currentTime;

		for (LampInfo info : infos)
		{
			if (info.isLighting)
			{
				sprite.position((info.x - back.tile().ox) * back.scale() + back.position().x, (info.y - back.tile().oy)
						* back.scale() + back.position().y);
				sprite.scale(0.2f + 0.15f * info.size);
				sprite.draw(bd, info.color);
			}
			info.tick(dt);
		}
	}
}
