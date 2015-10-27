#ifndef unilib_json
#define unilib_json

namespace unilib
{
	class JSONNode; 
	class JSONObject;
	class JSONArray;
	class JSONString;
}

#include "../util/Comparator.h"
#include "../util/String.h"
#include "../util/TreeMap.h"
#include <stdlib.h>
#include <stdio.h>
#include <sstream>
#include <string>
#include <string.h>
#include <iostream>
#include <vector>

using namespace std;
using namespace unilib;

namespace unilib
{
	class JSONNode {
    	public: virtual string ToString()=0;	
	};


    class JSONObject : public JSONNode
    {
    	private: TreeMap<string,JSONNode> * content;
    	public: JSONObject(string str);
    	public: JSONObject * GetJSONObject(string key);
    	public: string GetString(string key);
    	public: long GetLong(string key);
    	public: int GetInt(string key);
    	public: double GetDouble(string key);
    	public: float GetFloat(string key);
    	public: bool GetBoolean(string key);
    	public: long Length();
    	public: JSONArray * GetJSONArray(string key);   	
    	public: string ToString();	
		public: Iterator<Entry<string,JSONNode*>>* GetIterator();
		public: bool Has(string key);
    };
    
    class JSONArray : public JSONNode
    {
    	private: vector<JSONNode*> content;
	 	public: JSONArray(string str);
    	public: JSONObject * GetJSONObject(int index);
    	public: string GetString(int index);
    	public: long GetLong(int index);
    	public: int GetInt(int index);
    	public: double GetDouble(int index);
    	public: float GetFloat(int index);
    	public: bool GetBoolean(int index);
    	public: long Length();
    	public: JSONArray * GetJSONArray(int index);	
	 	public: string ToString();	
	 
	 };

	 class JSONString : public JSONNode
    {
    	private: string content;
	 	public: JSONString(string str);
 		public: string ToString();	
	 	public: string GetString();
    	public: long GetLong();
    	public: int GetInt();
    	public: double GetDouble();
    	public: float GetFloat();
    	public: bool GetBoolean();
	 };

}

#endif
