#ifndef HTTP_THREAD
#define HTTP_THREAD

#include <thread>
#include <mutex>
#include <csignal>
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <unistd.h>
#include "RequestHandler.hpp"


namespace http {
	class Thread {
		private: std::thread thr; 
		private: std::mutex thr_mutex;
		private: void run(int socket,RequestHandler * handler);
		public: ~Thread();
		public: Thread(int socket,RequestHandler * handler);
	};
}
#endif
