#include <iostream>
#include <fstream>
#include <cstdint>
#include <vector>
#include "http/Server.hpp"

class MyRequestHandler : public http::RequestHandler {
	public: void onRequestArrived(http::Request &request, http::OutputStream &out){
		request.dump();
		http::Response response(HTTP_1_1, STATUS_OK);
		
		std::string content = "<h1>Hello World!</h1>";
		response.setContentType("text/html");
		response.setChunked(true);
		
		response.sendHeaders(out);
		
		response.setContent(content.c_str(), content.length());
		response.sendBody(out);
	}
 };

int main () {	
	MyRequestHandler handler;
	http::Server server(1991);
	server.run(&handler);
	return 0;
}
