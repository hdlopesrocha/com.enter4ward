#include <iostream>
#include "unilib/util/Queue.cpp"


using namespace std;
using namespace unilib;


int main(){
	Queue<int*> * queue = new Queue<int*>();

	queue->Add(new int(1));
	queue->Add(new int(2));
	queue->Add(new int(3));

	Iterator<int*> * it = queue->GetIterator();
	
	while(it->IsValid()){
		cout << *(it->GetValue()) <<endl;
		it->Next();
	}
	
	return 0;
}
