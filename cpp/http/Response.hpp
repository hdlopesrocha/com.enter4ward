#ifndef RESPONSE_HPP
#define RESPONSE_HPP

#include <string>
#include <sstream>
#include <iostream>
#include <map>
#include <vector>
#include "Status.hpp"
#include "OutputStream.hpp"

namespace http {
	class Response {
		public: int code;
		public:	std::string version;
		public: char * content;
		public: std::map<std::string,std::vector<std::string>> headers;
		public: Response(std::string version,int code);
		public: void addHeader(std::string key, std::string value);
		public: void removeHeader(std::string key);
		public: std::vector<std::string> getHeaders(std::string key);
		public: bool send(OutputStream &out);
	}; 
}

#endif
