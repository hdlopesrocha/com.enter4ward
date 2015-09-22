#define DEBUG

#include <stdio.h>
#include <stdlib.h>
#include "framework/bitmap.hpp"

using namespace std;
using namespace framework;

#define PI 3.14159265359
int main(int argc, char **argv){
	BITMAP * bmp = new BITMAP(1280,600);

	bmp->Clear(COLOR(0x10,0x10,0x10));			// GRAY

	// COLORS
/*	float dim=0.005f;
	for(int i=0 ; i< bmp->GetHeight() ; ++i){
		for(int j=0 ; j< bmp->GetWidth() ; ++j){
			bmp->PutPixel(i,j,COLOR((int) (128+128*cos(i*dim)), (int)(128+128*sin(j*dim)), (int)(128+ 128*cos(i*dim)*cos(j*dim))));
		}		
	}
*/

	// CO-SINE FUNCTION
	bmp->PlotReset();
	float fou, freq=2;
	for(float t=0; t < 1; t+=1.0/1280){
		fou = 0;
		for(int k=0 ; k < 959 ; ++k)
			fou +=  (1.0/(2*k+1))*sin((2*k+1)*t*2*PI*freq);

		bmp->PixelPlot(300+280*fou,t*1280,COLOR(0xFF,0xFF,0xFF));
	}



	bmp->PutPixel(1,1,COLOR(0xFF,0xFF,0xFF));		// WHITE
	bmp->PutPixel(1,2,COLOR(0xFF,0xFF,0xFF));		// WHITE
	bmp->PutPixel(1,3,COLOR(0xFF,0xFF,0xFF));		// WHITE
	bmp->PutPixel(1,5,COLOR(0xFF,0xFF,0xFF));		// WHITE
	bmp->PutPixel(1,6,COLOR(0xFF,0xFF,0xFF));		// WHITE
	bmp->PutPixel(1,8,COLOR(0xFF,0xFF,0xFF));		// WHITE
	bmp->PutPixel(1,10,COLOR(0xFF,0xFF,0xFF));		// WHITE
	bmp->PutPixel(1,11,COLOR(0xFF,0xFF,0xFF));		// WHITE


	bmp->PutPixel(2,1,COLOR(0xFF,0xFF,0xFF));		// WHITE
	bmp->PutPixel(2,3,COLOR(0xFF,0xFF,0xFF));		// WHITE
	bmp->PutPixel(2,5,COLOR(0xFF,0xFF,0xFF));		// WHITE
	bmp->PutPixel(2,8,COLOR(0xFF,0xFF,0xFF));		// WHITE
	bmp->PutPixel(2,10,COLOR(0xFF,0xFF,0xFF));		// WHITE
	bmp->PutPixel(2,12,COLOR(0xFF,0xFF,0xFF));		// WHITE

	bmp->PutPixel(3,1,COLOR(0xFF,0xFF,0xFF));		// WHITE
	bmp->PutPixel(3,3,COLOR(0xFF,0xFF,0xFF));		// WHITE
	bmp->PutPixel(3,5,COLOR(0xFF,0xFF,0xFF));		// WHITE
	bmp->PutPixel(3,8,COLOR(0xFF,0xFF,0xFF));		// WHITE
	bmp->PutPixel(3,10,COLOR(0xFF,0xFF,0xFF));		// WHITE
	bmp->PutPixel(3,11,COLOR(0xFF,0xFF,0xFF));		// WHITE

	bmp->PutPixel(4,5,COLOR(0xFF,0xFF,0xFF));		// WHITE
	bmp->PutPixel(4,10,COLOR(0xFF,0xFF,0xFF));		// WHITE

	bmp->PutPixel(5,5,COLOR(0xFF,0xFF,0xFF));		// WHITE
	bmp->PutPixel(5,8,COLOR(0xFF,0xFF,0xFF));		// WHITE
	bmp->PutPixel(5,10,COLOR(0xFF,0xFF,0xFF));		// WHITE


	bmp->Save("hello.bmp");
	bmp = new BITMAP("hello.bmp");


	return 0;
}
