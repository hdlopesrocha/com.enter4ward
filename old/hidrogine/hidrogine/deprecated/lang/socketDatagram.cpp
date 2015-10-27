#ifndef LANG_SOCKET_DGRAM
#define LANG_SOCKET_DGRAM

// SIMPLE SOCKETS by hdlopesrocha

#include <sys/types.h>       // For data types
#include <sys/socket.h>      // For socket(), connect(), send(), and recv()
#include <netdb.h>           // For gethostbyname()
#include <arpa/inet.h>       // For inet_addr()
#include <unistd.h>          // For close()
#include <netinet/in.h>      // For sockaddr_in
#include <string.h>
#include <string>
#include <sstream>
#include <iostream>

using namespace std;
#define BUFFERSZ 1024
namespace lang {
	class SocketDatagram
	{
		private: int _socket;
 		private: struct sockaddr_in _addr;
 		private: socklen_t _length;
	
		public: SocketDatagram(){
			_socket = socket(AF_INET, SOCK_DGRAM, 0);
		}
		
		public: void find(const char * name, int port){
			struct hostent * other_host;
			/* procura maquina pelo IP */
		    if((other_host = gethostbyname(name))==NULL)
		    	cout << "error: gethostbyname" << endl;

			/* prepara o endereco do servidor */
		    _addr.sin_family = AF_INET;       			 					// tipo do socket
		   	_addr.sin_port = htons(port);   								// porta
		    _addr.sin_addr = *((struct in_addr *)other_host->h_addr);	// endereco da maquina
		}

		public: virtual void bind(int port){
			_addr.sin_family = AF_INET;
			_addr.sin_addr.s_addr = INADDR_ANY; /* ask system to assign a port no.*/
			_addr.sin_port = htons(port);
			
			if(::bind(_socket, (sockaddr*)&_addr, sizeof (_addr))<0)
				cout << "error: bind"<< endl;
			

			_length = sizeof(_addr);
			getsockname(_socket, (sockaddr*)&_addr, &_length);
		}

		public: virtual ssize_t write(string s){
			return sendto(_socket, s.c_str(), s.size()+1, 0, (struct sockaddr *)&(_addr), sizeof(struct sockaddr));
		}

		public: virtual string read(){
			socklen_t addr_len = sizeof(struct sockaddr);
			char msg[BUFFERSZ];
			memset(msg,0,BUFFERSZ);
			ssize_t bytes_recieved = recvfrom(_socket,msg,BUFFERSZ,0,(struct sockaddr *)&_addr, &addr_len);
			return string(msg);
		}
	}; 
}

#endif

