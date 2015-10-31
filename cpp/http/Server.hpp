#ifndef HTTP_SERVER_HPP
#define HTTP_SERVER_HPP

#define VERSION std::string("1.1.2")
#include <sys/types.h>       // For data types
#include <sys/socket.h>      // For socket(), connect(), send(), and recv()
#include <netdb.h>           // For gethostbyname()
#include <arpa/inet.h>       // For inet_addr()
#include <unistd.h>          // For close()
#include <netinet/in.h>      // For sockaddr_in
#include <iostream>
#include <string>


#include "RequestHandler.hpp"
#include "Thread.hpp"
#include "Response.hpp"


namespace http {

	class Server
	{
		private: int port;
		private: int socketFileDescriptor;
		public: Server(int port);
		public: void close();
		public: void run(RequestHandler * handler);

	}; 

}





#endif
