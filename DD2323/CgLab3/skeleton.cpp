#include <iostream>
#include <glm/glm.hpp>
#include <SDL.h>
#include "SDLauxiliary.h"
#include "TestModel.h"
#include "windows.h"

using namespace std;
using glm::vec3;
using glm::ivec2;
using glm::mat3;

// ----------------------------------------------------------------------------
// GLOBAL VARIABLES

const int SCREEN_WIDTH = 500;
const int SCREEN_HEIGHT = 500;
int f = SCREEN_HEIGHT;
SDL_Surface* screen;
int t;
vector<Triangle> triangles;
vec3 cameraPos(0, 0, -3);
vec3 lightPos(0, -0.5, -0.7);
vec3 lightPower = 11.f*vec3(1, 1, 1);
vec3 indirectLight = 0.5f*vec3(1, 1, 1);
float theta = 0;
float phi = 0;
mat3 R(1, 0, 0, 0, 1, 0, 0, 0, 1);
mat3 Rx(1, 0, 0, 0, 1, 0, 0, 0, 1);
float depthBuffer[SCREEN_HEIGHT][SCREEN_WIDTH];



// ----------------------------------------------------------------------------
// FUNCTIONS

struct Pixel {
	int x;
	int y;
	float zinv;
	vec3 illumination;
};

struct Vertex {
	vec3 position;
	vec3 normal;
	vec3 reflectance;
};

void Update();
void Draw();
void interpolateLine(float x1, float y1, float x2, float y2, vector<int>& points);
void interpolateSurface(const vector<Pixel> vertexPixels, vector<Pixel>& leftEdge, vector<Pixel>& rightEdge);
void drawPolygon(const vector<Pixel>& leftEdge, const vector<Pixel>& rightEdge, const vec3 color);
void Interpolate(Pixel a, Pixel b, vector<Pixel>& result);
void updateR();
void vertexShader(const vec3& v, Pixel& p);
void PixelShader(const Pixel& p);

int main( int argc, char* argv[] )
{
	LoadTestModel( triangles );
	screen = InitializeSDL( SCREEN_WIDTH, SCREEN_HEIGHT );
	t = SDL_GetTicks();	// Set start value for timer.
	Draw();
	while( NoQuitMessageSDL() )
	{
		Update();
		//Draw();
	}

	SDL_SaveBMP( screen, "screenshot.bmp" );
	return 0;
}

void updateR() {
	R = mat3(cos(theta), 0, sin(theta), 0.0f, 1.0f, 0.0f, -sin(theta), 0.0f, cos(theta));
	Rx = mat3(1, 0, 0, 0, cos(phi), -sin(phi), 0, sin(phi), cos(phi));
}

