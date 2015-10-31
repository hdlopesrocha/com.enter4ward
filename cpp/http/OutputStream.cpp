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
		if(cursor>0){
			int w = write(socket, buffer,cursor);
			cursor = 0;
			return w > 0;
		}
		return true;
	}

	bool OutputStream::writeBytes(const char *c, int l){
		
		if(l < OS_BUFFER_SIZE){
			if(cursor + l >= OS_BUFFER_SIZE) {
				if(!flush()){
					return false;
				}
			}
			for(int i=0; i < l;++i){
				if(!writeByte(c[i])){
					return false;
				}
			}
		}
		else {
			if(!flush()){
				return false;
			}
			write(socket, c, l);
		}

		return true;
	}



	OutputStream::OutputStream(int s){
		socket = s;
		cursor = 0;	
	}

}

