#include "OutputStream.hpp"

namespace http{

	bool OutputStream::writeByte(char c){				
		if(cursor<OS_BUFFER_SIZE){
			buffer[cursor++] = c;
		} else {
			int w = flush();
			if(w<=0){
				return false;
			}
			buffer[cursor++] = c;
		}
		return true;
	}

	bool OutputStream::flush(){
		int w = write(socket, buffer,cursor);
		cursor = 0;
		return w > 0;
	}

	bool OutputStream::writeBytes(const char *c,int o, int l){
		
		if(l < OS_BUFFER_SIZE){
			if(cursor + l >= OS_BUFFER_SIZE) {
				if(!flush()){
					return false;
				}
			}
			int end = o+l;
			for(int i=o; i < end;++i){
				if(!writeByte(c[i])){
					return false;
				}
			}
		}
		else {
			if(!flush()){
				return false;
			}
			write(socket, c+o, l);
		}

		return true;
	}



	OutputStream::OutputStream(int s){
		socket = s;
		cursor = 0;	
	}

}

