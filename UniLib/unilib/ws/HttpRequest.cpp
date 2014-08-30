#ifndef unilib_lang_HttpRequest
#define unilib_lang_HttpRequest
#include "WS.hpp"


namespace unilib{


	HttpRequest::~HttpRequest(){
		delete attributes->Delete();
	}
	
	
/*
GET /index/info?name=zpsdad&asdad=asdsad HTTP/1.1
Host: localhost:1991
User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:29.0) Gecko/20100101 Firefox/29.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,;q=0.8
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate
Connection: keep-alive
Cache-Control: max-age=0
*/

/*
POST /index HTTP/1.1
Host: localhost:1991
User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:29.0) Gecko/20100101 Firefox/29.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*;q=0.8
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate
Referer: http://localhost:1991/index
Cookie: ID=VDKQtbduSTLyQLiaS44c7Vsh99xJmXKHiefbFHVhkpdAAJlbOn
Connection: keep-alive
Content-Type: application/x-www-form-urlencoded
Content-Length: 9

text=dsff
*/

	HttpRequest::HttpRequest(string& request,HttpServer * server){
//		cout << request << endl;
		
		attributes = new TreeMap<string,string>(String::GetComparator());

		stringstream ss_request(request);

		string currentLine, temp, key , value, attr;
		   				
		if(getline(ss_request,currentLine,'\n')){
		   	stringstream ss_line(currentLine);
		   	
	
			getline(ss_line,type,' ');
			getline(ss_line,url,' ');
			getline(ss_line,version,' ');


			
		   	stringstream ss_url(url);
			getline(ss_url, file, '?');
			getline(ss_url, attr, '\n');
			
			SetAttributes(attr);
			ss_url.clear();
			ss_line.clear();
		}
		else {
			valid = false;
		}
		
		
		string content_type="";
		string content_length="";
		
		while(getline(ss_request,currentLine,'\n') && currentLine.size()>0){
			stringstream ss_dots(currentLine);
			getline(ss_dots,key,':');
			getline(ss_dots,value,';');
			key = String::Trim(key);
			value = String::Trim(value);

			transform(key.begin(), key.end(), key.begin(), ::tolower);

			if(key.compare("cookie")==0){
				// ID=...
				string cookieID = String::Trim(value.substr(3));
				
				cookie = server->GetCookie(cookieID);
			}
			else if(key.compare("content-type")==0){
				content_type=value;
			}	
			else if(key.compare("content-length")==0){
				content_length=value;
			}
		}

		if(content_type.compare("application/x-www-form-urlencoded")==0){

			getline(ss_request,currentLine,'\n');
			SetAttributes(currentLine);
		}
		
		
				
		ss_request.clear();
	}

	void HttpRequest::SetAttributes(string a){
	   	stringstream ss_attr(a);
		string temp, key , value;
		while(getline(ss_attr, temp, '&')){				
	   		stringstream ss_equal(temp);
			getline(ss_equal, key, '=');
			getline(ss_equal, value, '=');
		
			attributes->Put(key,new string(value));

			ss_equal.clear();
		}
		ss_attr.clear();
	}

	string HttpRequest::Read(string key){
		string * res = attributes->Get(key);
	
		return res!=NULL ? *res : "";
	}

	Iterator<Entry<string,string*> > * HttpRequest::GetIterator(){
		return attributes->GetIterator();
	}
	
	
	Cookie * HttpRequest::GetCookie(){
		return cookie;
	}
	
	string HttpRequest::GetVersion(){
		return version;
	}
	
	string HttpRequest::GetUrl(){
		return url;
	}

	string HttpRequest::GetFile(){
		return file;
	}

	bool HttpRequest::IsValid(){
		return valid;
	}	
	
	string HttpRequest::GetType(){
		return type;
	}
}

#endif
