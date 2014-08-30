#include <iostream>
#include "unilib/util/TreeMap.cpp"


using namespace std;
using namespace unilib;


int main(){
	TreeMap<string,string> * tree = new TreeMap<string,string>( new StringComparator());

	tree->Put("A","A");
	tree->Put("C","C");
	tree->Put("B","B");

/*	Iterator<string*> * it = tree->GetIterator();
	
	while(it->IsValid()){
		cout << *(it->GetValue()) <<endl;
		it->Next();
	}
*/	
	return 0;
}
