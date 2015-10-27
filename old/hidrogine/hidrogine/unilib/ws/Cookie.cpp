#ifndef unilib_lang_Cookie
#define unilib_lang_Cookie
#include "WS.hpp"


namespace unilib{

	Cookie::Cookie(string i,int m){
		maxAge=m;
		Attributes = new TreeMap<string,string>(String::GetComparator());
		id = i;
		lock = false;
		
	}

	
	void Cookie::Lock(){
		lock = true;
	}
	
	void Cookie::Unlock(){
		lock = false;
	}
	
	bool Cookie::IsLocked(){
		return lock;
	}	
	
	
	int Cookie::GetMaxAge(){
		return maxAge;
	}
	
	Cookie::~Cookie(){
		delete Attributes->Delete();
	}

	string Cookie::GetId(){
		return id;
	}


	string Cookie::Read(string key){
		string * res = Attributes->Get(key);
	
		return res!=NULL ? *res : "";
	}


	void Cookie::Write(string key, string value){
		string * ans = Attributes->Put(key,new string(value));
		if(ans!=NULL)
			delete ans;
	}



	

}

#endif
