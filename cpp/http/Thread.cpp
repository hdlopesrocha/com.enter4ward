#include "Thread.hpp"

namespace http{

	void Thread::run(int socket,RequestHandler * handler){
		std::string line = "";
		InputStream inputStream(socket);

		std::cout << "Socket: " << socket<< std::endl;
		while(inputStream.readLine(line)!=-1){
			std::cout << "|  " << line << std::endl;
			line.clear();
		}

		std::cout << "XXX" << std::endl;


		handler->onRequestArrived();
		::close(socket);
		delete this;
	}

	Thread::~Thread(){
		thr_mutex.lock();
		thr.detach();
		thr_mutex.unlock();
	}


	Thread::Thread(int socket,RequestHandler * handler){
		thr_mutex.lock();
		thr = std::thread(&Thread::run, this, socket,handler);
		thr_mutex.unlock();
	}

}

