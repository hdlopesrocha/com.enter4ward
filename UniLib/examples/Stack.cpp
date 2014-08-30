#include <iostream>
#include "unilib/util/Stack.cpp"


using namespace std;
using namespace unilib;


int main(){
	Stack<int*> * stack = new Stack<int*>();

	stack->Push(new int(3));
	stack->Push(new int(2));
	stack->Push(new int(1));

	Iterator<int*> * it = stack->GetIterator();
	
	while(it->IsValid()){
		cout << *(it->GetValue()) <<endl;
		it->Next();
	}
	
	return 0;
}
