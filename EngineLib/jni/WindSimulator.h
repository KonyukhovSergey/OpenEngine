/*
 * WindSimulator.h
 *
 *  Created on: 08.03.2013
 *      Author: jauseg
 */


#ifndef WINDSIMULATOR_H_
#define WINDSIMULATOR_H_

#define borderCount 2
#define sqr2h 0.707107f

float rndf();

class WindCell
{
public:
	Vector2D pos;
	Vector2D vel;
	Vector2D force;

	WindCell* neighbours[8];

	WindCell()
	{
	}

	void set(float x, float y)
	{
		pos.x = x;
		pos.y = y;
	}

	void calcForce(Vector2D *dirs, float cellSize)
	{
		//force.set(0, 0);

		Vector2D t,d;

		for (int i = 0; i < 8; i++)
		{
			t.set(dirs[i]);
			t.plus(neighbours[i]->vel);
			d.set(t);
			d.scale(cellSize / d.len());
			d.minus(t);
			force.plus(&d);
			neighbours[i]->force.minus(&d);
		}
	}

	void tick(float dt, float viscosity)
	{
		vel.scale(viscosity);
		vel.plus(&force, dt);
		force.set(0, 0);
	}
};

class WindSimulator
{
public:
	int widthCount;
	int heigthCount;
	float cellSize;

	WindCell *cells;
	Vector2D dirs[8];

	int cellsCount;

	WindSimulator()
	{
		cells = 0;
	}

	void init(float width, float height)
	{
		float c = 32;
		cells = 0;
		init(width, height, width > height ? width / c : height / c);
	}

	void init(float width, float heigth, float cellSize)
	{
		//dirs[0].set(-0.707107f, -0.707107f);
		//dirs[1].set(0.0f, -1.0f);
		//dirs[2].set(0.707107f, -0.707107f);
		//dirs[3].set(1.0f, 0.0f);
		//dirs[4].set(0.707107f, 0.707107f);
		//dirs[5].set(0.0f, 1.0f);
		//dirs[6].set(-0.707107f, 0.707107f);
		//dirs[7].set(-1.0f, 0.0f);

		dirs[0].set(-1.0f, -1.0f);
		dirs[1].set( 0.0f, -1.0f);
		dirs[2].set( 1.0f, -1.0f);
		dirs[3].set( 1.0f,  0.0f);
		dirs[4].set( 1.0f,  1.0f);
		dirs[5].set( 0.0f,  1.0f);
		dirs[6].set(-1.0f,  1.0f);
		dirs[7].set(-1.0f,  0.0f);

		//for (int i = 0; i < 8; i++)	{ dirs[i].scale(cellSize); }

		widthCount = (int)(width / cellSize) + 1 + 2 * borderCount;
		heigthCount = (int)(heigth / cellSize) + 1 + 2 * borderCount;

		this->cellSize = cellSize;

		cellsCount = widthCount * heigthCount;

		free();

		cells = new WindCell[cellsCount];

		for (int i = 0; i < cellsCount; i++)
		{
			int x = i % widthCount;
			int y = i / widthCount;

			cells[i].set(cellSize * (float)(x - borderCount), cellSize * (float)(y - borderCount));

			cells[i].neighbours[0] = &cells[((x - 1 + widthCount) % widthCount) + ((y - 1 + heigthCount) % heigthCount) * widthCount];
			cells[i].neighbours[1] = &cells[((x     + widthCount) % widthCount) + ((y - 1 + heigthCount) % heigthCount) * widthCount];
			cells[i].neighbours[2] = &cells[((x + 1 + widthCount) % widthCount) + ((y - 1 + heigthCount) % heigthCount) * widthCount];
			cells[i].neighbours[3] = &cells[((x + 1 + widthCount) % widthCount) + ((y     + heigthCount) % heigthCount) * widthCount];
			cells[i].neighbours[4] = &cells[((x + 1 + widthCount) % widthCount) + ((y + 1 + heigthCount) % heigthCount) * widthCount];
			cells[i].neighbours[5] = &cells[((x     + widthCount) % widthCount) + ((y + 1 + heigthCount) % heigthCount) * widthCount];
			cells[i].neighbours[6] = &cells[((x - 1 + widthCount) % widthCount) + ((y + 1 + heigthCount) % heigthCount) * widthCount];
			cells[i].neighbours[7] = &cells[((x - 1 + widthCount) % widthCount) + ((y     + heigthCount) % heigthCount) * widthCount];
		}
	}

	void free()
	{
		if (cells)
		{
			delete[] cells;
			cells = 0;
		}
	}

	void tick(float dt)
	{
		float viscosity = powf(0.99f, dt);

		for (int i = 0; i < cellsCount; i++)
		{
			cells[i].calcForce(dirs, cellSize);
		}

		for (int i = 0; i < cellsCount; i++)
		{
			cells[i].tick(dt, viscosity);
		}
	}

	void fling(float x, float y, float dx, float dy)
	{
		WindCell *cell = cellAt(x, y);
		cell->vel.plus(dx, dy);
	}

	WindCell* cellAt(float x, float y)
	{
		int cx = (int)(x / cellSize) + borderCount;
		int cy = (int)(y / cellSize) + borderCount;

		return &cells[cy * widthCount + cx];
	}
};

#endif
