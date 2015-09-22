#include "XmlNode.cpp"
#include <iostream>
#include <string>
#include <sstream>

using namespace std;

namespace unilib
{

	class XmlDocument {
		XmlNode * Root;

		public: XmlDocument(){
			Root = NULL;
		}

		public: XmlDocument(string path){
			Root = NULL;
			cout << "Hello World" << endl;

		}

		public: void Save(string path){
			
		}
	};
}