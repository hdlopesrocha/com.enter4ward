#ifndef SESSION_MANAGER_HPP
#define SESSION_MANAGER_HPP

#include <string>
#include <sstream>
#include <iostream>
#include <map>
#include <vector>
#include "Session.hpp"

namespace http {
	class SessionManager {
		public: std::map<long,Session*> sessions;
		public: SessionManager();
	}; 
}

#endif
