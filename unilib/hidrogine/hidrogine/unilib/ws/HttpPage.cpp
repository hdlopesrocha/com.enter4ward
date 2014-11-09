#ifndef unilib_lang_HttpPage
#define unilib_lang_HttpPage
#include "WS.hpp"

namespace unilib{


	HttpPage::HttpPage(string n){
		name = n;
		child = new TreeMap<string,HttpPage>(String::GetComparator());		
	}

	HttpPage::~HttpPage(){
		delete child->Clear();
	}

	void HttpPage::AddChild(HttpPage * page){
		child->Put(page->GetName(),page);
	}

	string HttpPage::GetName(){
		return name;
	}

	HttpPage * HttpPage::GetChild(string s){
		return child->Get(s);
	}




	void HttpPage::Process(HttpRequest * request,HttpResponse * response){
		response->Echo( "Type: " + request->GetType() +"<br>");
		response->Echo( "File: " + request->GetUrl() +"<br>");

		Iterator<Entry<string,string*> > * it = request->GetIterator();
		
		while(it->IsValid()){
			response->Echo( "Attribute: "+ it->GetValue().Key +"="+ *(it->GetValue().Value) +"<br>");
			it->Next();
		}
		response->Echo( "Version: " + request->GetVersion() +"<br>");
			
		Cookie * cookie = request->GetCookie();
		if(cookie!=NULL ){
			response->Echo( "Cookie: " + cookie->GetId() +"<br>");
		}
		delete it;
	}

}

#endif
