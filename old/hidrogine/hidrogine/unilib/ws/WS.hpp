#ifndef unilib_lang_WS
#define unilib_lang_WS


namespace unilib{
	class HttpServer;
	class HttpThread;
	class HttpResponse;
	class HttpPage;
	class Cookie;
	class HttpSocket;
}



#include <string>
#include <queue>
#include "../util/TreeMap.cpp"
#include "../util/HashMap.cpp"
#include "../util/String.cpp"
#include <fstream>
#include <iostream>
#include <string>
#include <deque>
#include <queue>
#include <sstream>
#include <thread>
#include <sys/types.h>       // For data types
#include <sys/socket.h>      // For socket(), connect(), send(), and recv()
#include <netdb.h>           // For gethostbyname()
#include <arpa/inet.h>       // For inet_addr()
#include <unistd.h>          // For close()
#include <netinet/in.h>      // For sockaddr_in
#include <string.h>
#include <vector>
#include <mutex>
#include <csignal>

#define BUFFERSZ 1024
#define VERSION string("1.0.2.7")

using namespace std;

namespace unilib{
	class HttpRequest {

		private: TreeMap<string,string> * attributes;
		private: string type="";
		private: string url="";
		private: string file="";
		private: string version="";
		private: bool valid=true;
		private: Cookie * cookie= NULL;

		public: ~HttpRequest();
		public: HttpRequest(string& request,HttpServer * server);
		public: string Read(string key);
		public: Iterator<Entry<string,string*> > * GetIterator();
		public: Cookie * GetCookie();
		public: string GetVersion();
		public: string GetUrl();
		public: string GetFile();
		public: bool IsValid();
		private: void SetAttributes(string a);
		public: string GetType();
		
	};


	class HttpServer {
		private: HashMap<string,Cookie> * Cookies;
		private: TreeMap<time_t,queue<Cookie*>> * cookieQueue;
		protected: mutex gr_lock, cq_lock;
		public: TreeMap<string,HttpPage> * Resources;
		public: HttpPage * root;	
		protected: thread gc_thr;
		protected: mutex gc_lock;
				
		public: void SetRoot(HttpPage * p);
		public: Cookie * GetCookie(string& cookieID);
		public: Cookie * GenerateCookie();
		private: bool CanGarbage(time_t currentTime);
		public: void GarbageCookies();
		public: HttpServer();
		public: ~HttpServer();
		public: void AddPage(string filename,HttpPage * page);
		public: void Run(int port);

	};


	class HttpThread {
		private: thread thr; 
		private: mutex thr_mutex;
		private: void run(int socket,HttpServer * server);
		public: ~HttpThread();
		public: HttpThread(int s, HttpServer * server);
	};
	
	class HttpResponse {
		private: string version="1.0";
		private: string status="200 OK";
		private: string contentType="text/html";
		private: Cookie * cookie= NULL;
		private: string data="";
		private: HttpServer * server;
		
		public: HttpResponse(HttpServer * s);
		public: Cookie * CreateCookie();
		private: string itos(int i);
		private: const string currentDateTime();
		public: void Echo(string s);
		private: string BuildHeader(int size);
		public: void Send(int socket);
		public: void SendFile(int socket,string filename);

	};
	
	class HttpPage {
		private: TreeMap<string,HttpPage> * child;
		private: string name;
		public: HttpPage(string n);
		public: virtual ~HttpPage();
		public: void AddChild(HttpPage * page);
		public: string GetName();
		public: HttpPage * GetChild(string s);
		public: Cookie * GetCookie();
		public: virtual void Process(HttpRequest * request, HttpResponse * response);

	};	

	class Cookie {

		private: string id;
		private: TreeMap<string,string> * Attributes;
		private: bool lock;
		private: int maxAge;
		
		public: Cookie(string i,int maxAge);
		public: void Lock();
		public: void Unlock();
		public: bool IsLocked();
		public: ~Cookie();
		public: string GetId();
		public: string Read(string key);
		public: void Write(string key, string value);
		public: int GetMaxAge();
		public: time_t GetExpiration();

	};

	class HttpSocket
	{
		private: int _socketfd;
 		private: struct sockaddr_in _addr;
 		private: socklen_t _length;
 		
		public: HttpSocket();
		public: HttpSocket(int port);
		public: virtual int Accept();
		public: virtual void Close();
	}; 
	
}

#endif
