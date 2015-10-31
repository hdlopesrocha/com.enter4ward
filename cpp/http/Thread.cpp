#include "Thread.hpp"

namespace http{


	void Thread::run(int socket,RequestHandler * handler){
		InputStream inputStream(socket);
		OutputStream outputStream(socket);

		handler->onRequestArrived(inputStream,outputStream);
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

