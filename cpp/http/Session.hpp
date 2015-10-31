#ifndef SESSION_HPP
#define SESSION_HPP

#include <string>
#include <sstream>
#include <iostream>
#include <map>
#include <vector>

namespace http {
	class Session {
		public: std::map<std::string,void*> objects;
		public: Session();
	}; 
}

#endif
