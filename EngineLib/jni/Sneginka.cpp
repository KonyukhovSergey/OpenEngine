/*
 * Sneginka.cpp
 *
 *  Created on: 08.03.2013
 *      Author: jauseg
 */

#include "Sneginka.h"

float rndf()
{
	return (float) (rand() & 0xffffff) / (float) 0xffffff;
//	return ((float) (rand() & 0x7fff)) / 32768.0f;
}
float scale = 1;
float turbulence = 0.2f;
int snowCount = 4;
float snowSpeed = 1.0f / 32.0f;

