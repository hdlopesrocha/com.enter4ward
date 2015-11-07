#ifndef FRAME_UTIL
#define FRAME_UTIL

#include <vector>

class  Frame
{
	public: std::vector<char> currentFrame;
	public: std::vector<char> oldFrame;
	public: long age;



	public: Frame(){
		age = -1;
	}


	public: void setFrame(std::vector<char> frame){
		if(age==-1){
			oldFrame = currentFrame = frame;
		}
		else {
			if(age >= 300){
				oldFrame = currentFrame;
				age = 0;			
			}
			currentFrame = frame;
		}
		++age;
	}
	
};





#endif



