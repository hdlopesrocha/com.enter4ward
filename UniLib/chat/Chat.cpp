#include "../unilib/ws/WS.hpp"
#include "../unilib/util/Queue.cpp"
#include "../unilib/util/LockMap.cpp"

using namespace std;
using namespace unilib;

LockMap<int,string>  * chat_messages = new LockMap<int,string>(new IntComparator());

void LoginForm(HttpResponse * response){
	response->Echo(
		"<div><form action='' method='POST'>"
			"User:<input type='text' name='user' autofocus><br>"
			"<input type='submit' value='login'>"
		"</form></div>"
	);
}

void ChatForm(HttpResponse * response){
   	response->Echo("<div id='msgs'></div>");	
	response->Echo("<script>getmsg(0);</script>");
	response->Echo(
		"<footer>"
			"<input type='text' name='message' autofocus class='message' onkeypress='handleKeyPress(event,this)' autocomplete='off'>"
		"</footer>"
	);
}

void Begin(HttpResponse * response){
	response->Echo("<html><head><link rel='stylesheet' type='text/css' href='/res/style.css'><script src='/res/msg.js'></script> <title>UniLib WS</title></head><body><div><h2>UniLib Chat</h2></div>");
}

void End(HttpResponse * response){
	response->Echo("</body></html>");
}

class Chat : public HttpPage {
	public: Chat():HttpPage("") {}
	
	public: virtual void Process(HttpRequest * request, HttpResponse * response){
		Cookie * cookie = request->GetCookie();
		{
			string receivedText = request->Read("user");
			if(receivedText.size()>0){
				if(cookie==NULL ){
					cookie = response->CreateCookie();
				}
				cookie->Write("user",receivedText);
			}
		}
		Begin(response);
		if(cookie!=NULL ){
			string receivedText = request->Read("message");
			if(receivedText.size()>0){
				chat_messages->Put(chat_messages->Size(),new string("<b>"+cookie->Read("user")+"</b> : "+receivedText));
			}
			ChatForm(response);
		}
		else {
			LoginForm(response);
		}
		End(response);
	}
};

class Msn : public HttpPage {
	public: Msn():HttpPage("") {}

	public: virtual void Process(HttpRequest * request, HttpResponse * response){
		string n = request->Read("n");
		int i = atoi(n.c_str());

		string * m = chat_messages->Get(i);
		
		if(m!=NULL)
        	response->Echo(*m+"<br>");
	}
};

int main(){
	chat_messages->Put(0,new string("<b>admin</b> : Welcome to Unilib Chat!"));
	HttpServer * server = new HttpServer();
	Chat * chat = new Chat();
	Msn * msn = new Msn();
	server->SetRoot(chat);
	server->AddPage("chat",chat);	
	server->AddPage("msn",msn);		
	server->Run(1991);
	return 0;
}

