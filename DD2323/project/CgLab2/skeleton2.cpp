#include <iostream>
#include <glm/glm.hpp>
#include <SDL.h>
#include "SDLauxiliary.h"
#include "TestModel2.h"

using namespace std;
using glm::vec3;
using glm::mat3;

// ----------------------------------------------------------------------------
// GLOBAL VARIABLES

const int SCREEN_WIDTH = 100;
const int SCREEN_HEIGHT = 100;
const int RESIZE = 4;
const int AAFACTOR = 1;
float f = SCREEN_HEIGHT/(double)1;
SDL_Surface* screen;
int t;
vector<Triangle> triangles;
vec3 cameraPos(0,0,-3);
float theta = 0;
mat3 R(1, 0, 0, 0, 1, 0, 0, 0, 1);
vec3 lightPos(0.0, -0.7, -0.5);
vec3 lightColor = 10.f*vec3(1,1,1);
vec3 indirectLight = 0.5f*vec3(1,1,1);

// ----------------------------------------------------------------------------
// FUNCTIONS

void Update();
void Draw();



struct Intersection {
	vec3 position;
	float distance;
	int triangleIndex;
	int originTriangle;
	bool onEdge;
};

bool ClosestIntersection(vec3 s, vec3 d, const vector<Triangle>& triangles, Intersection& closestIntersection);


int main( int argc, char* argv[] )
{
	screen = InitializeSDL( RESIZE*SCREEN_WIDTH, RESIZE*SCREEN_HEIGHT );
	t = SDL_GetTicks();	// Set start value for timer.
	LoadTestModel(triangles);

	Draw();
	while( NoQuitMessageSDL() )
	{
		Update();
	}

	SDL_SaveBMP( screen, "screenshot.bmp" );
	return 0;
}

void updateR() {
	R = mat3(cos(theta), 0, sin(theta), 0.0f, 1.0f, 0.0f, -sin(theta), 0.0f, cos(theta));
}

vec3 DirectLight(Intersection& i) {
	vec3 r = lightPos-i.position;
	vec3 D = lightColor*max(glm::dot(glm::normalize(r), triangles[i.triangleIndex].normal), 0.0f)/(4*3.14159265358979f*glm::dot(r, r));

	Intersection temp;
	temp.distance = std::numeric_limits<float>::max();
	temp.originTriangle = i.triangleIndex;
	bool temptest = ClosestIntersection(i.position, lightPos-i.position, triangles, temp);
	if (glm::distance(i.position, temp.position) > glm::distance(i.position, lightPos) || !temptest) {
		//i.distance += glm::distance(i.position, lightPos);
		return D;
	}
	
	return vec3(0,0,0);
}

void Update()
{
// Compute frame time:
int t2 = SDL_GetTicks();
float dt = float(t2-t);
t = t2;
if (dt > 5) {
	cout << "Render time: " << dt << " ms." << endl;
}
Uint8* keystate = SDL_GetKeyState( 0 );
bool click = false;
if( keystate[SDLK_UP] )
{
	cameraPos += R*vec3(0, 0, 0.1f);
	click = true;
}
if( keystate[SDLK_DOWN] )
{
	cameraPos += R*vec3(0, 0, -0.1f);
	click = true;
}
if( keystate[SDLK_LEFT] )
{
	//cameraPos.x -= .1;
	theta += .05;
	updateR();
	click = true;
}
if( keystate[SDLK_RIGHT] )
{
	//cameraPos.x += .1;
	theta -= .05;
	updateR();
	click = true;
}
if( keystate[SDLK_w] ) {
	lightPos += vec3(0, 0, 0.1f);
	click = true;
}
if( keystate[SDLK_a] ) {
	lightPos += vec3(-0.1f, 0, 0);
	click = true;
}
if( keystate[SDLK_s] ) {
	lightPos += vec3(0, 0, -0.1f);
	click = true;
}
if( keystate[SDLK_d] ) {
	lightPos += vec3(0.1f, 0, 0);
	click = true;
}
if( keystate[SDLK_e] ) {
	lightPos += vec3(0, -0.1f, 0);
	click = true;
}
if( keystate[SDLK_q] ) {
	lightPos += vec3(0, 0.1f, 0);
	click = true;
}
if( keystate[SDLK_i] ) {
	cameraPos += R*vec3(0, -0.1f, 0);
	click = true;
}
if( keystate[SDLK_j] ) {
	cameraPos += R*vec3(-0.1f, 0, 0);
	click = true;
}
if( keystate[SDLK_k] ) {
	cameraPos += R*vec3(0, 0.1f, 0);
	click = true;
}
if( keystate[SDLK_l] ) {
	cameraPos += R*vec3(0.1f, 0, 0);
	click = true;
}
if (click) {
	Draw();
}

}

