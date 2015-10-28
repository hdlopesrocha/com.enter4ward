#include <iostream>
#include <fstream>
#include <cstdint>
#include <vector>

#include "http/Server.hpp"



class MyRequestHandler : public http::RequestHandler {
	public: void onRequestArrived(http::Request &request){
		std::cout << request.method << " "<< request.file << std::endl;
		std::cout << "=== fields ==="<< std::endl;
		for (auto i : request.fields){
    		for (auto j : i.second){
    			std::cout << "{" << i.first << "," << j << "}" << std::endl;
    		}
		}

		std::cout << "=== headers ==="<< std::endl;
		for (auto i : request.headers){
    		for (auto j : i.second){
    			std::cout << "{" << i.first << "," << j << "}" << std::endl;
    		}
		}
	}
 };

int main () {
	MyRequestHandler handler;
	http::Server server(1991);
	server.run(&handler);
	return 0;
}
