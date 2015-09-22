#include <stdio.h>
#include <iostream>
#include <ctime>
#include "../Collection/Stack.cpp"
#include <string>

using namespace Collection;
using namespace std;

int main(){
    // Create a new Stack of int
    Stack<int> * stk = new Stack<int>();
    // Clone Stack
    Stack<int> * cln;
        
    // Insert some values
    stk->Push(new int(1));
    stk->Push(new int(2));
    stk->Push(new int(3));
    stk->Push(new int(4));
    stk->Push(new int(5));
    stk->Push(new int(6));

    // Delete top value
    delete stk->Pop();

    // Create a clone for this stack
    cln = stk->Clone();

    // Clear original Stack
    delete stk->Clear();

    // Get the iterator for clone stack
	Iterator<int> * it = cln->GetIterator();
	
    // Iterate through the clone stack
	while(it->IsValid()){
	  	cout << *(it->GetValue())<<"\n";
	   	it->Next();
	}

    // Delete Iterator
    delete it;

    // Delete clone Stack and Content
    delete cln->Delete();

    return 0;
}