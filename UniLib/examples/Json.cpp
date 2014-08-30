#include "unilib/json/JSONObject.hpp"

using namespace std;
using namespace unilib;

int main(){

	string str = "{\n\t\"x\":{\n\t\t\"y\":99,\n\t\t\"z\":\"ola\",\n\t\t\"w\":[1, 2,3 ]\n\t}\n}";
	JSONObject * obj1 = new JSONObject(str);
	JSONObject * obj2 = obj1->GetJSONObject("x");
	
	cout << str << endl;
	cout << "y="<<obj2->GetString("y") << endl;
	cout << "z="<<obj2->GetString("z") << endl;
	
	JSONArray *arr1= obj2->GetJSONArray("w");
	
	for(int i=0; i < arr1->Length() ; ++i){
		cout << "w["<< i <<"]="<<arr1->GetString(i) << endl;
	}

	return 0;
}

