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

const int SCREEN_WIDTH = 1280;
const int SCREEN_HEIGHT = 720;
float f = SCREEN_HEIGHT/(double)2;
int t;
vector<vec3> stars(1000);
SDL_Surface* screen;

// --------------------------------------------------------
// FUNCTION DECLARATIONS

void Draw();
void Update();
void drawLine(int i);

// --------------------------------------------------------
// FUNCTION DEFINITIONS

int main( int argc, char* argv[] )
{
	screen = InitializeSDL( SCREEN_WIDTH, SCREEN_HEIGHT );
	//stars(1000);
	for (int i = 0; i < stars.size(); i++) {
		stars[i].x = 1-2*float(rand())/float(RAND_MAX);
		stars[i].y = 1-2*float(rand())/float(RAND_MAX);
		stars[i].z = float(rand())/float(RAND_MAX);
	}
	t = SDL_GetTicks();
	while( NoQuitMessageSDL() )
	{
		Update();
		Draw();
		cout << "hej" << endl;
		
	}
	SDL_SaveBMP( screen, "screenshot.bmp" );
	return 0;
}

void Update() {
	int t2 = SDL_GetTicks();
	float dt = float(t2-t);
	t = t2;
	for (int i = 0; i < stars.size(); i++) {
		stars[i].z -= .0001*dt;
		if (stars[i].z <= 0) {
			stars[i].z += 1;
		} else if (stars[i].z > 1) {
			stars[i].z -= 1;
		}
	}
}
void drawLine(int i) {
		float screenx1 = f*stars[i].x/(double)stars[i].z+SCREEN_WIDTH/2;
		float screeny1 = f*stars[i].y/(double)stars[i].z+SCREEN_HEIGHT/2;

		float screenx2 = f*stars[i].x/(double)(stars[i].z+.01)+SCREEN_WIDTH/2;
		float screeny2 = f*stars[i].y/(double)(stars[i].z+.01)+SCREEN_HEIGHT/2;
		vec3 color = 0.2f * vec3(1,1,1) / (stars[i].z*stars[i].z)-.2f;
		//int points = 100*(1-stars[i].z*stars[i].z);
		int points = sqrt((screenx2-screenx1)*(screenx2-screenx1)+(screeny2-screeny1)*(screeny2-screeny1));
		if (points > 200) {
			points = 200;
		}
		for (int j = 0; j < points; j++) {
			PutPixelSDL(screen, screenx1+(screenx2-screenx1)*j/(double)(points-1), screeny1+(screeny2-screeny1)*j/(double)(points-1), color);
			//PutPixelSDL(screen, screenx1, screeny1, color);
		}
}

void Draw()
{
	SDL_FillRect(screen, 0, 0);
	for( int i=0; i<stars.size(); ++i )
	{
		drawLine(i);

		//float col = 1.5-1.5*stars[i].z;
		//vec3 color(1,1,1);
		//vec3 color = 0.2f * vec3(1,1,1) / (stars[i].z*stars[i].z);
		//PutPixelSDL( screen, screenx, screeny, color );

	}

	if( SDL_MUSTLOCK(screen) )
		SDL_UnlockSurface(screen);

	SDL_UpdateRect( screen, 0, 0, 0, 0 );


}