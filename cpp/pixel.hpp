#ifndef PIXEL_UTIL
#define PIXEL_UTIL

class  Pixel
{
	public: char data[3];

	public: Pixel(){
		data[0] = data[1] = data[2] = 0;
	}

	public: Pixel(char r, char g, char b){
		data[0] = r;
		data[1] = g;
		data[2] = b;
	}

	
};





#endif



