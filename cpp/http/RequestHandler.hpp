#ifndef REQUEST_HANDLER_HPP
#define REQUEST_HANDLER_HPP

#include "Request.hpp"
#include "OutputStream.hpp"

namespace http {
	class RequestHandler
	{
		public: virtual void onRequestArrived(Request &request, OutputStream &out) = 0;
	}; 
}

#endif
