#include <iostream>
#include <fstream>
#include <cstdint>
#include <unistd.h>
#include <vector>
#include <string.h>
#include "http/Server.hpp"
#include "pixel.hpp"
#include "frame.hpp"
#include <IL/il.h>
#include <IL/ilu.h>

// ffmpeg -f video4linux2 -i /dev/video0 -vf scale=1024:768 -an -qscale:v 2 -f mjpeg http://localhost:1991/stream/test.mjpg
// ffplay http://localhost:1991/stream/test.mjpg

std::map<std::string,Frame> frameBuffer;

std::mutex frameMutex;

#define STREAM_BLOCK_SIZE 32768


void receiveStream(http::InputStream &in, http::Request &request){
	http::Response response(HTTP_1_1, STATUS_OK);
	
	std::vector<char> tempFrame;

	
	while(true){
		std::string lenStr;
		if(in.readLine(lenStr)==-1){
			break;
		}
		
		unsigned int len;   
	    	std::stringstream ss;
	    	ss << std::hex << lenStr;
	    	ss >> len;

		//std::cout << len << std::endl;
 	
		for (int i = 0; i < len; ++i){
			tempFrame.push_back(in.readByte());
		}
	
	    	if(len<STREAM_BLOCK_SIZE) {
	    		frameMutex.lock();
 			frameBuffer[request.file].setFrame(tempFrame);
	    		frameMutex.unlock();
   			tempFrame.clear();
	   	}

		if(in.readLine(lenStr)==-1){
			break;
		}
	}
}

void sendStream(http::OutputStream &out, http::Request &request){
	http::Response response(HTTP_1_1, STATUS_OK);
	response.setChunked(STREAM_BLOCK_SIZE);
	response.setContentType("image/jpeg");
	response.sendHeaders(out);

	bool valid = true;
	while(valid){
		frameMutex.lock();
		Frame frame = frameBuffer[request.file];
		frameMutex.unlock();
		response.setContent(&frame.currentFrame[0],frame.currentFrame.size());
		valid = response.sendBody(out);

		usleep(33333);
	}
	response.endChunk(out);
}


void notFound(http::InputStream &in, http::OutputStream &out){
	http::Response response(HTTP_1_1, STATUS_NOT_FOUND);
	std::string content = "<h1>Page not found!</h1>";

	response.setContentType("text/html");
	response.setContent(content.c_str(), content.length());

	response.sendHeaders(out);
	response.sendBody(out);
}


void index(http::InputStream &in, http::OutputStream &out){
	http::Response response(HTTP_1_1, STATUS_OK);
	std::string content = "<h1>Hello World!</h1>";

// ERR_CONTENT_LENGTH_MISMATCH
	response.setContentType("text/html");
	response.setChunked(1024);
	response.sendHeaders(out);

	response.setContent(content.c_str(), content.length());
	response.sendBody(out);
	response.endChunk(out);
}

class MyRequestHandler : public http::RequestHandler {
	public: void onRequestArrived(http::InputStream &in, http::OutputStream &out){
		//request.dump();
		http::Request request(in);
		
		if(request.method.compare("GET")==0){
			if(request.file.compare("/")==0){
				index(in,out);
			}
			else if(request.file.substr(0,7).compare("/stream")==0){
				sendStream(out,request);
			}
			else {
				notFound(in,out);
			}
		} else if(request.method.compare("POST")==0){
			if(request.file.substr(0,7).compare("/stream")==0){
				receiveStream(in,request);
			}
		}
	}
 };





void devilTest(){
	ilInit();	
 	FILE *file = fopen("test.jpg", "rb");
 	fseek(file, 0, SEEK_END);
	ILuint fileSize = ftell(file);
	char * bytes = (char*) malloc(fileSize);
	fseek(file, 0, SEEK_SET);
	fread(bytes, 1, fileSize, file);
	fclose(file);
	std::vector<char> img;
	//overlay(bytes,fileSize,img);

}


int main () {
	devilTest();
	MyRequestHandler handler;
	http::Server server(1991);
	server.run(&handler);
	return 0;
}
