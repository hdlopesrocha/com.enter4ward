#ifndef unilib_lang_HttpThread
#define unilib_lang_HttpThread
#include "WS.hpp"
#include <stdio.h>
#include <stdlib.h>

using namespace std;

namespace unilib{

	void HttpThread::run(int socket,HttpServer * server){
		string message="";
		char msg[BUFFERSZ];
		int bytes_recv = BUFFERSZ;

		while(bytes_recv==BUFFERSZ){
			memset(msg,0,BUFFERSZ);
			bytes_recv = recv(socket,msg,BUFFERSZ,0);
			message += string(msg);
		}

		HttpRequest request = HttpRequest(message,server);
		HttpResponse response = HttpResponse(server);
		
		if(request.IsValid()){
//			cout << "========================================================================================"<<endl << message;

			HttpPage * page=NULL;
								
			long event=0;
			string file = request.GetFile().substr(1);
			stringstream ss_page(file);
			string page_split;

			if(file.length()==0)
				page = server->root;
			
			while (getline(ss_page,page_split,'/')) {
				if(page_split.compare("event")==0){
					getline(ss_page,page_split,'/');
					event=atol(page_split.c_str());
					break;
				}
			
				if(page==NULL){
					page = server->Resources->Get(page_split);
				}
				else {
					page = page->GetChild(page_split);
				}
				if(page==NULL)
					break;
			}
			ss_page.clear();




			// COOKIE
			
			if(event!=0){
				// 1 - Get event number
				// 2 - Get event from event list
				// 3 - Execute Event
				// 4 - It may process a page
				std::string number;
				std::stringstream strstream;
				strstream << event;
				strstream >> number;


				response.Echo("event_handler "+number);		
				response.Send(socket);
				
			}				
			else if(page!=NULL){
				page->Process(&request,&response);
				response.Send(socket);
			}
			else if(request.GetUrl().length()>0) {
				response.SendFile(socket,request.GetUrl().substr(1));
			}
			
			
		}

		::close(socket);

		delete this;
	}

	HttpThread::~HttpThread(){
		thr_mutex.lock();
		thr.detach();
		thr_mutex.unlock();
	}


	HttpThread::HttpThread(int s, HttpServer * server){
		thr_mutex.lock();
		thr = thread(&HttpThread::run, this, s,server);
		thr_mutex.unlock();
	}

}

#endif
