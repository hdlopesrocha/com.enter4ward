#include "unilib/ws/WS.hpp"
#include "unilib/json/Json.hpp"


using namespace std;
using namespace unilib;

void Navigator(HttpResponse * response){
	response->Echo(
		"<div>"
			"<nav>"
				"<a href='/index'>Home</a> | "
				"<a href='/index/login'>Login</a> | "
				"<a href='/index/store'>Store</a>"
			"</nav>"
		"</div>"
	);
}

void Footer(HttpResponse * response){
	response->Echo(
		"<div>"
			"<footer>"
				"<a href='/index/info'>Info</a>"
			"</footer>"
		"</div>"
	);
}

void LoginForm(HttpResponse * response){
	response->Echo(
		"<form action='' method='POST'>"
			"User:<input type='text' name='user'><br>"
			"Pass:<input type='password' name='pass'><br>"
			"<input type='submit' value='login'>"
		"</form>"
	);
}

void StoreForm(HttpResponse * response,Cookie * cookie){
	response->Echo(
		"<form action='' method='POST'>"
			"<input type='text' name='text'><br>"
			"<input type='submit' value='save'>"
		"</form>"
	);
}

void Begin(HttpResponse * response){
	response->Echo("<html><head><link rel='stylesheet' type='text/css' href='/res/style.css'><title>UniLib WS</title></head><body>");
}

void End(HttpResponse * response){
	response->Echo("</body></html>");
}











class Index : public HttpPage {

	public: Index(string n):HttpPage(n) {}

	public: virtual void Process(HttpRequest * request, HttpResponse * response){
		Begin(response);
		Navigator(response);
		response->Echo("<div>");
		response->Echo("<img src='res/test.png'>");
		response->Echo("</div>");
		

		
		
		
		
		Footer(response);
		End(response);
	}
};

class Login : public HttpPage {

	public: Login(string n):HttpPage(n) {}

	public: virtual void Process(HttpRequest * request, HttpResponse * response){
		Begin(response);
		Navigator(response);
		response->Echo("<div>");
		LoginForm(response);
		response->Echo("</div>");
		Footer(response);
		End(response);
	}
};

class Store : public HttpPage {

	public: Store(string n):HttpPage(n) {}

	public: virtual void Process(HttpRequest * request, HttpResponse * response){
		Cookie * cookie = request->GetCookie();
		Begin(response);
		Navigator(response);
		response->Echo("<div>");
		StoreForm(response, cookie);

		string receivedText = request->Read("text");
		if(receivedText.size()>0){
			if(cookie==NULL ){
				cookie = response->CreateCookie();
			}
			cookie->Write("text",receivedText);
		}

		if(cookie!=NULL ){
			response->Echo("Stored:&nbsp"+cookie->Read("text"));
		}
		response->Echo("</div>");
		Footer(response);
		End(response);
	}
};

int main(){

	HttpServer * server = new HttpServer();

	string str = "{\n\t\"x\":{\n\t\t\"y\":99,\n\t\t\"z\":\"ola\",\n\t\t\"w\":[1, 2,3 ]\n\t}\n}";
	JSONObject * obj1 = new JSONObject(str);
	JSONObject * obj2 = obj1->GetJSONObject("x");
	
	cout << str << endl;
	cout << "y="<<obj2->GetInt("y") << endl;
	cout << "z="<<obj2->GetString("z") << endl;
	
	JSONArray *arr1= obj2->GetJSONArray("w");
	
	for(int i=0; i < arr1->Length() ; ++i){
		cout << "w["<< i <<"]="<<arr1->GetInt(i) << endl;
	}


	Index * index = new Index("index");

	index->AddChild(new HttpPage("info"));
	index->AddChild(new Store("store"));
	index->AddChild(new Login("login"));

	server->SetRoot(index);
	server->AddPage("index.html",index);
	server->AddPage("index",index);
	server->Run(1991);


	return 0;
}

