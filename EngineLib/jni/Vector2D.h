/*
 * Vector2D.h
 *
 *  Created on: 08.03.2013
 *      Author: jauseg
 */

#ifndef VECTOR2D_H_
#define VECTOR2D_H_

#include <math.h>

float interpolate(float v1, float v2, float v3, float v4, float px, float py);

class Vector2D
{
public:

public:
	float x;
	float y;

	Vector2D()
	{
		x = 0;
		y = 0;
	}

	Vector2D(float _x, float _y)
	{
		x = _x;
		y = _y;
	}

	void plus(float _x, float _y)
	{
		x += _x;
		y += _y;
	}

	void plus(float _x, float _y, float s)
	{
		x += _x * s;
		y += _y * s;
	}

	void plus(Vector2D *a)
	{
		x += a->x;
		y += a->y;
	}

	void minus(Vector2D *a)
	{
		x -= a->x;
		y -= a->y;
	}

	void plus(Vector2D *a, float s)
	{
		x += a->x * s;
		y += a->y * s;
	}

	float len2()
	{
		return x * x + y * y;
	}

	float len()
	{
		return sqrt(x * x + y * y);
	}

	float scalar(Vector2D *b)
	{
		return x * b->x + y * b->y;
	}

	void scale(float s)
	{
		x *= s;
		y *= s;
	}

	void set(float x, float y)
	{
		this->x = x;
		this->y = y;
	}

	void set(Vector2D *v)
	{
		x = v->x;
		y = v->y;
	}
};



#endif /* VECTOR2D_H_ */