bool ClosestIntersection(vec3 s, vec3 d, 
	const vector<Triangle>& triangles, 
	Intersection& closestIntersection) {
		bool retBool = false;

		for (int i = 0; i < triangles.size(); i++) {
			vec3 v0 = triangles[i].v0;
			vec3 v1 = triangles[i].v1;
			vec3 v2 = triangles[i].v2;
			vec3 e1 = v1-v0;
			vec3 e2 = v2-v0;
			float l1 = glm::length(e1);
			float l2 = glm::length(e2);
			vec3 b = s-v0;

			float fac = -d.x*e1.y*e2.z
						+d.x*e2.y*e1.z
						+e1.x*d.y*e2.z
						-e1.x*e2.y*d.z
						-e2.x*d.y*e1.z
						+e2.x*e1.y*d.z;

			if (fac != 0) {

				vec3 Ainv0(e1.y*e2.z-e2.y*e1.z, e2.x*e1.z-e1.x*e2.z, e1.x*e2.y-e2.x*e1.y);
				float dist = glm::dot(Ainv0, b)/fac;
				if (dist > 0 & dist < closestIntersection.distance && i != closestIntersection.originTriangle) {

					vec3 Ainv1(-e2.y*d.z+d.y*e2.z, -d.x*e2.z+e2.x*d.z, -e2.x*d.y+d.x*e2.y);
					vec3 Ainv2(-d.y*e1.z+e1.y*d.z, -e1.x*d.z+d.x*e1.z, -d.x*e1.y+e1.x*d.y);
					float x1 = glm::dot(Ainv1, b)/fac;
					float x2 = glm::dot(Ainv2, b)/fac;
					if (dist < closestIntersection.distance) {
						if (round(10000*(x1+x2))/10000 <= 1.0f && x1 >= 0 && x2 >= 0) {
							closestIntersection.distance = dist;
							closestIntersection.triangleIndex = i;
							closestIntersection.position = s+d*dist;
							retBool = true;
						}
					}
					if ((x1 >= -0.02f && x2 >= -0.02f && x1+x2 < 1.02f) && (round(10*(x1+x2))/10 == 1.0f || round(10*l1*x1)/l1 == 0 || round(10*l2*x2)/l2 == 0)) {
						closestIntersection.onEdge = true;
					}
			 	}
			}
		}
	closestIntersection.distance = glm::distance(s, closestIntersection.position);
	return retBool;
}

double round(double d) {
	return floor(d+0.5);
}

void Draw()
{
	if( SDL_MUSTLOCK(screen) )
		SDL_LockSurface(screen);

	vec3 s = cameraPos;
	for (int y = 0; y < SCREEN_HEIGHT; y++) {
		for (int x = 0; x < SCREEN_WIDTH; x++) {

			vec3 color(0,0,0);
			Intersection closestIntersection;
			closestIntersection.distance = std::numeric_limits<float>::max();
			closestIntersection.originTriangle = std::numeric_limits<int>::max();
			closestIntersection.onEdge = false;
			vec3 d(x-SCREEN_WIDTH/2, y-SCREEN_HEIGHT/2, f);
			d = R*d;
			bool test = ClosestIntersection(s, d, triangles, closestIntersection);
			if (test && closestIntersection.onEdge) {

				for (int a = 1-AAFACTOR; a < AAFACTOR; a++) {
					for (int b = 1-AAFACTOR; b < AAFACTOR; b++) {
						vec3 d(x+1.0f*b/AAFACTOR-SCREEN_WIDTH/2, y+1.0f*a/AAFACTOR-SCREEN_HEIGHT/2, f);
						d = R*d;
						Intersection closestIntersection;
						closestIntersection.distance = std::numeric_limits<float>::max();
						closestIntersection.originTriangle = std::numeric_limits<int>::max();

						bool test = ClosestIntersection(s, d, triangles, closestIntersection);
						
						if (test) {
							//color = 0.1f*triangles[closestIntersection.triangleIndex].color*closestIntersection.distance*closestIntersection.distance;
							//color = 8.0f*triangles[closestIntersection.triangleIndex].color/(0+(closestIntersection.distance-0.0f)*(closestIntersection.distance-0.0f));
							//color = -0.01f*triangles[closestIntersection.triangleIndex].color*glm::dot(triangles[closestIntersection.triangleIndex].normal, d);
							color += 1.0f/(2*AAFACTOR-1)/(2*AAFACTOR-1)*triangles[closestIntersection.triangleIndex].color*(DirectLight(closestIntersection)+indirectLight);
							//color += 1.0f/AAFACTOR/AAFACTOR*(DirectLight(closestIntersection)+indirectLight);
						}

					}
				}
			} else if (test) {
				color = triangles[closestIntersection.triangleIndex].color*(DirectLight(closestIntersection)+indirectLight);
			}

			for (int k = 0; k < RESIZE; k++) {
				for (int j = 0; j < RESIZE; j++) {
					PutPixelSDL( screen, x*RESIZE+j, y*RESIZE+k, color );
				}
			}
			//PutPixelSDL( screen, x, y, color );
			//PutPixelSDL( screen, triangles[i].v1.x*SCREEN_WIDTH, triangles[i].v1.y*SCREEN_HEIGHT, color );
			//PutPixelSDL( screen, triangles[i].v2.x*SCREEN_WIDTH, triangles[i].v2.y*SCREEN_HEIGHT, color );

			
		}
	}


	if( SDL_MUSTLOCK(screen) )
		SDL_UnlockSurface(screen);

	SDL_UpdateRect( screen, 0, 0, 0, 0 );
}