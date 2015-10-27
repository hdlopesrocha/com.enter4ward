#include "InputStream.hpp"

namespace http{

	int InputStream::readByte(){				
		if(available>=0 && cursor>=available){
			cursor = 0;
			available = read(socket, buffer, IS_BUFFER_SIZE);		
		}
		if(available<=0){
			return -1;
		}

		return buffer[cursor++];
	}


	int InputStream::readLine(std::string &line){
		int len = 0;		
		bool cr = false;
		while (true) {
			int b = readByte();
			if (b == -1) {
				return len==0 ? -1 : len;
			}
			if (b == '\r') {
				if (cr) {
					++len;
					line += '\r'; // add the previous \r
				} else {
					cr = true;
				}
			} else if (cr && b == '\n') {
				return len;
			} else {
				++len;
				line += (char) b;
				cr = false;
			}
		}
		return -1;
	}



	InputStream::InputStream(int s){
		socket = s;
		cursor = 0;
		available = 0;		
	}

}

