#include <stdio.h>
#include <iostream>
#include <ctime>
#include "../Collection/Queue.cpp"
#include <string>

using namespace Collection;
using namespace std;

int main(){
    // Create a new Queue of int
    Queue<int> * queue = new Queue<int>();
    // Clone Queue
    Queue<int> * cln;
        
    // Insert some values
    queue->Add(new int(0));
    queue->Add(new int(1));
    queue->Add(new int(2));
    queue->Add(new int(3));
    queue->Add(new int(4));
    queue->Add(new int(5));

    // Delete first value
    delete queue->Remove();

    // Create a clone for this queue
    cln = queue->Clone();

    // Clear original Queue
    delete queue->Clear();

    // Get the iterator for clone queue
	Iterator<int> * it = cln->GetIterator();
	
    // Iterate through the clone queue
	while(it->IsValid()){
	  	cout << *(it->GetValue())<<"\n";
	   	it->Next();
	}

    // Delete Iterator
    delete it;

    // Delete clone Queue and Content
    delete cln->Delete();

    return 0;
}