#ifndef REQUEST_HPP
#define REQUEST_HPP

#include <string>
#include <iostream>
#include <map>
#include <algorithm> 
#include <sstream>
#include <vector>
#include "Status.hpp"
#include "InputStream.hpp"


namespace http {
	class Request {
		public: std::string method;
		public: std::string file;
		public: std::string query;
		public: std::string version;
		public: std::map<std::string,std::vector<std::string>> fields;	
		public: std::map<std::string,std::vector<std::string>> headers;
		public: Request(std::string method, std::string file, std::string query, std::string version);
		public: Request(InputStream &in);
		public: void dump();
	}; 
}

#endif
