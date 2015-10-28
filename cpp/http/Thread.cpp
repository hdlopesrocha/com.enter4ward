#include "Thread.hpp"

namespace http{

	void setAttributes(std::string &a,Request &request){
	   	std::stringstream ss_attr(a);
		std::string temp, key , value;
		while(getline(ss_attr, temp, '&')){				
	   		std::stringstream ss_equal(temp);
			getline(ss_equal, key, '=');
			getline(ss_equal, value, '=');
			request.fields[key].push_back(value);
			ss_equal.clear();
		}
		ss_attr.clear();
	}

// trim from start
static inline std::string &ltrim(std::string &s) {
        s.erase(s.begin(), std::find_if(s.begin(), s.end(), std::not1(std::ptr_fun<int, int>(std::isspace))));
        return s;
}

// trim from end
static inline std::string &rtrim(std::string &s) {
        s.erase(std::find_if(s.rbegin(), s.rend(), std::not1(std::ptr_fun<int, int>(std::isspace))).base(), s.end());
        return s;
}

// trim from both ends
static inline std::string &trim(std::string &s) {
        return ltrim(rtrim(s));
}

	void Thread::run(int socket,RequestHandler * handler){
		std::string line = "";
		InputStream inputStream(socket);

		std::string file,method,query,version;
		std::string temp, key , value;



		if(inputStream.readLine(line)!=-1){
		 	std::stringstream ss_line(line);		   	
			getline(ss_line,method,' ');
			std::string url;
			getline(ss_line,url,' ');
			getline(ss_line,version,' ');
		   	std::stringstream ss_url(url);
			getline(ss_url, file, '?');
			getline(ss_url, query, '\n');
			ss_url.clear();
			ss_line.clear();
			line.clear();
		}

		Request request(method,file,query,version);
		setAttributes(query,request);

		while(inputStream.readLine(line)>0){
		 	std::stringstream ss_line(line);		   	
			getline(ss_line,key,':');
			getline(ss_line,value,':');
			value = trim(value);
			request.headers[key].push_back(value);
			line.clear();
		}

		handler->onRequestArrived(request);
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

