#include <iostream>
#include <fstream>
#include <cstdint>
#include <vector>

#include "http/Server.hpp"



class MyRequestHandler : public http::RequestHandler {
	public: void onRequestArrived(){
		std::cout << "onRequestArrived" << std::endl;
	}
 };

int main () {
	MyRequestHandler handler;
	http::Server server(1991);
	server.run(&handler);
	return 0;
}
