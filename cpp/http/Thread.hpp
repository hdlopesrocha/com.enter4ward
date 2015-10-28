#ifndef HTTP_THREAD_HPP
#define HTTP_THREAD_HPP

#include <thread>
#include <mutex>
#include <sstream>
#include <csignal>
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <functional> 
#include <cctype>
#include <locale>
#include <algorithm> 
#include <unistd.h>
#include "RequestHandler.hpp"
#include "Request.hpp"
#include "InputStream.hpp"

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
