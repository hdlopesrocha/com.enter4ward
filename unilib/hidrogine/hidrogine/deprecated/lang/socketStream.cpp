#ifndef LANG_SOCKET_STREAM
#define LANG_SOCKET_STREAM

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
	class SocketStream
	{
		private: int _socket;
 		private: struct sockaddr_in _addr;
 		private: socklen_t _length;

	
		public: SocketStream(){
			if((_socket = socket(AF_INET, SOCK_STREAM, 0))<0)
		    	cout << "error: socket" << endl;
		}
		
		public: void find(const char * name, int port){
			SocketStream s = SocketStream();
			struct hostent * other_host;
			/* procura maquina pelo IP */
		    if((other_host = gethostbyname(name))==NULL)
		    	cout << "error: gethostbyname" << endl;

			/* prepara o endereco do servidor */
		    _addr.sin_family = AF_INET;       			 					// tipo do socket
		   	_addr.sin_port = htons(port);   								// porta
		   	_addr.sin_addr = *((struct in_addr *)other_host->h_addr);	// endereco da maquina

			if (connect(_socket, (struct sockaddr *)&(_addr),sizeof(struct sockaddr)) < 0)
				cout << "error: connect"<< endl;

		}

		public: virtual void bind(int port){
			_addr.sin_family = AF_INET;
			_addr.sin_addr.s_addr = INADDR_ANY; /* ask system to assign a port no.*/
			_addr.sin_port = htons(port);
			_length = sizeof(struct sockaddr_in);


			if(::bind(_socket, (sockaddr*)&_addr, sizeof (_addr))<0)
				cout << "error: bind"<< endl;
			

			_length = sizeof(_addr);
			if(getsockname(_socket, (sockaddr*)&_addr, &_length)<0)
				cout << "error: getsocketname" << endl;

			if(listen(_socket,1) < 0)
				cout << "error: listen" << endl;

			if((_socket = accept(_socket, (struct sockaddr *)&_addr,&_length))<0)
				cout << "error: accept" << endl;
		}

		public: virtual ssize_t write(string s){
			return send(_socket, s.c_str(), s.size()+1,0);
		}

		public: virtual string read(){
			socklen_t addr_len = sizeof(struct sockaddr);
			char msg[BUFFERSZ];
			memset(msg,0,BUFFERSZ);
			recv(_socket,msg,BUFFERSZ,0);
			return string(msg);
		}
	}; 
}

#endif

