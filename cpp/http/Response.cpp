#include "Response.hpp"

namespace http {
	Response::Response(std::string v, int c){
		version = v;
		code = c;
		content = NULL;
	}

	void Response::addHeader(std::string key, std::string value){
		headers[key].push_back(value);
	}


	void Response::removeHeader(std::string key){
		std::map<std::string,std::vector<std::string>>::iterator it = headers.find(key);
  		headers.erase (it);   
	}

	std::vector<std::string> Response::getHeaders(std::string key){
		return headers[key];
	}

	bool Response::send(OutputStream &out){
		bool success = true;
		std::stringstream msg;

		msg << version << " " << code << " " <<  statusStrings[code] <<"\r\n";
		

		std::string data = msg.str();
		success &= out.writeBytes(data.c_str(),0,data.length());
		success &= out.flush();
		return success;
	}


}

