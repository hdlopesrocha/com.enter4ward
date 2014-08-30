
#include "Json.h"

namespace unilib
{

	int sizeOf(string& str,int first, char begin, char end){
		int count=0;
		int strSize = str.size();

		for(int i=first; i < strSize ; ++i){

			if(str.at(i)==begin){
				count++;
			} else if(str.at(i)==end){
				count--;
			}	

			if(count==0 || (begin==end && count==2)){
				return i-first;
			}
		}
		return -1;
	}
	
	int sizeOfString(string& str,int first){
		int count=0;
		int strSize = str.size();

		for(int i=first; i < strSize ; ++i){

			if(str.at(i)=='"'){
				count++;
			} 
			if(count==2){
				return i-first;
			}
		}
		return -1;
	}

 	JSONArray::JSONArray(string str){
 		int sz=str.size();
		string value;
		
		for(int i=1;i<sz;i++){
			char c=str.at(i);

			if(c=='{'){
				int sz= sizeOf(str,i,'{','}');
				value =str.substr(i,sz+1);
				i += sz;
			}
			else if(c=='['){
				int sz= sizeOf(str,i,'[',']'); 
				value =str.substr(i,sz+1);
				i += sz;
			}
			else if(c=='"'){
				int sz= sizeOfString(str,i); 
				value = str.substr(i,sz+1);
				i += sz;
			}
			else if(c==',' || c==']'){
				if(value.at(0)=='{'){
					content.push_back(new JSONObject(value));				
				}
				else if(value.at(0)=='['){
					content.push_back(new JSONArray(value));	
				}
				else {
					content.push_back(new JSONString(value));
				}
				value ="";
			}
			else if(c==' ' || c=='\n' || c=='\t' || c=='\b'){
				// ignore
			}
			else {
				value+=c;
			}	
		}
 	}
 	
 	JSONObject * JSONArray::GetJSONObject(int index){
		return (JSONObject*) content.at(index);
 	}
 	
 	JSONArray * JSONArray::GetJSONArray(int index){
		return (JSONArray*) content.at(index);
 	}



 	string JSONArray::GetString(int index){
 		return ((JSONString*)content.at(index))->GetString();
 	}
 	

 	long JSONArray::GetLong(int index){
 		return ((JSONString*)content.at(index))->GetLong();
 	}
 	
 	int JSONArray::GetInt(int index){
   	return ((JSONString*)content.at(index))->GetInt();
   }
    	
 	double JSONArray::GetDouble(int index){
 		return ((JSONString*)content.at(index))->GetDouble();
 	}
 	
 	float JSONArray::GetFloat(int index){
 		return ((JSONString*)content.at(index))->GetFloat();
 	}
 	
 	bool JSONArray::GetBoolean(int index){
 		return ((JSONString*)content.at(index))->GetBoolean();
 	}
 	
 	long JSONArray::Length(){
 		return content.size();
 	}
 	 
 	JSONObject::JSONObject(string str){
 		content = new TreeMap<string,JSONNode>(String::GetComparator());
	 
		int sz=str.size();

		string key, value;
		bool onValue=false;


		for(int i=1;i<sz;i++){
			char c=str.at(i);

			if(c=='{'){
				int sz= sizeOf(str,i,'{','}');
				value =str.substr(i,sz+1);
				i += sz;
			}
			else if(c=='['){
				int sz= sizeOf(str,i,'[',']'); 
				value =str.substr(i,sz+1);
				i += sz;
			}
			else if(c=='"'){
				int sz= sizeOfString(str,i); 
				if(onValue) {
					value = str.substr(i,sz+1);
				}
				else {
					key = str.substr(i+1,sz-1);
				}
				i += sz;
			}
			else if(c==',' || c=='}'){
				if(value.at(0)=='{'){
					content->Put(key, new JSONObject(value));				
				}
				else if(value.at(0)=='['){
					content->Put(key, new JSONArray(value));	
				}
				else {
					content->Put(key,new JSONString(value));
				}
			
				key = value ="";
				onValue=false;
			}
			else if(c==':'){
				onValue=true;
			}
			else if(c==' ' || c=='\n' || c=='\t' || c=='\b'){
				// ignore
			}
			else {
				value+=c;
			}	
		}
 	}
 	

 	JSONArray * JSONObject::GetJSONArray(string key){
		return (JSONArray*) content->Get(key);
 	}    	

 	JSONObject * JSONObject::GetJSONObject(string key){
		return (JSONObject*) content->Get(key);
 	}

 	string JSONObject::GetString(string key){
 		return ((JSONString*)content->Get(key))->GetString();
 	}

 	
 	long JSONObject::GetLong(string key){
 		return ((JSONString*)content->Get(key))->GetLong();
 	}
 	
 	int JSONObject::GetInt(string key){
   	return ((JSONString*)content->Get(key))->GetInt();
   }
    	
 	double JSONObject::GetDouble(string key){
 		return ((JSONString*)content->Get(key))->GetDouble();
 	}
 	
 	float JSONObject::GetFloat(string key){
 		return ((JSONString*)content->Get(key))->GetFloat();
 	}
 	
 	bool JSONObject::GetBoolean(string key){
 		return ((JSONString*)content->Get(key))->GetBoolean();
 	}
 	
 	long JSONObject::Length(){
 		return content->Size();
 	}

 	JSONString::JSONString(string str){
 		content=str;
 	}

 	string JSONArray::ToString(){
 		return "[array]";
 	}

 	string JSONObject::ToString(){
 		return "{object}";
 	}


 	string JSONString::ToString(){
 		return content;
 	}

 	string JSONString::GetString(){
 		return content.substr(1,content.length()-2);
 	}
 	
 	long JSONString::GetLong(){
 		return atol(content.c_str());
 	}
 	
 	int JSONString::GetInt(){
 		return atoi(content.c_str());
 	}
 	
 	double JSONString::GetDouble(){
 		return atof(content.c_str());
 	}
 	
 	float JSONString::GetFloat(){
 		return atof(content.c_str());
 	}
 	
 	bool JSONString::GetBoolean(){
 		return content.compare("true")==0;
 	}

}

