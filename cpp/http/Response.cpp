#include "Response.hpp"

namespace http {

	Response::Response(std::string v, int c){
		version = v;
		chunkSize = 0;
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

		if(chunkSize>0){
			msg << "Transfer-Encoding: chunked\r\n"; 
		}
		else if(content!=NULL){
			msg << "Content-Length: " << contentLength << "\r\n";
		}

		std::string data = msg.str();
		if(!out.writeBytes(data.c_str(),data.length())){
			return false;
		}

		if(!out.writeBytes(CRLF.c_str(),CRLF.length())){
			return false;
		}

		return out.flush();
	}

	void Response::setChunked(int v){
		chunkSize = v;
	}

	void Response::setContentType(std::string t){
		contentType = t;
	}

	bool Response::endChunk(OutputStream &out){
		if(chunkSize>0){
			static std::string end = "0\r\n\r\n";				
			return out.writeBytes(end.c_str(),end.length());
		}
		return false;
	}

	bool Response::sendBody(OutputStream &out){
		if(chunkSize>0){
			for (int i = 0; i < contentLength; i+=chunkSize) {
				unsigned int wr = contentLength - i;
				if(wr > chunkSize) {
					wr = chunkSize;
				}
				
				std::stringstream ss;

				ss << std::hex<<wr << "\r\n";

				std::string data = ss.str();
				std::cout << data;

				if(!out.writeBytes(data.c_str(),data.length())){
					return false;
				}

				if(!out.writeBytes(&content[i],wr)){
					return false;				
				}

				if(!out.writeBytes(CRLF.c_str(),CRLF.length())){
					return false;
				}
			}

		}
		else {
			if(!out.writeBytes(content,contentLength)){
				return false;
			}
		}

		return out.flush();
	}

	void Response::setContent(const char * c, long l){
		content = c;
		contentLength = l;
	}
}

