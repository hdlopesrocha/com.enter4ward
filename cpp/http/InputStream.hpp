#ifndef INPUT_STREAM
#define INPUT_STREAM

#include <thread>
#include <mutex>
#include <csignal>
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <unistd.h>
#include "RequestHandler.hpp"

#define IS_BUFFER_SIZE 2048

namespace http {
	class InputStream {
		private: int socket;
		private: char buffer[IS_BUFFER_SIZE];
		private: int cursor;
		private: int available;

		public: InputStream(int s);
		public: int readLine(std::string &line);
		public: int readByte();
	};
}
#endif
