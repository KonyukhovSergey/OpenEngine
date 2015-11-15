/*
 * Sneginka.h
 *
 *  Created on: 08.03.2013
 *      Author: jauseg
 */

#ifndef SNEGINKA_H_
#define SNEGINKA_H_

#include <stdlib.h>

float rndf();
extern float scale;

#include "Vector2D.h"
#include "WindSimulator.h"

extern float turbulence;
extern float snowSpeed;

class Sneginka
{
public:
	Vector2D pos;
	Vector2D vel;
	float alfa;
	float size;

	void init(float width, float height)
	{
		init(0, 0, width, height);
	}

	void init(float left, float top, float width, float height)
	{
		alfa = rndf() * 0.25f + 0.75f;
		pos.set(rndf() * width + left, rndf() * height + top);
		vel.set(0, 0);
		size = (rndf() * 90.0f + 10.0f) * scale;
	}

	void putTo20(float *ptr)
	{
		float x = pos.x;
		float y = pos.y;
		float s = size * 0.2f;

		unsigned int ca = ((unsigned int) (255.0f * ((1.0f - (size - 10.0f) / 800.0f))));
		unsigned int c = ca << 16 | ca << 8 | ca | ca << 24;

		*ptr = x;
		ptr++;

		*ptr = y;
		ptr++;

		*ptr = s;
		ptr++;


		*(int*) ptr = c;
	}

	void putTo(float *pVertex, int *pColor)
	{
		float x = pos.x;
		float y = pos.y;
		float s = size * 0.2f;

		unsigned int ca = ((unsigned int) (255.0f * ((1.0f - (size - 10.0f) / 800.0f))));
		unsigned int c = ca << 16 | ca << 8 | ca | ca << 24;
		//unsigned int c = 0xFFFFFFFF;

		*pVertex = x - s;
		pVertex++;
		*pVertex = y - s;
		pVertex++;
		*pVertex = x + s;
		pVertex++;
		*pVertex = y - s;
		pVertex++;
		*pVertex = x + s;
		pVertex++;
		*pVertex = y + s;
		pVertex++;
		*pVertex = x - s;
		pVertex++;
		*pVertex = y - s;
		pVertex++;
		*pVertex = x + s;
		pVertex++;
		*pVertex = y + s;
		pVertex++;
		*pVertex = x - s;
		pVertex++;
		*pVertex = y + s;
		//pVertex++;

		*pColor = c;
		pColor++;
		*pColor = c;
		pColor++;
		*pColor = c;
		pColor++;
		*pColor = c;
		pColor++;
		*pColor = c;
		pColor++;
		*pColor = c;
		//pColor++;
	}

	void tick(WindSimulator *wind)
	{
		float s = scale * size * snowSpeed;

		pos.x += vel.x * s;
		pos.y += (vel.y + 1.1f) * s;

		//wind->calc(&pos, &vel);

		vel.x *= 0.97f;
		vel.y *= 0.97f;
	}
};

#endif /* SNEGINKA_H_ */
