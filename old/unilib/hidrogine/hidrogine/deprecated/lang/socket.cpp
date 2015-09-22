#ifndef LANG_SOCKET
#define LANG_SOCKET


#include <sys/types.h>       // For data types
#include <sys/socket.h>      // For socket(), connect(), send(), and recv()
#include <netdb.h>           // For gethostbyname()
#include <arpa/inet.h>       // For inet_addr()
#include <unistd.h>          // For close()
#include <netinet/in.h>      // For sockaddr_in

namespace lang {
	class Socket
	{

		public: Socket(){
		}
		
		public: virtual void bind(int port)=0;
		public: virtual void find(const char * name, int port)=0;

	}; 



}

#endif

