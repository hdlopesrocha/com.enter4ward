#ifndef OUTPUT_STREAM_HPP
#define OUTPUT_STREAM_HPP

#include <thread>
#include <mutex>
#include <csignal>
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <unistd.h>

#define OS_BUFFER_SIZE 2048

namespace http {
	class OutputStream {
		private: int socket;
		private: char buffer[OS_BUFFER_SIZE];
		private: int cursor;
		
		public: OutputStream(int s);
		public: bool writeBytes(const char * c, int l);
		public: bool writeByte(char c);
		public: bool flush();
	};
}
#endif