void Update()
{
	// Compute frame time:
	int t2 = SDL_GetTicks();
	float dt = float(t2-t);
	t = t2;
	if (dt > 3) {
		cout << "Render time: " << dt << " ms." << endl;
	}
	if (dt < 16) {
		Sleep(16-dt);
	}

	Uint8* keystate = SDL_GetKeyState(0);
	bool click = false;

	if( keystate[SDLK_UP] )
	{
		cameraPos += Rx*R*vec3(0, 0, 0.02f);
		click = true;
	}
	if( keystate[SDLK_DOWN] )
	{
		cameraPos += Rx*R*vec3(0, 0, -0.02f);
		click = true;
	}
	if( keystate[SDLK_LEFT] )
	{
		//cameraPos.x -= .1;
		theta += .01;
		updateR();
		click = true;
	}
	if( keystate[SDLK_RIGHT] )
	{
		//cameraPos.x += .1;
		theta -= .01;
		updateR();
		click = true;
	}
	if( keystate[SDLK_w] ) {
		lightPos += vec3(0, 0, 0.02f);
		click = true;
	}
	if( keystate[SDLK_a] ) {
		lightPos += vec3(-0.02f, 0, 0);
		click = true;
	}
	if( keystate[SDLK_s] ) {
		lightPos += vec3(0, 0, -0.02f);
		click = true;
	}
	if( keystate[SDLK_d] ) {
		lightPos += vec3(0.02f, 0, 0);
		click = true;
	}
	if( keystate[SDLK_e] ) {
		lightPos += vec3(0, -0.02f, 0);
		click = true;
	}
	if( keystate[SDLK_q] ) {
		lightPos += vec3(0, 0.02f, 0);
		click = true;
	}
	if( keystate[SDLK_i] ) {
		cameraPos += R*vec3(0, -0.02f, 0);
		click = true;
	}
	if( keystate[SDLK_j] ) {
		cameraPos += R*vec3(-0.02f, 0, 0);
		click = true;
	}
	if( keystate[SDLK_k] ) {
		cameraPos += R*vec3(0, 0.02f, 0);
		click = true;
	}
	if( keystate[SDLK_l] ) {
		cameraPos += R*vec3(0.02f, 0, 0);
		click = true;
	}
	if( keystate[SDLK_u] ) {
		phi += 0.01;
		updateR();
		click = true;
	}
	if( keystate[SDLK_o] ) {
		phi -= 0.01;
		updateR();
		click = true;
	}
	if (click) {
		Draw();
	}
}

double round(double d) {
	return floor(d+0.5);
}

void interpolateLine(Pixel a, Pixel b, vector<Pixel>& points) {
	//int num = ceil(sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2)));
	int num = max(abs(a.x-b.x), abs(a.y-b.y))+1;
	if (num < 2*SCREEN_WIDTH && num > 1) {
		for (int i = 0; i < num; i++) {
			Pixel temp;
			temp.x = round(a.x+(b.x-a.x)*i/(num-1));
			temp.y = round(a.y+(b.y-a.y)*i/(num-1));
			temp.zinv = a.zinv+(b.zinv-a.zinv)*i/float(num-1);
			temp.illumination = a.illumination+i/float(num-1)*(b.illumination-a.illumination);
			//cout << "interpolateLine: " << temp.illumination.x << "   " << temp.illumination.y << "   " << temp.illumination.z << endl;

			points.push_back(temp);
		}
	} else if (num == 1) {
		points.push_back(a);
	}
}

void interpolateSurface(const vector<Pixel> vertexPixels, vector<Pixel>& leftEdge, vector<Pixel>& rightEdge) {
	
	int minRow = min(vertexPixels[0].y, min(vertexPixels[1].y, vertexPixels[2].y));
	int maxRow = max(vertexPixels[0].y, max(vertexPixels[1].y, vertexPixels[2].y));
	int rows = maxRow-minRow+1;

	leftEdge.resize(rows);
	rightEdge.resize(rows);

	for (int i = 0; i < rows; i++) {
		leftEdge[i].x = SCREEN_WIDTH;
		rightEdge[i].x = -SCREEN_WIDTH;
		leftEdge[i].y = minRow+i;
		rightEdge[i].y = minRow+i;
	}


	for (int i = 0; i < 3; i++) {
		vector<Pixel> points;
		interpolateLine(vertexPixels[i], vertexPixels[(i+1)%3], points);
		for (int j = 0; j < points.size(); j++) {
			int y = points[j].y-minRow;
			int x = points[j].x;
			if (x < leftEdge[y].x) {
				leftEdge[y].x = x;
				leftEdge[y].zinv = points[j].zinv;
				leftEdge[y].illumination = points[j].illumination;
			}
			if (x > rightEdge[y].x) {
				rightEdge[y].x = x;
				rightEdge[y].zinv = points[j].zinv;
				rightEdge[y].illumination = points[j].illumination;
			}
		}
	}
}

