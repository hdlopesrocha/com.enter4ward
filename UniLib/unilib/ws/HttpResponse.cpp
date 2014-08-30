#ifndef unilib_lang_HttpResponse
#define unilib_lang_HttpResponse
#include "WS.hpp"


namespace unilib{

	HttpResponse::HttpResponse(HttpServer * s){
		server = s;
	}

	Cookie * HttpResponse::CreateCookie(){
		cookie = server->GenerateCookie();
		return cookie;
	}

	string HttpResponse::itos(int i) // convert int to string
	{
	    stringstream s;
	    s << i;
	    return s.str();
	}

	// Get current date/time, format is YYYY-MM-DD.HH:mm:ss
	const string HttpResponse::currentDateTime() {
	    time_t     now = time(0);
	    struct tm  tstruct;
	    char       buf[80];
	    tstruct = *localtime(&now);
	    // Visit http://en.cppreference.com/w/cpp/chrono/c/strftime
	    // for more information about date/time format
	    strftime(buf, sizeof(buf),"%a, %d %b %Y %X %Z", &tstruct);

	    return buf;
	}

	void HttpResponse::Echo(string s){
		data += s;
	}

	string HttpResponse::BuildHeader(int size){
		string s="";
		s+="HTTP/" + version + " " + status+"\n";
		s+="Date: " + currentDateTime() + "\n";
		s+="Content-Type: "+ contentType+ "\n";
		s+="Content-Length: "+itos(size)+"\n";
		s+="Server: UniLib/"+VERSION+" (C++)\n";
		s+="Connection: close\n";
		if(cookie!=NULL){
			s+="Set-Cookie: ID="+ cookie->GetId()+"; Max-Age="+to_string(cookie->GetMaxAge())+"; Version=1;Path=/;\n";
		}
		s+="\n";
	//	cout << s << endl;
		return s;
	}

	void HttpResponse::Send(int socket){
		string s = BuildHeader(data.length()) +data;
		send(socket, s.c_str(), s.size()+1,0);
	}


	
	void HttpResponse::SendFile(int socket,string filename)
	{
	//	cout << "Send:" << filename << endl;
	    ifstream ifs(filename, ios::binary|ios::ate);

	    if (!ifs){
			return;
	    }

		deque<string> FileToks = String::Split(string(filename.c_str())+"", '.');
		if(FileToks.size()>1){
			FileToks.pop_back();
			contentType = FileToks.front();
		}
		else {
			contentType = "text/html";
		}
	    
	    ifstream::pos_type pos = ifs.tellg();

	    vector<char> bytes(pos);

	    ifs.seekg(0, ios::beg);
	    ifs.read(&bytes[0], pos);

		string s = BuildHeader(bytes.size());

		vector<char> r(s.begin(),s.end());
    	r.insert(r.end(),bytes.begin(),bytes.end());
		send(socket, &r[0], r.size(),0);
		
		ifs.close();
		
	}

		

}

#endif
