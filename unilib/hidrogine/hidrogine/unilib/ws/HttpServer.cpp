#ifndef unilib_lang_HttpServer
#define unilib_lang_HttpServer
#include "WS.hpp"
#define COOKIE_TIME 3600

namespace unilib{

	void HttpServer::SetRoot(HttpPage * p){
		root = p;
	}

	Cookie * HttpServer::GetCookie(string& cookieID){
		return Cookies->Get(cookieID);
	}

	Cookie * HttpServer::GenerateCookie(){
		Cookie * cookie = NULL;
		
		while(cookie==NULL){
			string s= String::Random(32);
			while(Cookies->Get(s)!=NULL){
				s = String::Random(32);
				cout << "Cookie Hit!" << endl;
			}
			cookie = new Cookie(s,COOKIE_TIME);

			Cookie * replacedCookie = Cookies->Put(s,cookie);
			if(replacedCookie!=NULL){
				delete Cookies->Put(s,replacedCookie);
				cookie=NULL;
				cout << "Play Euro-Millions, today is your day!"<< endl;
			}
		
		
		}
		time_t key = time(0) + cookie->GetMaxAge();
		cq_lock.lock();
		queue<Cookie*> * cookies = cookieQueue->Get(key);
		if(cookies==NULL){
			cookies = new queue<Cookie*>();
			cookieQueue->Put(key,cookies);
		}
		cookies->push(cookie);
		cq_lock.unlock();
		return cookie;
	}

	/* =======================*/

	void HttpServer::GarbageCookies(){
		while(true){
			sleep(2);
//			cout << "Cookies: "<< Cookies->Size() << endl;
			time_t currentTime = time(0);

			while(cookieQueue->Size()>0){
				Entry<time_t,queue<Cookie*>*> entry = cookieQueue->Leftest();

				if(entry.Key<= currentTime){
//					cout << entry.Key<< " | " <<currentTime<< endl;
					cq_lock.lock();
					queue<Cookie*> * cookies = entry.Value;
					while(cookies->size()>0){
						Cookie * old = cookies->front();
						Cookies->Remove(old->GetId());
						delete old;
						cookies->pop();

					}

					delete cookieQueue->Remove(entry.Key);
					cq_lock.unlock();
				}
				else {
					break;
				}
			}
		}
	}

	/* =======================*/

	HttpServer::HttpServer(){
	    cout << " ____ ___      .__.____    ._____.      __      __  _________" <<endl;
		cout << "|    |   \\____ |__|    |   |__\\_ |__   /  \\    /  \\/   _____/"<<endl;
		cout << "|    |   /    \\|  |    |   |  || __ \\  \\   \\/\\/   /\\_____  \\ "<<endl;
		cout << "|    |  /   |  \\  |    |___|  || \\_\\ \\  \\        / /        \\"<<endl;
		cout << "|______/|___|  /__|_______ \\__||___  /   \\__/\\  / /_______  /"<<endl;
		cout << "             \\/           \\/       \\/         \\/          \\/"<<endl;
		cout << "Language: C++" <<endl;
		cout << "Version: "<< VERSION <<endl;
		cout << "Developer: hdlopesrocha" << endl;
		cout << "Url: localhost:1991" << endl;
		Resources = new TreeMap<string,HttpPage>(String::GetComparator());
		Cookies = new HashMap<string,Cookie>(String::GetComparator(),new HashString());
		cookieQueue = new TreeMap<time_t,queue<Cookie*>> (new TimeComparator());
		gc_thr = thread(&HttpServer::GarbageCookies, this);
	}

	HttpServer::~HttpServer(){
		delete Resources->Clear();
	}

	void HttpServer::AddPage(string filename,HttpPage * page){
		Resources->Put(filename,page);
	}

	void HttpServer::Run(int port){
		HttpSocket socket = HttpSocket(port);

		while(1){
			new HttpThread(socket.Accept(),this);
		}
	}

}

#endif
