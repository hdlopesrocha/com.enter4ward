#include "Response.hpp"

namespace http {

	#define MAX_CHUNK_SIZE 8192

	Response::Response(std::string v, int c){
		version = v;
		isChunked = false;
		code = c;
		content = NULL;
		contentLength = 0;
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

	bool Response::sendHeaders(OutputStream &out){
		bool success = true;
		std::stringstream msg;

		msg << version << " " << code << " " <<  statusStrings[code] << "\r\n";
		msg << "Server: Hidrogine/" << VERSION << "\r\n";

		if(contentType.length()>0){
			msg << "Content-Type: " << contentType << "\r\n";
		}

		if(isChunked){
			msg << "Transfer-Encoding: chunked\r\n"; 
		}
		else if(content!=NULL){
			msg << "Content-Length: " << contentLength << "\r\n";
		}

		msg << "\r\n";
		std::string data = msg.str();
		success &= out.writeBytes(data.c_str(),0,data.length());
		success &= out.flush();
		return success;
	}

	void Response::setChunked(bool v){
		isChunked = v;
	}

	void Response::setContentType(std::string t){
		contentType = t;
	}

	bool Response::sendBody(OutputStream &out){
		bool success = true;
		if(isChunked){
			for (int i = 0; i < contentLength; i+=MAX_CHUNK_SIZE) {
				int wr = contentLength - i;
				if(wr > MAX_CHUNK_SIZE){
					wr = MAX_CHUNK_SIZE;
				}
				{
					std::stringstream msg;
					msg << wr << "\r\n";
					std::string data = msg.str();
					success &= out.writeBytes(data.c_str(),0,data.length());
				}
				success &= out.writeBytes(content,i,wr);
				{
					std::string data = "\r\n";
					success &= out.writeBytes(data.c_str(),0,data.length());
				}
			}
		}
		else {
			success &= out.writeBytes(content,0,contentLength);
		}

		success &= out.flush();
		return success;
	}

	void Response::setContent(const char * c, long l){
		content = c;
		contentLength = l;
	}

}

