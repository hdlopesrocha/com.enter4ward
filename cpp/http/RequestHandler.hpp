#ifndef REQUEST_HANDLER_HPP
#define REQUEST_HANDLER_HPP

#include "Request.hpp"

namespace http {
	class RequestHandler
	{
		public: virtual void onRequestArrived(Request &request) = 0;
	}; 
}

#endif
