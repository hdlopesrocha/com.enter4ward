#ifndef REQUEST_HANDLER_HPP
#define REQUEST_HANDLER_HPP

#include "Request.hpp"
#include "OutputStream.hpp"
#include "InputStream.hpp"

namespace http {
	class RequestHandler
	{
		public: virtual void onRequestArrived(InputStream &in, OutputStream &out) = 0;
	}; 
}

#endif
