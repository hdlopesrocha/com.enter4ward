#include "Server.hpp"

namespace http {

	Server::Server(int p){
		port = p;
		std::cout << "    __    _     __                 _" <<std::endl;
		std::cout << "   / /_  (_)___/ /________  ____ _(_)___  ___"<<std::endl;
		std::cout << "  / __ \\/ / __  / ___/ __ \\/ __ `/ / __ \\/ _ \\"<<std::endl;
		std::cout << " / / / / / /_/ / /  / /_/ / /_/ / / / / /  __/"<<std::endl;
		std::cout << "/_/ /_/_/\\__,_/_/   \\____/\\__, /_/_/ /_/\\___/"<<std::endl;
		std::cout << "                         /____/  web service"<<std::endl;
		std::cout << "Language: C++" <<std::endl;
		std::cout << "Version: "<< VERSION <<std::endl;
		std::cout << "Developer: hdlopesrocha" << std::endl;
		std::cout << "Port: " << p << std::endl;

	}

	void Server::close(){
		::close(socketFileDescriptor);
	}
	

	void Server::run(RequestHandler * handler){
 		struct sockaddr_in inAddress;
 		socklen_t addressLength;

		if((socketFileDescriptor = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP))<0)
	    	std::cout << "error: socket" << std::endl;
		int yes=1;
		//char yes='1'; // use this under Solaris

		if (setsockopt(socketFileDescriptor, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(yes)) == -1) {
				std::cout << "error: setsockopt"<< std::endl;

		}

		inAddress.sin_family = AF_INET;
		inAddress.sin_addr.s_addr = INADDR_ANY; /* ask system to assign a port no.*/
		inAddress.sin_port = htons(port);
		addressLength = sizeof(struct sockaddr_in);


		if(::bind(socketFileDescriptor, (sockaddr*)&inAddress, sizeof (inAddress))<0)
			std::cout << "error: bind"<< std::endl;
		

		addressLength = sizeof(inAddress);
		if(getsockname(socketFileDescriptor, (sockaddr*)&inAddress, &addressLength)<0)
			std::cout << "error: getsocketname" << std::endl;

		if(::listen(socketFileDescriptor,1) < 0)
			std::cout << "error: listen" << std::endl;

		while(true){
			int socket=0;
			if((socket = ::accept(socketFileDescriptor, (struct sockaddr *)&inAddress,&addressLength))<0)
				std::cout << "error: accept" << std::endl;

			new Thread(socket, handler);	// deletes itself			

		}		
	}
}
