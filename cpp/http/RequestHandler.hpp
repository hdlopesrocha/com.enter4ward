#ifndef REQUEST_HANDLER_HPP
#define REQUEST_HANDLER_HPP

namespace http {
	class RequestHandler
	{
		public: virtual void onRequestArrived() = 0;
	}; 
}

#endif
