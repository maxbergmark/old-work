// Introduction lab that covers:
// * C++
// * SDL
// * 2D graphics
// * Plotting pixels
// * Video memory
// * Color representation
// * Linear interpolation
// * glm::vec3 and std::vector

#include "SDL.h"
#include <iostream>
#include <glm/glm.hpp>
#include <vector>
#include "SDLauxiliary.h"

using namespace std;
using glm::vec3;

// --------------------------------------------------------
// GLOBAL VARIABLES

const int SCREEN_WIDTH = 1600;
const int SCREEN_HEIGHT = 1200;
SDL_Surface* screen;

// --------------------------------------------------------
// FUNCTION DECLARATIONS

void Draw();
void Interpolate( float a, float b, vector<float>& result );

// --------------------------------------------------------
// FUNCTION DEFINITIONS

int main( int argc, char* argv[] )
{
	screen = InitializeSDL( SCREEN_WIDTH, SCREEN_HEIGHT );
	while( NoQuitMessageSDL() )
	{
		Draw();
	}
	SDL_SaveBMP( screen, "screenshot.bmp" );
	return 0;
}

void Interpolate (vec3 a, vec3 b, vector<vec3>& result) {
	for (int i = 0; i < result.size(); i++) {
		result[i].x = a.x+i*(b.x-a.x)/(double)result.size();
		result[i].y = a.y+i*(b.y-a.y)/(double)result.size();
		result[i].z = a.z+i*(b.z-a.z)/(double)result.size();
	}
}

void Draw()
{
	vec3 topLeft(1,0,0);
	vec3 topRight(0,0,1);
	vec3 bottomLeft(1,1,0);
	vec3 bottomRight(0,1,0);

	vector<vec3> left(SCREEN_HEIGHT);
	Interpolate(topLeft, bottomLeft, left);

	vector<vec3> right(SCREEN_HEIGHT);
	Interpolate(topRight, bottomRight, right);


	for( int y=0; y<SCREEN_HEIGHT; ++y )
	{

		vector<vec3> temp(SCREEN_WIDTH);
		Interpolate(left[y], right[y], temp);

		for( int x=0; x<SCREEN_WIDTH; ++x )
		{
			//vec3 color(1-1.0*x/(double)SCREEN_WIDTH, 1.0*y/(double)SCREEN_HEIGHT,1.0*x/(double)SCREEN_WIDTH-1.0*y/(double)SCREEN_HEIGHT);
			vec3 color(temp[x].x, temp[x].y, temp[x].z);
			PutPixelSDL( screen, x, y, color );
		}
	}

	if( SDL_MUSTLOCK(screen) )
		SDL_UnlockSurface(screen);

	SDL_UpdateRect( screen, 0, 0, 0, 0 );


}