void drawPolygon(const vector<Pixel>& leftEdge, const vector<Pixel>& rightEdge, const vec3 color) {
	for (int y = 0; y < leftEdge.size(); y++) {
		//cout << leftEdge[y].illumination.x << "   " << leftEdge[y].illumination.y << "   " << leftEdge[y].illumination.z << endl;

		int num = rightEdge[y].x-leftEdge[y].x+1;
		for (int x = 0; x < num; x++) {
			if (0 <= leftEdge[y].y && leftEdge[y].y < SCREEN_HEIGHT) {
				if (0 <= leftEdge[y].x+x && leftEdge[y].x+x < SCREEN_WIDTH) {
					Pixel drawPixel;
					drawPixel.x = leftEdge[y].x+x;
					drawPixel.y = leftEdge[y].y;
					drawPixel.zinv = leftEdge[y].zinv+(rightEdge[y].zinv-leftEdge[y].zinv)*x/(num-1);
					drawPixel.illumination = leftEdge[y].illumination+x/float(num-1)*(rightEdge[y].illumination-leftEdge[y].illumination);
					PixelShader(drawPixel);
				}
			}
		}
	}
}

void vertexShader(const Vertex& v, Pixel& p) {
	vec3 l1 = (v.position-cameraPos)*R*Rx;
	int x1 = int(f*l1.x/l1.z+SCREEN_WIDTH/2);
	int y1 = int(f*l1.y/l1.z+SCREEN_HEIGHT/2);
	p.x = x1;
	p.y = y1;
	p.zinv = 1/float(l1.z);
	vec3 l = lightPos-v.position;
	float angle = max(glm::dot(l, v.normal), 0.0f);
	float test = angle/(4*3.1416*glm::dot(l, l));
	vec3 factor = test*lightPower;
	p.illumination = (factor+indirectLight)*v.reflectance;
	//cout << "vertexShader: " << p.illumination.x << "   " << p.illumination.y << "   " << p.illumination.z << endl;

}

void PixelShader(const Pixel& p) {
	int x = p.x;
	int y = p.y;
	if (p.zinv >= depthBuffer[y][x]) {
		depthBuffer[y][x] = p.zinv;
		PutPixelSDL(screen, x, y, p.illumination);
		//PutPixelSDL(screen, x, y, vec3(1,1,1));
	}
}


void Draw()
{
	SDL_FillRect( screen, 0, 0 );

	if( SDL_MUSTLOCK(screen) )
		SDL_LockSurface(screen);

	for (int y = 0; y < SCREEN_HEIGHT; y++) {
		for (int x = 0; x < SCREEN_WIDTH; x++) {
			depthBuffer[y][x] = 0;
		}
	}
	
	for( int i=0; i<triangles.size(); ++i )
	{
		vector<Vertex> vertices(3);

		vertices[0].position = triangles[i].v0;
		vertices[1].position = triangles[i].v1;
		vertices[2].position = triangles[i].v2;
		for (int j = 0; j < 3; j++) {
			vertices[j].normal = triangles[i].normal;
			vertices[j].reflectance = triangles[i].color;
		}
		/*
		vertices[0].reflectance = vec3(1, 0, 0);
		vertices[1].reflectance = vec3(0, 1, 0);
		vertices[2].reflectance = vec3(0, 0, 1);
		*/
		vector<Pixel> vertexPixels(3);
		vector<Pixel> leftEdge;
		vector<Pixel> rightEdge;
		vec3 color = triangles[i].color;

		bool check = true;
		for (int j = 0; j < 3; j++) {
			Pixel temp;
			vertexShader(vertices[j], temp);
			vertexPixels[j] = temp;
			if (temp.zinv < 0) {
				check = false;
				break;
			}
		}
		if (check) {
			interpolateSurface(vertexPixels, leftEdge, rightEdge);
			drawPolygon(leftEdge, rightEdge, color);
		}

	}
	//cout << "rendering finished" << endl;
	
	if ( SDL_MUSTLOCK(screen) )
		SDL_UnlockSurface(screen);

	SDL_UpdateRect( screen, 0, 0, 0, 0 );
}