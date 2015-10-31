#ifndef RESPONSE_HPP
#define RESPONSE_HPP

#include <string>
#include <sstream>
#include <iostream>
#include <map>
#include <vector>
#include "Status.hpp"
#include "Server.hpp"
#include "OutputStream.hpp"

namespace http {
	static std::string CRLF = "\r\n"; 


	class Response {
		public: int code;
		public:	std::string version;
		public:	std::string contentType;
		public: long contentLength;
		public: int chunkSize;
 		public: const char * content;
		public: std::map<std::string,std::vector<std::string>> headers;
		public: Response(std::string version,int code);

		public: void addHeader(std::string key, std::string value);
		public: void removeHeader(std::string key);
		public: std::vector<std::string> getHeaders(std::string key);
		public: bool sendBody(OutputStream &out);
		public: bool endChunk(OutputStream &out);
		public: bool sendHeaders(OutputStream &out);
		public: void setContent(const char * c, long l);
		public:	void setContentType(std::string type);
		public: void setChunked(int v);
		public: void setContinuous(bool v);
		


	}; 
}

#endif
