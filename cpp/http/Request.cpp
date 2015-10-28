#include "Request.hpp"

namespace http {
	Request::Request(std::string m, std::string f, std::string q, std::string v){
		method = m;
		file = f;
		query = q;
		version = v;
	}
}

