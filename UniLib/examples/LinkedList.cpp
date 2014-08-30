#include <iostream>
#include "unilib/util/LinkedList.cpp"


using namespace std;
using namespace unilib;


int main(){
	LinkedList<int*> * list = new LinkedList<int*>();

	list->Add(new int(1));
	list->Add(new int(2));
	list->Add(new int(3));

	Iterator<int*> * it = list->GetOterator();
	
	while(it->IsValid()){
		cout << *(it->GetValue()) <<endl;
		it->Next();
	}
	
	return 0;
}
