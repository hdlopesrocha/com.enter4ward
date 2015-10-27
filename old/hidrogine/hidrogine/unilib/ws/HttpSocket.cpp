#ifndef unilib_lang_HttpSocket
#define unilib_lang_HttpSocket
#include "WS.hpp"

namespace unilib {

	HttpSocket::HttpSocket(){
	
	}


	HttpSocket::HttpSocket(int port){
		if((_socketfd = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP))<0)
	    	cout << "error: socket" << endl;
		int yes=1;
		//char yes='1'; // use this under Solaris

		if (setsockopt(_socketfd, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(yes)) == -1) {
				cout << "error: setsockopt"<< endl;

		}

		_addr.sin_family = AF_INET;
		_addr.sin_addr.s_addr = INADDR_ANY; /* ask system to assign a port no.*/
		_addr.sin_port = htons(port);
		_length = sizeof(struct sockaddr_in);


		if(::bind(_socketfd, (sockaddr*)&_addr, sizeof (_addr))<0)
			cout << "error: bind"<< endl;
		

		_length = sizeof(_addr);
		if(getsockname(_socketfd, (sockaddr*)&_addr, &_length)<0)
			cout << "error: getsocketname" << endl;

		if(::listen(_socketfd,1) < 0)
			cout << "error: listen" << endl;
	}
	
	int HttpSocket::Accept(){
		int socket=0;
		if((socket = ::accept(_socketfd, (struct sockaddr *)&_addr,&_length))<0)
			cout << "error: accept" << endl;
		return socket;		
	}

	void HttpSocket::Close(){
		::close(_socketfd);
	}

}

#endif

